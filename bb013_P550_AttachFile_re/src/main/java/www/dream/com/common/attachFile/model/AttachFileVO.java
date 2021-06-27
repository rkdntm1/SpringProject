package www.dream.com.common.attachFile.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import lombok.Data;
import www.dream.com.framework.util.FileUtil;

@Data
public class AttachFileVO {
	public static final String THUMBNAIL_FILE_PREFIX = "thumb_";
	public static final String UUID_PURE_SEP = "_";
	
	//UUID
	@Expose
	private String uuid;
	//서버에서 저장된 폴더 이름
	@Expose
	private String savedFolderPath;
	//사용자가 업로드한 순수 파일 이름
	@Expose
	private String pureFileName;
	@Expose
	private String pureSaveFileName;
	@Expose
	private String pureThumbnailFileName;
	//MultimediaType
	@Expose
	private MultimediaType multimediaType;
	
	public static String fillterPureFileName(String pureSaveFileName) {
		return pureSaveFileName.substring(pureSaveFileName.indexOf(UUID_PURE_SEP) + 1);
	}
			
	@Expose
	private String fileCallPath;
	@Expose
	private String originalFileCallPath;
	
	public String getJson() {
		buildAuxInfo();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String ret = "";
		try {
			ret = URLEncoder.encode(gson.toJson(this), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/** Client에서 필요한 부가적인, 보조적인 정보 만들기 */
	private void buildAuxInfo() {
		pureSaveFileName = uuid + UUID_PURE_SEP + pureFileName;
		
		if (multimediaType == MultimediaType.image)
			pureThumbnailFileName = THUMBNAIL_FILE_PREFIX + pureSaveFileName; 
		if (multimediaType == MultimediaType.video) {
			String pureThumbnailFileName = AttachFileVO.THUMBNAIL_FILE_PREFIX + FileUtil.truncateExt(pureSaveFileName) + ".png";
		}
		
		fileCallPath = savedFolderPath + File.separator + pureThumbnailFileName;
		
		originalFileCallPath = savedFolderPath + File.separator + pureSaveFileName ;
	}
}
