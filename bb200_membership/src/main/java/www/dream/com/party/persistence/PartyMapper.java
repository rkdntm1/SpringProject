package www.dream.com.party.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.party.model.ContactPoint;
import www.dream.com.party.model.ContactPointTypeVO;
import www.dream.com.party.model.Party;
import www.dream.com.party.model.Member;

/**
 * Mybatis를 활용하여 Party 종류의 객체를 관리하는 인터페이스
 * @author YongHoon Kim
 */
public interface PartyMapper {
	//함수 정의 순서 LRCUD
	//1.목록 조회
	public List<Party> getList(); //table에 들어있는 모든 정보
	public int checkDuplication(@Param("partyId") String partyId);
	//2.개별 객체 조회
	public Party findPartyByUserId(String userId);
	//3.Insert
	//4.Update
	public int setPwd(Party p);
	//5.Delete
	
	/** 연락처 관련 정의 영역 */
	public List<ContactPointTypeVO> getCPTypeList();
	

}
