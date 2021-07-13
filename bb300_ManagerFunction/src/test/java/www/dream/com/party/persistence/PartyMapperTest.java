package www.dream.com.party.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.party.model.Party;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration({"file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml",
	"file:src\\main\\webapp\\WEB-INF\\spring\\security-context.xml"}) //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyMapperTest {
	@Autowired
	private PasswordEncoder pwEncoder;
	@Autowired
	private PartyMapper partyMapper;
	
	@Test
	public void test100GetList() {
		assertNotNull(partyMapper);
		try {
			partyMapper.getList().forEach(p->{System.out.println(p);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test200FindPartyByUserId() {
		try {
			Party found = partyMapper.findPartyByUserId("admin");
			found.getAuthorityList().forEach(auth-> {System.out.println(auth);});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test440SetPwd() {
		try {
			partyMapper.getList().forEach(p->{
				p.setUserPwd(pwEncoder.encode("1234"));
				partyMapper.setPwd(p);
			});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}