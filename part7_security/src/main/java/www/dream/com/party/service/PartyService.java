package www.dream.com.party.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import www.dream.com.party.model.Party;
import www.dream.com.party.persistence.PartyMapper;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PartyService implements UserDetailsService {
	@NonNull 
	private PartyMapper partyMapper;
	
	public List<Party> getList() {
		return partyMapper.getList();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return partyMapper.findPartyByUserId(username);
	}
}
