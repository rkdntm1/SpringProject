package www.dream.com.common.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public enum MultimediaType {
	image, video, audio, others;
	
	public static MultimediaType identifyMultimediaType(File file) {
		String contentType = null;
		try {
			//discrete-type := "text" / "image" / "audio" / "video" / "application" / extension-token
			contentType = Files.probeContentType(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (contentType == null || contentType.startsWith("text") || contentType.startsWith("application"))
			return MultimediaType.others;
		if (contentType.startsWith("image"))
			return MultimediaType.image;
		if (contentType.startsWith("audio"))
			return MultimediaType.audio;
		if (contentType.startsWith("video"))
			return MultimediaType.video;
		
		//새로운 국제 표준이 만들어 졌을가요?(조건에 없는 타입이 나올경우 메시지 출력)
		System.out.println("새로운 국제 표준이 만들어 졌을가요? " + contentType);
		
		return null;
	}
}
