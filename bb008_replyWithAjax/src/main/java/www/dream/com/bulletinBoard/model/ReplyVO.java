package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
public class ReplyVO extends CommonMngVO {
	public static final String DESCRIM4REPLY = "reply";
	/** DB 함수 get_id 참조 */
	public static final int ID_LENGTH = 5;
	
	private String id;		//아이디
	@HashTarget
	private String content;	//내용
	
	//navigabillity 항해가능성 - 객체 접근성
	@HashTarget 
	private Party writer;
	
	public ReplyVO(String parentId, String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	public ReplyVO(String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	@Override
	public String toString() {
		return "ReplyVO [id=" + id + ", content=" + content 
				+ ", writer=" + writer 
				+ ", " + super.toString() + "]";
	}
}
