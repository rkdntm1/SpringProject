package www.dream.com.common.dto;

import lombok.Data;

@Data
public class Criteria {
	private static final float PAGENATION_TOTAL = 10;
	private int pageNumber;	// 현재 쪽 번호
	private int amount; 	// 쪽 당 보여줄 데이터 건수

	private int startPage, endPage; // 화면에 출력되는 첫 쪽 번호 및 마지막 쪽 번호
	private boolean prev, next;		// 앞으로 가기 및 뒤로 가기 
	
	private int total; // 전체 데이타 건수
	
	public Criteria() {
		this(1, 10, 123);
	}
	
	public Criteria(int pageNumber, int amount) {
		this(pageNumber, amount, 123);
	}
	
	public Criteria(int pageNumber, int amount, int total) {
		this.pageNumber = pageNumber;
		this.amount = amount;
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
}
