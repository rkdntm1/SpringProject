package www.dream.com.bulletinBoard.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
public class PostVO extends ReplyVO {
	public static final String DESCRIM4POST = "post";
	
	@HashTarget
	private String title;	//제목
	private int readCnt;	//조회수
	private int likeCnt;	//좋아요수
	private int	dislikeCnt;	//싫어요수
	
	public PostVO(String title, String content, Party writer) {
		super(content, writer);
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "PostVO ["+ ToStringSuperHelp.trimSuperString(super.toString()) 
				+ ", title=" + title + ", readCnt=" + readCnt + ", likeCnt=" + likeCnt 
				+ ", dislikeCnt=" + dislikeCnt + "]";
	}
}
