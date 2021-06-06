package www.dream.com.common.dto;

import lombok.Data;

@Data
public class Criteria {
	private static final float PAGENATION_TOTAL = 10;
	
	/** 검색어 뭉치. 예시) "내사랑 이오" */
	private String searching; 
	
	private int pageNumber;	// 현재 쪽 번호
	private int amount; 	// 쪽 당 보여줄 데이터 건수

	private int startPage, endPage; // 화면에 출력되는 첫 쪽 번호 및 마지막 쪽 번호
	private boolean prev, next;		// 앞으로 가기 및 뒤로 가기 
	
	private long total; // 전체 데이타 건수
	
	public Criteria() { // 기본생성자가 제일 먼저 호출 
		this.pageNumber = 1;
		this.amount = 10;
	}
	
	public void setTotal(long total) {
		this.total = total;
		calc();
	}
	
	private void calc() {
		endPage = (int) (Math.ceil(pageNumber / PAGENATION_TOTAL) * PAGENATION_TOTAL);
		startPage = endPage - (int) (PAGENATION_TOTAL - 1);
		int realEnd = (int) Math.ceil((float) total / amount);
		if (endPage > realEnd) {
			endPage = realEnd;
		}
		
		prev = startPage > 1;
		next = endPage < realEnd;
		
	}
	
	/** searching이 null 이면 새로운 배열을 만들고 있으면 공백을 기준으로 분할해서 배열로 받음*/
	public String[] getSearchingHashtags() {
		return searching == null ? new String[] {} : searching.split(" ");
	}
}
