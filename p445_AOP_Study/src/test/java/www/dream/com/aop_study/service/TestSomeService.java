package www.dream.com.aop_study.service;

import static org.junit.Assert.*;

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
public class TestSomeService {
	@Autowired
	private SomeService someService; 
	
	@Test
	public void test() {
		System.out.println(someService.doAdd(2, 3));
		System.out.println(someService.doBdd(44, 77));
		System.out.println(someService.doCdd(1232, 3351));
	}
}
