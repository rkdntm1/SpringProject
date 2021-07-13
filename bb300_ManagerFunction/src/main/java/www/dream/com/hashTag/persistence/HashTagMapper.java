package www.dream.com.hashTag.persistence;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import www.dream.com.hashTag.model.HashtagVO;
import www.dream.com.hashTag.model.IHashTagOpponent;

public interface HashTagMapper {
	/**
	 * setHashTag로 받은 단어 중에 기존에 관리하고 있는 단어 집합 찾기
	 * @param setHashTag isEmpty() false일때만 불러주세요
	 * @return
	 */
	public Set<HashtagVO> findExisting(@Param("setHashTag") Set<String> setHashTag);
	public Set<HashtagVO> findPrevUsedHashTag(@Param("opponent") IHashTagOpponent hashTagOpponent, @Param("curSearch") Set<String> curSearch);
	/**
	 * Hashtag와 Post 사이의 관계 정보 다중입력(고성능) 입력
	 * @param setExisting	Table에 존재하는 단어의 ID
	 * @param opponentId	상대방 타입
	 * @param opponentType	상대방 ID
	 * @return
	 */
	public int insertMapBetweenStringId(@Param("setExisting") Set<HashtagVO> setExisting, @Param("opponent") IHashTagOpponent opponent);
	
	/**
	 * HashtagVOdml id를 지정한 개수만큼 Sequence를 통하여 한번에 왕창 만들기. 성능
	 * @param cnt
	 * @return
	 */
	public String getIds(int cnt);
	public int createHashTag(@Param("setNewHashTag") Set<HashtagVO> newHashtag);

	public void deleteMapBetweenStringId(@Param("opponent") IHashTagOpponent opponent);
	public void deleteMapBetweenOpponent(@Param("setPrevUsed") Set<HashtagVO> setPrevUsed, @Param("opponent") IHashTagOpponent opponent);

	


}
