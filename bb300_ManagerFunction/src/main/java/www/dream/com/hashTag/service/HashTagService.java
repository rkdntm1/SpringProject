package www.dream.com.hashTag.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.dream.com.framework.util.StringUtil;
import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.model.IHashTagOpponent;
import www.dream.com.hashTag.persistence.HashTagMapper;
import www.dream.com.party.model.Party;

@Service
public class HashTagService {
	@Autowired
	private HashTagMapper hashTagMapper;
	/**
	 * hashTagOpponent 상대
	 * mapOccur 단어와 출현 횟수
	 * @author qoddn987
	 *
	 */
	public void createHashTagAndMapping(IHashTagOpponent hashTagOpponent, Map<String, Integer> mapOccur) {
		Set<String> setHashTag = mapOccur.keySet();
		if (setHashTag.isEmpty()) {
			//게시글에서 단어가 나타나지 않았으면 처리할 것이 없군요
			return;
		}
	
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setHashTag);
		//기존에 있는 것들과는 짝 지어 주어야합니다.
		for (HashtagVO hashtag : setExisting) {
			hashtag.setOccurCnt(mapOccur.get(hashtag.getHashtag()));
		}
		
		//setHashTag에 남은 것들은 신규 처리해야할 것들입니다.
		for (HashtagVO hashtag : setExisting) {
			setHashTag.remove(hashtag.getHashtag());
		}
		Set<String> setNewHashTag = setHashTag;
		if (! setNewHashTag.isEmpty()) {
			//새로운 단어를 HashTag 테이블에 등록해줍니다.
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			int idx = 0;
			Set<HashtagVO> setHT = new HashSet<>();
			for (String hashTag : setNewHashTag) {
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag);
				newHashtag.setOccurCnt(mapOccur.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT);
			//새 단어를 단어집에 넣었으니 기존 단어가 된 것입니다.
			setExisting.addAll(setHT);
		}

		//기존 단어 및 신규 단어와 짝짓기
			hashTagMapper.insertMapBetweenStringId(setExisting, hashTagOpponent);
			
	}
	
	public void deleteMap(IHashTagOpponent hashTagOpponent) {
		hashTagMapper.deleteMapBetweenStringId(hashTagOpponent);
	}
	
	/**
	 * 기존에 검색한 단어는 활용 횟수 올려주기
	 * 신규 단어는 단어 새롭게 만들고 횟수는 1
	 * @param curUser
	 * @param searchingHashtags
	 */
	public void mngHashTagAndMapping(Party curUser, Map<String, Integer> mapSearchTag) {
		Set<String> setSearchWord = mapSearchTag.keySet();
		if (setSearchWord.isEmpty()) {
			//게시글에서 단어가 나타나지 않았으면 처리할 것이 없군요
			return;
		}
		
		//기존에 검색에서 활용되었고 활용 횟수 1 증가 시킬 대상
		Set<HashtagVO> setPrevUsed = hashTagMapper.findPrevUsedHashTag(curUser, setSearchWord);
		//내가 검색한 단어에서 해쉬태그 테이블에 있는것만 골라옴
		Set<HashtagVO> setExisting = hashTagMapper.findExisting(setSearchWord);
		//기존에 있는 것들과는 짝 지어 주어야합니다.
		for (HashtagVO hashtag : setPrevUsed) {
			hashtag.setOccurCnt(mapSearchTag.get(hashtag.getHashtag()));
		}
		
		//setSearchWord에 남은 것들은 신규 처리해야할 것들입니다.
		for (HashtagVO hashtag : setExisting) {
			setSearchWord.remove(hashtag.getHashtag());
		}
		
		//검색에서 새롭게 나타난 단어. 새 단어 등록 해주는 이유는 게시글이나 상품이 새롭게 등장하면 즉시 검색되도록 합니다.
		//setNewHashTag은 setSearchWord의 쓰임새가 달라짐에 따라 가독성을 위해서 변수명을 바꿔줌. 
		Set<String> setNewHashTag = setSearchWord;
		if (! setNewHashTag.isEmpty()) {
			//새로운 단어를 HashTag 테이블에 등록해줍니다.
			int[] ids = StringUtil.convertCommaSepString2IntArr(hashTagMapper.getIds(setNewHashTag.size()));
			int idx = 0;
			Set<HashtagVO> setHT = new HashSet<>();
			for (String hashTag : setNewHashTag) {
				HashtagVO newHashtag = new HashtagVO(ids[idx++], hashTag);
				newHashtag.setOccurCnt(mapSearchTag.get(hashTag));
				setHT.add(newHashtag);
			}
			hashTagMapper.createHashTag(setHT);
			//새 단어를 단어집에 넣었으니 기존 단어가 된 것입니다.
			setPrevUsed.addAll(setHT);
		}
		//관리됐던 단어가 검색에서 새롭게 나타남
		setPrevUsed.addAll(setExisting);
		
		
		//기존 활용 횟수 정보 없얘기 
		hashTagMapper.deleteMapBetweenOpponent(setPrevUsed, curUser);
		//기존 단어 및 신규 단어와 짝짓기
		hashTagMapper.insertMapBetweenStringId(setPrevUsed, curUser);
	}

}
