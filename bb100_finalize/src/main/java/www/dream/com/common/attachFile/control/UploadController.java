package www.dream.com.common.attachFile.control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.model.MultimediaType;
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
	@PreAuthorize("isAuthenticated()") //현재 사용자가 로그인처리된 사용자인가?
	@ResponseBody
	public ResponseEntity<List<String>> uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		List<AttachFileVO> listAttachFileVO = new ArrayList<>();
		File uploadPath = new File(UPLOAD_FOLDER, getFolderName());
		if (! uploadPath.exists()) {
			// 필요한 폴더 구조가 없다면 그 전체를 만들어 준다.
			uploadPath.mkdirs();
		}
		
		for (MultipartFile uf : uploadFiles) {
			listAttachFileVO.add(new AttachFileVO(uploadPath, uf));
		}
		List<String> ret = listAttachFileVO.stream()
											.map(vo -> vo.getJson())
											.collect(Collectors.toList());
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
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
	@PreAuthorize("isAuthenticated()") //현재 사용자가 로그인처리된 사용자인가?
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
	
}