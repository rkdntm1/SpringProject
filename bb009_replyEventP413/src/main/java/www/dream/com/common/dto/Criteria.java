package www.dream.com.common.dto;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import www.dream.com.framework.util.StringUtil;

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
		//endPage = (int) (Math.ceil(pageNumber / PAGENATION_TOTAL) * PAGENATION_TOTAL);
		//현재 페이지가 중앙에 위치하도록 하는 스타일 
		endPage = pageNumber + (int)(PAGENATION_TOTAL / 2);
		if (endPage < PAGENATION_TOTAL)
			endPage = (int) PAGENATION_TOTAL;	
		startPage = endPage - (int) (PAGENATION_TOTAL - 1);
		int realEnd = (int) Math.ceil((float) total / amount);
		if (endPage > realEnd) {
			endPage = realEnd;
		}
		
		prev = startPage > 1;
		next = endPage < realEnd;
		
	}
	
	/** 검색내용이 있는지 확인 framework 사용 */
	public boolean hasSearching() {
		return StringUtil.hasInfo(searching);
	}
	
	/** searching이 null 이면 새로운 배열을 만들고 있으면 공백을 기준으로 분할해서 배열로 받음*/
	public String[] getSearchingHashtags() {
		return searching == null ? new String[] {} : searching.split(" ");
	}
	
	/**
	 * Criteria가 가지고 있는 정보를 UriComponentsBuilder에 추가해줍니다.
	 * @param builder
	 */
	public void appendQueryParam(UriComponentsBuilder builder) {
		builder.queryParam("pageNumber", pageNumber)
				.queryParam("amount", amount)
				.queryParam("searching", searching);
	}
}
