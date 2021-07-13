package www.dream.com.bulletinBoard.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.attachFile.model.AttachFileVO;
import www.dream.com.common.attachFile.persistence.AttachFileVOMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.hashTag.service.HashTagService;
import www.dream.com.party.model.Party;

/**
 * ReplyVO와 PostVO의 클래스 설계도를 기반으로
 * 해당 Table을 Top-전략으로 통합하여 만들었기에
 * ReplyMapper는 통합해 놓았다.
 * PostService와 ReplyService와 분리하였다.
 * @author ivarBae
 */
@Service
public class PostService {
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private HashTagService hashTagService;
	
	@Autowired
	private AttachFileVOMapper attachFileVOMapper;

	//LRCUD
	public long getSearchTotalCount(int boardId, Criteria cri) {
		if (cri.hasSearching()) {
			return replyMapper.getSearchTotalCount(boardId, cri);
		} else {
			return replyMapper.getTotalCount(boardId, PostVO.DESCRIM4POST);
		}
	}
	
	/* mapper 함수의 인자 개수가 여러개 일 때는 필수적으로 @Param을 넣어야 합니다
	 * 이를 실수하지 않기 위하여 한개여도 그냥 명시적으로 넣어 주세요 */
	public List<PostVO> getListByHashTag(Party curUser, int boardId, Criteria cri) {
		if (cri.hasSearching()) {
			String[] searchingHashtags = cri.getSearchingHashtags();
			if(curUser != null) {
				mngPersonalFavorite(curUser,searchingHashtags);
			}
			return replyMapper.getListByHashTag(boardId, cri);
		} else {
			if(curUser == null) {
				return replyMapper.getList(boardId, cri);
			} else {
				//개인화서비스 기준점이 아닌 유저를 줘서 해쉬태그검색
				return replyMapper.getFavorite(boardId, curUser); 
			}

		}
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return (PostVO) replyMapper.findReplyById(id);
	}
	
	
	@Transactional
	public int insert(BoardVO board, PostVO post) {
		int affectedRows = replyMapper.insert(board, post);
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post);
		hashTagService.createHashTagAndMapping(post, mapOccur);
		
		//첨부 파일 정보를 관리합니다. 고성능 
		List<AttachFileVO> listAttach = post.getListAttach();
		if(listAttach != null && ! listAttach.isEmpty()) {
			attachFileVOMapper.insert(post.getId(), listAttach);
		}
		return affectedRows;
	}


	/** 게시글 수정 처리 
	 * 첨부파일정보,연관 단어 기존 정보 전부 삭제 후 다시 등록
	 */
	@Transactional
	public boolean updatePost(PostVO post) {
		attachFileVOMapper.delete(post.getId());
		
		//첨부 파일 정보를 관리합니다. 고성능 
		List<AttachFileVO> listAttach = post.getListAttach();
		if(listAttach != null && ! listAttach.isEmpty()) {
			attachFileVOMapper.insert(post.getId(), listAttach);
		}
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post);
		hashTagService.deleteMap(post);
		hashTagService.createHashTagAndMapping(post, mapOccur);
		
		return replyMapper.updatePost(post) == 1;
	}
	
	/** id 값으로 Post 객체 삭제 */
	@Transactional
	public boolean deletePostById(String id) {
		PostVO post = new PostVO();
		post.setId(id);
		hashTagService.deleteMap(post);
		attachFileVOMapper.delete(id);
		return replyMapper.deleteReplyById(id) == 1;
	}
	
	private void mngPersonalFavorite(Party curUser, String[] searchingHashtags) {
		Map<String, Integer> mapOccur = new HashMap<>();
		Arrays.stream(searchingHashtags).forEach(word->{
			mapOccur.put(word, 1);
		});

		//신규 단어는 단어 등록 후 occur_cnt 1로 설정
		hashTagService.mngHashTagAndMapping(curUser,mapOccur);
		
	}
}
