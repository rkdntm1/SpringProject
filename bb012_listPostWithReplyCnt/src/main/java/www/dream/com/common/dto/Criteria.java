package www.dream.com.common.dto;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import www.dream.com.framework.util.StringUtil;

@Data
public class Criteria implements Comparable<Criteria>{
	private static final float PAGENATION_TOTAL = 10;
	 
	/** 검색어 뭉치. 예시) "내사랑 이오" */
	private String searching; 
	
	private int pageNumber;	// 현재 쪽 번호
	private int amount; 	// 쪽 당 보여줄 데이터 건수
	
	/* @JsonIgnoreProperties
	 * JSON으로 클라이언트에게 정보 전달 시 필요가 없을시 지정하는 것이며
	 * 이는 통신 데이터 크기 절감. 따라서 성능 향상!! 
	 */
	@JsonIgnoreProperties  
	private int startPage; // 화면에 출력되는 첫 쪽 번호
	@JsonIgnoreProperties
	private int endPage; // 화면에 출력되는 마지막 쪽 번호
	@JsonIgnoreProperties
	private boolean prev;		// 앞으로 가기
	@JsonIgnoreProperties
	private boolean next;		// 뒤로 가기 
	
	@JsonIgnoreProperties
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

	/**
	 * 모든 목록 페이지에서 페이징 처리용 HTML tag들을 각자 출력하는 중복성을 제거하여
	 * 이곳에서 통합적으로 서비스 할 수 있도록 모듈화 시켰다. 이로써 코드량의 절감. 유지보수성 향상
	 * @return
	 */
	public String getPagingDiv() {
		StringBuilder sb = new StringBuilder("<ul id='ulPagination' class='pagination'>");
		
		if (this.prev) {
			sb.append("<li class='page-item previous'>");
			sb.append("<a class='page-link' href='" + (this.startPage - 1)+ "'>&lt;&lt;</a>");
			sb.append("</li>");
		}
		
		for (int num = this.startPage; num <=this.endPage; num++) {
			sb.append("<li class='page-item " + (this.pageNumber == num ? "active" : "") + "'>");
			sb.append("<a class='page-link' href=" + num + ">" + num + "</a>");
			sb.append("</li>");
		}
		
		if (this.next) {
			sb.append("<li class='page-item next'>");
			sb.append("<a class='page-link' href='" + (this.endPage + 1)+ "'>&gt;&gt;</a>");
			sb.append("</li>");
		}
		sb.append("</ul>");

		return sb.toString();
	}
	
	@Override
	public int compareTo(Criteria o) {
		int ret = pageNumber - o.pageNumber;
		return ret == 0 ? amount - o.amount : ret;
	}
}
