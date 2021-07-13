package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 시스템 운영자
 * @author ivarBae
 */
@Data
@NoArgsConstructor
public class Manager extends Party {
	private static List<AuthorityVO> listAuthority = new ArrayList<>();
	static {
		listAuthority.add(new AuthorityVO("manager"));
		listAuthority.add(new AuthorityVO("user"));
	}
	@Override
	public List<AuthorityVO> getAuthorityList() {
		return listAuthority;
	}
	public Manager(String userId) {
		super(userId);
	}

}
