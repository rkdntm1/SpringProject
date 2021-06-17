package www.dream.com.bulletinBoard.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.model.CommonMngVO;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.AnchorTarget;
import www.dream.com.framework.printer.ClassPrintTarget;
import www.dream.com.framework.printer.PrintTarget;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
@ClassPrintTarget
public class ReplyVO extends CommonMngVO {
	public static final String DESCRIM4REPLY = "reply";
	/** DB 함수 get_id 참조 */
	public static final int ID_LENGTH = 5;
	
	@AnchorTarget
	private String id;		//아이디
	@HashTarget @PrintTarget(order=130, caption="내용")
	private String content;	//내용
	
	private int replyCnt = 0;	
	
	//navigabillity 항해가능성 - 객체 접근성
	@HashTarget 
	private Party writer;
	
	private List<ReplyVO> listReply = new ArrayList<>();
	
	public ReplyVO(String parentId, String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	public ReplyVO(String content, Party writer) {
		this.content = content;
		this.writer = writer;
	}
	
	public int getDepth() {
		return id.length() / ID_LENGTH;
	}
	
	public String getOrginalId() {
		return id.substring(0, id.length() - ID_LENGTH);
	}
	
	@Override
	public String toString() {
		return "ReplyVO [id=" + id + ", content=" + content 
				+ ", writer=" + writer 
				+ ", " + super.toString() + "]";
	}
	
	/**
	 * Query 를 통하여 정보를 읽을 때는 목록으로만 가능 
	 * @param listFromDB
	 * @return
	 */
	public static List<ReplyVO> buildCompositeHierachy(List<ReplyVO> listFromDB) {
		List<ReplyVO> ret = new ArrayList<>();
		
		Map<String, ReplyVO> map = new HashMap<>();
		for (ReplyVO reply : listFromDB) {
			if (reply.getDepth() ==3)
				ret.add(reply);
			map.put(reply.getId(), reply);
			if (map.containsKey(reply.getOrginalId())) {
				map.get(reply.getOrginalId()).getListReply().add(reply);
			}
		}
		return ret;
	}
}