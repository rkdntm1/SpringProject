package www.dream.com.hashTag.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashTagOpponent;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.persistence.HashTagMapper;
import www.dream.com.party.model.Party;

@Service
public class HashTagService {
	@Autowired
	private HashTagMapper hashTagMapper;
	
	/**
	 * 
	 * @param hashTagOpponent 상대
	 * @param mapOccur 단어와 출현 횟수
	 */
	public void createHashTagAndMapping(HashTagOpponent hashTagOpponent,
			Map<String, Integer> mapOccur) {
		Set<String> setHashTag = mapOccur.keySet(); // HashTag와 빈도수를 짝지어놓은 Map에 .keySet() 통하여 key값들, 즉 HashTag들을 가져와 집합으로 만들어준다.
		
		if (setHashTag.isEmpty())  //해쉬태그를 담아놓은 setHashTag 집합이 비어있다면 리턴처리
			return;
		
		// @Service를 안쓰고 static일시 context에 없는 객체라 nullpoint오류발생
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
				newHashtag.setOccurCnt(mapOccur.get(hasTag));
				listNewHashTag.add(newHashtag);
			}
			hashTagMapper.createHashTag(listNewHashTag); //신규단어가 들어감
			//새 단어를 단어집에 넣었으니 기존 단어가 된 것입니다.
			setExisting.addAll(listNewHashTag);
		}
		
		//기존 단어 및 신규 단어 와 짝짓기
		hashTagMapper.insertMapBetweenStringId(setExisting, hashTagOpponent);
	}
	
	public void deleteMap(HashTagOpponent hashTagOpponent) {
		hashTagMapper.deleteMapBetweenStringId(hashTagOpponent);
	}

	/**
	 * 기존에 검색한 단어는 활용 횟수 올려주기
	 * 신규 단어는 단어 새롭게 만들고 횟수는 1로 넣어줌.
	 * @param curUser
	 * @param mapSearchWord
	 */
	public void mngHashTagAndMapping(Party curUser, Map<String, Integer> mapSearchWord) {
		Set<String> setSearchword = mapSearchWord.keySet(); // HashTag와 빈도수를 짝지어놓은 Map에 .keySet() 통하여 key값들, 즉 HashTag들을 가져와 집합으로 만들어준다.
		
		if (setSearchword.isEmpty())  //해쉬태그를 담아놓은 setHashTag 집합이 비어있다면 리턴처리
			return;
		
		//기존에 검색에서 활용되었고 활용 횟수 1 증가 시킬 대상
		Set<HashtagVO> setPrevUsed = hashTagMapper.findPrevUsedHashTag(curUser, setSearchword); // DB에 등록된 기존 해쉬태그 와 일치하는 해쉬태그를 가진 객체들의 집합
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setSearchword); // DB에 등록된 기존 해쉬태그 와 일치하는 해쉬태그를 가진 객체들의 집합
		
		//기존에 있는 것들은 사용횟수 올리기
		for (HashtagVO hashtag : setPrevUsed) {
			//객체의 해쉬태그를 가져와서, 그 해쉬태그의 빈도수를 가져와서, 그 빈도수를 객체의 occurCnt 속성에 Set 시킴.
			hashtag.setOccurCnt(mapSearchWord.get(hashtag.getHashtag()));    
		}
		
		//setHashTag에 남은 것들은 신규 처리해야할 것들입니다.
		for (HashtagVO hashtag : setExisting) {
			setSearchword.remove(hashtag.getHashtag()); //기존에 있는 해쉬태그의 정보를 가지고 입력된 전체 해쉬태그에서 제거 시킴 => 신규만 남음
		}
		
		// 검색에서 새롭게 나타난 단어. 새 단어 등록해 주는 이유는 게시글이나 상품이 새롭게 등장하면 즉시 검색되도록 합니다.
		//setNewHashTag 변수명은 가독성을 위하여 setSearchword을 대체하는 것. 이 아래에서 setSearchword 변수 사용 금지.
		Set<String> setNewHashTag = setSearchword;
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
				newHashtag.setOccurCnt(mapSearchWord.get(hasTag));
				listNewHashTag.add(newHashtag);
			}
			hashTagMapper.createHashTag(listNewHashTag); //신규단어가 들어감
			//새 단어를 단어집에 넣었으니 기존 단어가 된 것입니다.
			setPrevUsed.addAll(listNewHashTag);
		}
		//관리되던 단어가 검색에서 새롭게 나타남
		setPrevUsed.addAll(setExisting);
		
		//기존 활용 횟수 정보 없애기
		hashTagMapper.deleteMapBetweenOpponentStringId(setPrevUsed, curUser);
		//기존 단어 및 신규 단어 와 짝짓기
		hashTagMapper.insertMapBetweenStringId(setPrevUsed, curUser);
	}
	
}
