package www.dream.com.aop_study.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SomeService {
	public int doAdd(int a, int b) {
		return a + b;
	}
	
	public int doBdd(int a, int b) {
		return a + b;
	}
	
	public int doCdd(int a, int b) {
		return a + b;
	}
}
