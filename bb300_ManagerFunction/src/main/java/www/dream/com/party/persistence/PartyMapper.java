package www.dream.com.party.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import www.dream.com.party.model.AuthorityPartyType;
import www.dream.com.party.model.ContactAllergyType;
import www.dream.com.party.model.ContactTypeVO;
import www.dream.com.party.model.Member;
import www.dream.com.party.model.Party;

/**
 * Mybatis를 활용하여 Party 종류의 객체를 관리하는 인터페이스
 * @author ivarBae
 */
public interface PartyMapper {
	//함수 정의 순서 LRCUD
	//목록 조회
	public List<Party> getList();
	public int IDDuplicateCheck(@Param("userId") String userId);
	//개별 객체 조회
	public Party findPartyByUserId(String userId);
	//Insert
	public int setPwd(Party p);
	//update
	//Delete
	/** ----------------------회원관리 관련 정의 영역 ---------------------*/
	public void joinMember(@Param("party") Member member);
	
	/** -------------연락처 관련 정의 영역 -------------*/
	public List<ContactTypeVO> getCPTypeList();
	
	/** -------------권한 관련 정의 영역 -------------*/
	public AuthorityPartyType getMemberType();
	
	
}








