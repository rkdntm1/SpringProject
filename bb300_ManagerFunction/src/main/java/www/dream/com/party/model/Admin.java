package www.dream.com.party.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사장. 운영자 등록, 삭제 권한 있음
 * @author ivarBae
 */
@Data
@NoArgsConstructor
public class Admin extends Party {
	private static List<AuthorityVO> listAuthority = new ArrayList<>();
	static {
		listAuthority.add(new AuthorityVO("admin"));
		listAuthority.add(new AuthorityVO("manager"));
		listAuthority.add(new AuthorityVO("user"));
	}
	@Override
	public List<AuthorityVO> getAuthorityList() {
		return listAuthority;
	}
	public Admin(String userId) {
		super(userId);
	}

	@Override
	public String toString() {
		return "Admin [toString()=" + super.toString() + "]";
	}
}
