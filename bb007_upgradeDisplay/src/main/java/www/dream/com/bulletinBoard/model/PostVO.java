package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framwork.langPosAnalyzer.HashTarget;
import www.dream.com.framwork.printer.ClassHeaderTarget;
import www.dream.com.framwork.printer.HeaderTarget;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
@ClassHeaderTarget
public class PostVO extends CommonMngVO {
	/** DB 함수 get_id 참조 */
	public static final int ID_LENGTH = 5;
	
	private String id;		//아이디
	@HashTarget @HeaderTarget(order=100, caption="제목")
	private String title;	//제목
	@HashTarget
	private String content;	//내용
	@HeaderTarget(order=300, caption="조회수")
	private int readCnt;	
	private int likeCnt;	//좋아요수
	private int	dislikeCnt;	//싫어요수
	
	//navigabillity 항해가능성 - 객체 접근성
	@HashTarget 
	private Party writer;
	
	public PostVO(String title, String content, Party writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
	
	@Override
	public String toString() {
		return "PostVO [id=" + id + ", title=" + title + ", content=" + content 
				+ ", readCnt=" + readCnt + ", likeCnt=" + likeCnt 
				+ ", dislikeCnt=" + dislikeCnt + ", writer=" + writer 
				+ ", " + super.toString()
				+ "]";
	}
}
