package www.dream.com.framework.util;

public class FileUtil {
	public static String truncateExt(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}
}
