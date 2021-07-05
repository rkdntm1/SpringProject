package www.dream.com.bulletinBoard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ctc.wstx.util.StringUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.framework.langPosAnalyzer.HashTarget;
import www.dream.com.framework.printer.ClassPrintTarget;
import www.dream.com.framework.printer.PrintTarget;
import www.dream.com.framework.util.ToStringSuperHelp;
import www.dream.com.party.model.Party;

/**
 * 게시글
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
@ClassPrintTarget
public class PostVO extends ReplyVO {
	public static final String DESCRIM4POST = "post";
	
	@HashTarget 
	private String title;	//제목
	@PrintTarget(order=300, caption="조회수")
	private int readCnt;	//조회수
	private int likeCnt;	//좋아요수
	private int	dislikeCnt;	//싫어요수
	
	private List<String> listAttachInStringFormat;
	private List<AttachFileVO> listAttach;
	
	public PostVO(String title, String content, Party writer) {
		super(content, writer);
		this.title = title;
	}
	
	@PrintTarget(order=100, caption="제목", withAnchor=true)
	public String getTitleWithCnt() {
		return title + "[" + super.replyCnt + "]";
	}
	
	public List<String> getAttachListInGson() {
		List<String> ret = new ArrayList<>();
		ret.addAll(listAttach.stream().map(vo -> vo.getJson()).collect(Collectors.toList()));
		return ret;
	}
	
	public void parseAttachInfo() {
		if (listAttach == null) {
			listAttach = new ArrayList<>();
		}
		
		if (listAttachInStringFormat != null) {
			for (String ai : listAttachInStringFormat) {
				listAttach.add(AttachFileVO.fromJson(ai));
			}	
		}
	}
	
	@Override
	public String toString() {
		return "PostVO ["+ ToStringSuperHelp.trimSuperString(super.toString()) 
				+ ", title=" + title + ", readCnt=" + readCnt + ", likeCnt=" + likeCnt 
				+ ", dislikeCnt=" + dislikeCnt + "]";
	}
}