package www.dream.com.framework.util;

public class FileUtil {
	public static String truncateExt(String fileName) {
		if(! StringUtil.hasInfo(fileName))
			return "";
		int lastIdx = fileName.lastIndexOf('.');
		if (lastIdx == -1)
			return fileName;
		return fileName.substring(0, lastIdx);
	}
}
