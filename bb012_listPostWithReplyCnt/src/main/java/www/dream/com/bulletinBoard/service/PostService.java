package www.dream.com.bulletinBoard.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.bulletinBoard.model.BoardVO;
import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.bulletinBoard.persistence.ReplyMapper;
import www.dream.com.common.dto.Criteria;
import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.persistence.HashTagMapper;

/**
 * ReplyVO와 PostVO의 클래스 설계도를 기반으로하는 것이며
 * 해당 Table을 Top-전략으로 통합하여 만들었기에 ReplyMapper는 통합해놓았고.
 * PostService를 ReplyService와 분리하였다.
 * @author YongHoon Kim
 */
@Service
public class PostService {
	@Autowired
	private ReplyMapper postMapper;
	
	@Autowired
	private HashTagMapper hashTagMapper;

	public long getSearchTotalCount(@Param("boardId") int boardId, @Param("cri") Criteria cri) {
		if (cri.hasSearching()) { // 검색이 있을때
			return postMapper.getSearchTotalCount(boardId, cri);	
		} else {
			return postMapper.getTotalCount(boardId, PostVO.DESCRIM4POST);	
		}
	}

	public List<PostVO> getListByHashTag(int boardId, Criteria cri) {
		if (cri.hasSearching()) { // 검색이 있을때
			return postMapper.getListByHashTag(boardId, cri);	
		} else {
			return postMapper.getList(boardId, cri);
		}
	}
	
	/** id 값으로 Post 객체 조회 */
	public PostVO findPostById(String id) {
		return (PostVO) postMapper.findReplyById(id);
	}
	
	/** 주어진 board객체와 post객체를 가지고 등록시킴 */
	public int insert(BoardVO board, PostVO post) {
		int affectedRows = postMapper.insert(board, post);
		Map<String, Integer> mapOccur = PosAnalyzer.getHashTags(post); // 입력해준 post 객체를 던져줘서 HashTag를 찾아 그 HashTag와 빈도수를 map으로 짝지어준다. 
		Set<String> setHashTag = mapOccur.keySet(); // HashTag와 빈도수를 짝지어놓은 Map에 .keySet() 통하여 key값들, 즉 HashTag들을 가져와 집합으로 만들어준다.
		createHashTagAndMapping(post, mapOccur, setHashTag);
		return affectedRows;
	}
	
	/** getHashTags를 통해 찾은 해쉬태그정보를 가져와서 신규 및 기존에 있는 단어들을 짝지어준다. */
	private void createHashTagAndMapping(PostVO post, Map<String, Integer> mapOccur, Set<String> setHashTag) {
		if (setHashTag.isEmpty())  //해쉬태그를 담아놓은 setHashTag 집합이 비어있다면 리턴처리
			return;
		
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag); // DB에 등록된 기존 해쉬태그 와 일치하는 해쉬태그를 가진 객체들의 집합
		
		//기존에 있는 것들과는 짝 지어 주어야합니다.
		for (HashtagVO hashtag : setExisting) {
			//객체의 해쉬태그를 가져와서, 그 해쉬태그의 빈도수를 가져와서, 그 빈도수를 객체의 occurCnt 속성에 Set 시킴.
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag()));    
		}
		
		//setHashTag에 남은 것들은 신규 처리해야할 것들입니다.
		for (HashtagVO hashtag : setExisting) {
			setHashTag.remove(hashtag.getHashtag()); //기존에 있는 해쉬태그의 정보를 가지고 입력된 전체 해쉬태그에서 제거 시킴 => 신규만 남음
		}
		
		// 신규만 남은 새로운 집합으로 만들어줌 (가독성을 위해서)
		Set<String> setNewHashTag = setHashTag;
		
		if (! setNewHashTag.isEmpty()) { // 신규 처리할게 있을경우
			//신규집합의 크기 정보로 그 개수 만큼 squence를 만들어내고 그 정보를 int형 배열로 만들어낸다.
			//ex)새로운 시퀀스가 150, 151, 152로 생성되었으면 이것을 [150, 151, 152] 이렇게 배열 형태로 만들어준다.
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			int idx = 0; // 만들어진 시퀀스 배열과 해쉬태그를 연결시키기 위한 index 
			Set<HashtagVO> listNewHashTag = new HashSet<>(); //신규객체를 담을 저장소
			
			for (String hasTag : setNewHashTag) {
				//ex) 신규 해쉬태그 : [물고기, 어항, 어부]
				//ex) 시퀀스 ids  : [150, 151, 152]				
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hasTag); // hastag객체의 id와 hashtag값을 짝지어 객체를 신규생성 
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
		return postMapper.deleteReplyById(id) == 1;
	}
}
