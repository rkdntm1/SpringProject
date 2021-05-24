package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;

/**
 * 게시판 
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
public class BoardVO extends CommonMngVO {
	private int id; //아이디
	private String name; //게시판 이름
	private String description; //게시판 설명
	
	public BoardVO(int id) { //강제적으로 생성자를 만드는순간 @Data의 기본생성자가 사라짐-> @NoArgsConstructor
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "BoardVO [아이디=" + id + ", 게시판이름=" + name
				+ ", 설명=" + description + ", "
				+ super.toString() + "]";
	}
}
