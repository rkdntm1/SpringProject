package www.dream.com.testFramework;

import java.util.Map;

import org.junit.Test;

import www.dream.com.bulletinBoard.model.PostVO;
import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.party.model.Admin;
import www.dream.com.party.model.ContactPoint;

public class TestPosAnlyzer {
	
	@Test
	public void test() {
		PostVO post = new PostVO();
		post.setTitle("질문");
		post.setContent("선생님아버지어머니누나형동생");
		Admin writer = new Admin();
		writer.setName("홍길동");
		post.setWriter(writer);
		
		ContactPoint cp = new ContactPoint();
		cp.setInfo("서울특별시 구로구 가산디지털");
		writer.addContactPoint(cp);
		cp = new ContactPoint();
		cp.setInfo("rkdntm4@naver.com");
		writer.addContactPoint(cp);
		
		Map<String, Integer> map = PosAnalyzer.getHashTags(post);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
}
