package www.dream.com.framework.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;
import www.dream.com.framework.util.ComparablePair;

public class TestPosAnlyzer {
	
	@Test
	public void test() {
		Post post = new Post();
		post.setTitle("질문");
		post.setContent("선생님아버지어머니누나형동생");
		Party writer = new Party();
		writer.setName("홍길동");
		post.setWriter(writer);
		ContactPoint cp = new ContactPoint();
		cp.setInfo("서울특별시 구로구 가산디지털");
		writer.addCP(cp);
		cp = new ContactPoint();
		cp.setInfo("rkdntm4@naver.com");
		writer.addCP(cp);
		
		Map<String, Integer> map = PosAnalyzer.getHashTags(post);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
}
