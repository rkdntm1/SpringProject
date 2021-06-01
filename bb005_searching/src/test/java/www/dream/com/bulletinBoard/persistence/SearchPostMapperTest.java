package www.dream.com.bulletinBoard.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import www.dream.com.bulletinBoard.dto.PostCriteria;
import www.dream.com.common.dto.Criteria;
 
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src\\main\\webapp\\WEB-INF\\spring\\root-context.xml") //여기에 들어있는정보를바탕으로 객체를 만들어라 junit아
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchPostMapperTest {
	@Autowired
	private PostMapper postMapper;
	
	//@Test
	public void test040GetList() {
		assertNotNull(postMapper);
		Map<String, String> map = new HashMap<>();
		map.put("T", "여기는 집");
		//map.put("C", "하세요");
		try {
			postMapper.getListBySearch(3, map).forEach(post->{
				System.out.println(post);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test042GetList() {
		assertNotNull(postMapper);
		PostCriteria cri = new PostCriteria();
		cri.setAmount(10);
		cri.setPageNumber(2);
		cri.setType("TC");
		cri.setKeyword("여기는 집");
		try {
			postMapper.getListBySearchWithPaging(3, cri).forEach(post->{
				System.out.println(post);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test044GetList() {
		assertNotNull(postMapper);
		PostCriteria cri = new PostCriteria();
		cri.setAmount(10);
		cri.setPageNumber(2);
		try {
			postMapper.getListBySearchWithPaging(3, cri).forEach(post->{
				System.out.println(post);});	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void test045GetList() {
		assertNotNull(postMapper);
		PostCriteria cri = new PostCriteria();
		cri.setAmount(10);
		cri.setPageNumber(2);
		try {
			System.out.println(postMapper.getTotalCountBySearch(3, cri));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
