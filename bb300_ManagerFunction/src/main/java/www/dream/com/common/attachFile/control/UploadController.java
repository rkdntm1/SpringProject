package www.dream.com.common.attachFile.control;


import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.framework.util.StringUtil;

@RestController
@RequestMapping("/uploadFiles/*")
public class UploadController {
	private static final String UPLOAD_FOLDER = "C:\\uploadedFiles";
	@GetMapping("uploadByAjax")
	public void uploadByAjax() {
	}
	
	@PreAuthorize("isAuthenticated()") //현재 사용자가 로그인 처리 했습니까?를 물어봄
	@PostMapping(value = "upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<String>> uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		List<AttachFileVO> listAttachFileVO = new ArrayList<>();
		File uploadPath = new File(UPLOAD_FOLDER, getFolderName());
		if (! uploadPath.exists()) {
			//필요한 폴더구조가 없다면 그 전체를 만들어 준다
			uploadPath.mkdirs();
		}
		
		for (MultipartFile uf : uploadFiles) {
			AttachFileVO attachFileVO = new AttachFileVO(uploadPath, uf);
			listAttachFileVO.add(attachFileVO);
		}
		List<String> ret = listAttachFileVO.stream().map(vo->vo.getJson()).collect(Collectors.toList());
		
		System.out.println("apfhd");
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@GetMapping("display")
	public ResponseEntity<byte[]> getThumbnaileFile(@RequestParam("fileName") String fileName) {
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
	 * @param fileName C:\\uploadedFiles\2021\06\22\myfile.txt	Full File Path 형식으로 관리 할 것임.
	 * @return
	 */
	@GetMapping(value="download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, @RequestParam("fileName") String fileName) {
		Resource resource = new FileSystemResource(fileName);
		
		if(! resource.exists()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		String resourceFilename = AttachFileVO.filterPureFileName(resource.getFilename());
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadFileName = null;
			if(userAgent.contains("Trident") || userAgent.contains("Edge")) {
				downloadFileName = URLEncoder.encode(resourceFilename, "UTF-8");
			} else {
				downloadFileName = new String(resourceFilename.getBytes("UTF-8"), "ISO-8859-1");	
			}
			
			headers.add("Content-Disposition", "attachment; filename=" + downloadFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(resource, headers, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()") //현재 사용자가 로그인 처리 했습니까?를 물어봄
	@PostMapping(value = "deleteFile")
	public ResponseEntity<String> cancelAttach(AttachFileVO attchVO) {
		try {
			File pureSaveFile = new File(attchVO.getSavedFolderPath(), attchVO.getPureSaveFileName());
			pureSaveFile.delete();
			if (StringUtil.hasInfo(attchVO.getPureThumbnailFileName())) {
				new File(attchVO.getSavedFolderPath(), attchVO.getPureThumbnailFileName()).delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
	}
	
	//오늘 날짜에 활용할 폴더 이름을 주세요
	private String getFolderName() {
		SimpleDateFormat fsd = new SimpleDateFormat("yyyy-MM-dd");
		return fsd.format(new Date()).replace('-', File.separatorChar);
	}
	
	
}
