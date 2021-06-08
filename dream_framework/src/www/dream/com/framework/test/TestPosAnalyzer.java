package www.dream.com.framework.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import www.dream.com.framework.langPosAnalyzer.PosAnalyzer;

public class TestPosAnalyzer {

	//@Test
	public void testString() {
		Map<String, Integer> map = PosAnalyzer.getHashTags("안녕하세요! 홍길동님");
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
	
	//@Test
	public void testList() {
		List<String> data = new ArrayList<>();
		data.add("안녕하세요! 홍길동님");
		data.add("홍길동전");
		data.add("조선시대");
		Map<String, Integer> map = PosAnalyzer.getHashTags(data);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}
	
	@Test
	public void test() {
		Post post = new Post();
		post.setTitle("질문");
		post.setContent("Setter에는 해쉬타겟을?");
		Party writer = new Party();
		writer.setName("홍길동");
		post.setWriter(writer);
		
		ContactPoint cp = new ContactPoint();
		cp.setInfo("서울특별시 구로구 가산디지털");
		writer.addCP(cp);
		cp = new ContactPoint();
		cp.setInfo("rkdntm1@gmail.com");
		writer.addCP(cp);
		
		Map<String, Integer> map = PosAnalyzer.getHashTags(post);
		for (String k : map.keySet()) {
			System.out.println(k + " : " + map.get(k));
		}
	}

}
