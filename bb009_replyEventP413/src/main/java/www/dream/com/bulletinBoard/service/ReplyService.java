package www.dream.com.bulletinBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.ReplyVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.dto.Criteria;

@Service
public class ReplyService {
	@Autowired
	private ReplyMapper replyMapper;
	
	public List<ReplyVO> getReplyListWithPaging(String originalId, Criteria cri) {
		int idLength = originalId.length() + ReplyVO.ID_LENGTH;
		return replyMapper.getReplyListWithPaging(originalId, idLength, cri);
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
