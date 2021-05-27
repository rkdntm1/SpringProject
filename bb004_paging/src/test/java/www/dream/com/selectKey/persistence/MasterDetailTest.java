package www.dream.com.selectKey.persistence;

import java.util.Calendar;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.selectKey.model.DetailVO;
import www.dream.com.selectKey.model.MasterVO;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml") //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MasterDetailTest {
	@Autowired
	private MasterDetail masterDetailMapper;
	
	@Test
	public void testInsertMaster() {
		try {
			MasterVO newBie = new MasterVO(); //신규회원등록
			newBie.setName("newBie");	//신규회원에 이름 등록
			Date now = Calendar.getInstance().getTime(); //시간 정보
			newBie.setReg_dt(now); 		//신규회원에 등록시간 등록
			masterDetailMapper.insertMaster(newBie);
			int idOFNew = masterDetailMapper.findByName("newBie", now);
			
			DetailVO addr = new DetailVO(); //주소 정보도 함께 등록
			addr.setInfo("address 대강대충");
			masterDetailMapper.insertDetail(idOFNew, addr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertMasterBySelectKey() {
		try {
			MasterVO newBie = new MasterVO(); //신규회원등록
			newBie.setName("newBie");	//신규회원에 이름 등록
			masterDetailMapper.insertMasterBySelectKey(newBie);
			
			DetailVO addr = new DetailVO(); //주소 정보도 함께 등록
			addr.setInfo("address 대강대충");
			masterDetailMapper.insertDetail(newBie.getId(), addr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
