package www.dream.com.framwork.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import www.dream.com.framwork.langPosAnalyzer.PosAnalyzer;
import www.dream.com.framwork.util.ComparablePair;

public class TestPosAnalyzer {

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
