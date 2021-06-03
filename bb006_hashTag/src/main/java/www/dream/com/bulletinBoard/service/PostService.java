package www.dream.com.bulletinBoard.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.PostMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framwork.langPosAnalyzer.PosAnalyzer;
import www.dream.com.framwork.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.persistence.HashTagMapper;

@Service
public class PostService {
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private HashTagMapper hashTagMapper;

	// LRCUD
	public long getTotalCount(int boardId) {
		return postMapper.getTotalCount(boardId);
	}
	
	/*
	 * mapper 함수의 인자 개수가 여러개 일 경우 필수적으로 @Param을 넣야합니다. 이를 실수하지 않기 위하여 한개여도 그냥 명시적으로
	 * 넣어주자
	 */
	public List<PostVO> getList(int boardId, Criteria cri) {
		return postMapper.getList(boardId, cri);
	}

	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return postMapper.findPostById(id);
	}

	public int insert(BoardVO board, PostVO post) {
		int affectedRows = postMapper.insert(board, post);
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post); // post 객체를 던져줘서 HashTag를 찾아 반복수를 map으로 짝지어준다. 
		Set<String> setHashTag = mapOccur.keySet(); // hastag들의 집합을 만들어 놓는다.
		createHashTagAndMapping(post, mapOccur, setHashTag);
		return affectedRows;
	}

	private void createHashTagAndMapping(PostVO post, Map<String, Integer> mapOccur, Set<String> setHashTag) {
		if (setHashTag.isEmpty())  //해쉬태그를 담아놓은 setHashTag 집합이 비어있다면
			// 게시글에서 단어가 나타나지 않았으면 처리할 것이 없다.
			return;
		
		// 넣은 정보를 바탕으로 기존의 해쉬태그 정보가 있는지 확인하여 그 정보를 집합으로 만들어줌
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag); 
		//기존에 있는 것들과는 짝 지어 주어야합니다.
		for (HashtagVO hashtag : setExisting) {
			//기존에 단어가 들어가있으면 그 단어를 가져와서(setExisting를 반복돌려) 그 단어의 빈도수를 가져와 Set 함
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag()));    
		}
		
		//setHashTag에 남은 것들은 신규 처리해야할 것들입니다.
		for (HashtagVO hashtag : setExisting) {
			setHashTag.remove(hashtag.getHashtag()); //기존 단어들을 제거
		}
		// 기존 단어를 없애고 나서의 단어집을 집합으로 만들어줌
		Set<String> setNewHashTag = setHashTag;
		
		if (! setNewHashTag.isEmpty()) {
			//새로운 단어를 HashTag 테이블에 등록해줍니다.
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			int idx = 0;
			Set<HashtagVO> listNewHashTag = new HashSet<>();
			for (String hasTag : setNewHashTag) {
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hasTag);
				listNewHashTag.add(newHashtag);
			}
			hashTagMapper.createHashTag(listNewHashTag); //신규단어가 들어감
			//새 단어를 단어집에 넣었으니 기존 단어가 된 것입니다.
			setExisting.addAll(listNewHashTag);
		}
			hashTagMapper.insertMapBetweenPost(setExisting, post.getId());	//기존 단어 및 신규 단어 와 짝짓기
		}
	


	/** 게시글 수정 처리 */
	public boolean updatePost(PostVO post) {
		return postMapper.updatePost(post) == 1;
	}

	/** id 값으로 Post 객체 삭제 */
	public boolean deletePostById(String id) {
		return postMapper.deletePostById(id) == 1;
	}
}
