package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원
 * @author YongHoon Kim
 */
@Data
@NoArgsConstructor
public class Member extends Party {
	private static List<AuthorityVO> listAuthority = new ArrayList<>();
	static {
		listAuthority.add(new AuthorityVO("user"));
	}
	
	@Override
	public List<AuthorityVO> getAuthorityList() {
		return listAuthority;
	}
	
	public Member(String userId) {
		super(userId);
	}
}
