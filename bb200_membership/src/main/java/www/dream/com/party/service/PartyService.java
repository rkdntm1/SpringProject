package www.dream.com.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import www.dream.com.framework.springSecurityAdaper.CustomUser;
import www.dream.com.party.model.ContactPointTypeVO;
import www.dream.com.party.model.Party;
import www.dream.com.party.persistence.PartyMapper;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PartyService implements UserDetailsService {
	@Autowired 
	private PartyMapper partyMapper;
	
	public List<Party> getList() {
		return partyMapper.getList();
	}

	//연락처유형 조회
	public List<ContactPointTypeVO> getCPTypeList() {
		return partyMapper.getCPTypeList();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Party loginParty = partyMapper.findPartyByUserId(username);
		return loginParty == null? null : new CustomUser(loginParty);
	}

	public int checkDuplication(String partyId) {
		return partyMapper.checkDuplication(partyId);
	}
}
