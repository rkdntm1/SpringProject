package www.dream.com.framwork.util;

public class StringUtil {

	/**
	 * String으로 받아진 시퀀스들을 배열로 만들고 다시 이것을 int형 배열로 형변환시켜줌.
	 * @param ids 새로운 HashTag 집합의 크기로 만들어낸 시퀀스들
	 * @return
	 */
	public static int[] convertCommaSepString2IntArr(String ids) {
		String[] splited = ids.split(",");
		int[] ret = new int[splited.length];
		for (int i = 0; i < splited.length; i++) {
			try {
				ret[i] = Integer.parseInt(splited[i]);	
			} catch (NumberFormatException e) {
			}
		}
		return ret;
	}
}
