package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.util.ComparablePair;

@Service
public class ReplyService {
	@Autowired
	private ReplyMapper replyMapper;

	public int getCountOfReply(String replyId) {
		int idLength = replyId.length() + ReplyVO.ID_LENGTH;
		return replyMapper.getAllReplyCount(replyId, idLength);
	}
	
	public ComparablePair<Criteria, List<ReplyVO>> getReplyListWithPaging(String originalId,
			Criteria cri) {
		int idLength = originalId.length() + ReplyVO.ID_LENGTH;
		cri.setTotal(replyMapper.getReplyCount(originalId, idLength));
		ComparablePair<Criteria, List<ReplyVO>> ret = new ComparablePair<>(cri, replyMapper.getReplyListWithPaging(originalId, idLength, cri));
		return ret;
	}
	
	public List<ReplyVO> getReplyListOfReply(String replyId) {
		int idLength = replyId.length() + ReplyVO.ID_LENGTH;
		List<ReplyVO> justRead = replyMapper.getReplyListOfReply(replyId, idLength);
		return ReplyVO.buildCompositeHierachy(justRead);
		//return justRead;
	}
				
				
	public ReplyVO findReplyById(String id) {
		return replyMapper.findReplyById(id);
	}
	
	public int insertReply(String originalId, ReplyVO reply) {
		return replyMapper.insertReply(originalId, reply);
	}
	
	public int updateReply(ReplyVO reply) {
		return replyMapper.updateReply(reply);
	}
	
	public int deleteReplyById(String id) {
		return replyMapper.deleteReplyById(id);
	}
}
