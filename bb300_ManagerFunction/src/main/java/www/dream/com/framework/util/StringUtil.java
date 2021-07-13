package www.dream.com.framework.util;

public class StringUtil {

	/**
	 * ids 분할하여 정수 배열로 반환
	 * @param ids 숫자가 ,로 구분되어 있습니다
	 * @return
	 */
	public static int[] convertCommaSepString2IntArr(String ids) {
		String[] splited = ids.split(",");
		int[] ret = new int[splited.length];
		for (int i = 0; i < splited.length; i++) {
			try {
				ret[i] = Integer.parseInt(splited[i]);
			} catch(NumberFormatException e) {
			}
		}
		return ret;
	}
	
	public static boolean hasInfo(String str) {
		//널이면 정보 없고 또는 텅 비어 있어도 정보 없네   a && b --> !a || !b  드모르강 법칙
		return str != null && ! str.trim().isEmpty(); 
	}
}
