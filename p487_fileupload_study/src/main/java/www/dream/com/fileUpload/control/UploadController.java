package www.dream.com.fileUpload.control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import net.coobird.thumbnailator.Thumbnailator;
import www.dream.com.fileUpload.model.AttachFileVO;
import www.dream.com.framework.util.FileUtil;
import www.dream.com.framework.util.StringUtil;

@Controller
@RequestMapping("/uploadFiles/*")
public class UploadController {
	private static final String UPLOAD_FOLDER = "C:\\uploadFiles";
	
	@GetMapping("uploadByAjax")
	public void uploadByAjax() {
	}
	
	@PostMapping(value = "upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileVO>> uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		List<AttachFileVO> listAttachFileVO = new ArrayList<>();
		File uploadPath = new File(UPLOAD_FOLDER, getFolderName());
		if (! uploadPath.exists()) {
			// 필요한 폴더 구조가 없다면 그 전체를 만들어 준다.
			uploadPath.mkdirs();
		}
		
		for (MultipartFile uf : uploadFiles) {
			AttachFileVO attachFileVO = new AttachFileVO();
			attachFileVO.setSavedFolderPath(uploadPath.getAbsolutePath());
			
			String originalFilename = uf.getOriginalFilename();
			//폴더구조없이 순수 파일 이름 찾기
			String pureFilename = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
			attachFileVO.setPureFileName(pureFilename);
			//UUID 여러 사용자가 올리는 파일의 이름이 같더라도 모두 수용 할 수 있다.
			String uuid = UUID.randomUUID().toString();
			attachFileVO.setUuid(uuid);
			String pureSaveFileName = attachFileVO.getPureSaveFileName();
			File save = new File(uploadPath, pureSaveFileName);
			
			try {
				uf.transferTo(save);
				makeThumbnail(uploadPath, save, pureSaveFileName, attachFileVO);
				attachFileVO.setMultimediaType(MultimediaType.identifyMultimediaType(save));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			listAttachFileVO.add(attachFileVO);
		}
		return new ResponseEntity<>(listAttachFileVO, HttpStatus.OK);
	}
	
	@GetMapping("display")
	@ResponseBody
	public ResponseEntity<byte[]> getThumbnailFile(@RequestParam("fileName") String fileName) {
		File file = new File(fileName);
		ResponseEntity<byte[]> res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			res = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 
	 * @param fileName ex) C:\ uploadFiles\2021\06\22\myfile.txt   전체 파일(Full File Path) 형식으로 관리 할 것
	 * @return
	 */
	@GetMapping(value="download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, @RequestParam("fileName") String fileName) {
		Resource resource = new FileSystemResource(fileName);
		
		if (! resource.exists()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND); 
		}
		String resourceFilename = AttachFileVO.fillterPureFileName(resource.getFilename());
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadFileName = null;
			if (userAgent.contains("Trident") || userAgent.contains("Edge")) { //ms 계열 
				downloadFileName = URLEncoder.encode(resourceFilename, "UTF-8");
			} else { // 표준일때
				downloadFileName = new String(resourceFilename.getBytes("UTF-8"), "ISO-8859-1");
			}
			headers.add("Content-Disposition", "attachment; filename=" + downloadFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK); 
	}
	
	@PostMapping(value = "deleteFile")
	@ResponseBody
	public ResponseEntity<String> cancelAttach(AttachFileVO attchVo) {
		try {
			File pureSaveFile = new File(attchVo.getSavedFolderPath(), attchVo.getPureSaveFileName());
			pureSaveFile.delete();
			if (StringUtil.hasInfo(attchVo.getPureThumbnailFileName())) {
				new File(attchVo.getSavedFolderPath(), attchVo.getPureThumbnailFileName()).delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
	}
	
	//오늘 날짜에 활용할 폴더 이름을 주세요
	private String getFolderName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//File.separatorChar는 윈도우즈에서는 \\ 를 사용
		return sdf.format(new Date()).replace('-', File.separatorChar);
	}
	
	private void makeThumbnail(File uploadPath, File uploadedFile, String pureSaveFileName, AttachFileVO attachFileVO) {
		MultimediaType multimediaType = MultimediaType.identifyMultimediaType(uploadedFile);
		if (multimediaType == MultimediaType.image) {
			String pureThumbnailFileName = AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName;
			attachFileVO.setPureThumbnailFileName(pureThumbnailFileName);
			File thumbnailFile = new File(uploadPath, pureThumbnailFileName);
			try {
				Thumbnailator.createThumbnail(uploadedFile, thumbnailFile, 100, 100);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else if (multimediaType == MultimediaType.video) {
			pureSaveFileName = FileUtil.truncateExt(pureSaveFileName); //확장자 제거한 파일명추출
			String pureThumbnailFileName = AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName + ".png";
			attachFileVO.setPureThumbnailFileName(pureThumbnailFileName);
			File thumbnailFile = new File(uploadPath, pureThumbnailFileName);
			try {
				int frameNumber = 0;
				//Video 파일에서 첫번째 프레임의 이미지를 가져오기
				Picture picture = FrameGrab.getFrameFromFile(uploadedFile, frameNumber);
				BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
				ByteArrayOutputStream os = new ByteArrayOutputStream(); 
				ImageIO.write(bufferedImage, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				
				FileOutputStream fileOutputStream = new FileOutputStream(thumbnailFile);
				//가져온 이미지를 Thumbnail로 만들기
				Thumbnailator.createThumbnail(is, fileOutputStream, 100, 100);
				fileOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
