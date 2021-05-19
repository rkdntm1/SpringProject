package www.dream.com.party.persistence;

import java.util.List;

import www.dream.com.party.model.Party;

/**
 * Mybatis를 활용하여 Party 종류의 객체를 관리하는 인터페이스
 * @author YongHoon Kim
 */
public interface PartyMapper {
	//함수 정의 순서 LRCUD
	//1.목록 조회
	public List<Party> getList(); //table에 들어있는 모든 정보
	//2.개별 객체 조회
	//3.Insert
	//4.Update
	//5.Delete
}
