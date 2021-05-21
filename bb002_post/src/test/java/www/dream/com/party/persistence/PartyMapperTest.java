package www.dream.com.party.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml") //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PartyMapperTest {
	@Autowired
	private PartyMapper partyMapper;
	
	@Test
	public void testGetList() {
		assertNotNull(partyMapper);
		try {
			partyMapper.getList().forEach(p->{System.out.println(p);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
