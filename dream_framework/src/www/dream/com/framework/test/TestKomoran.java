package www.dream.com.framework.test;

import java.util.List;

import org.junit.Test;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import www.dream.com.framework.langPosAnalyzer.TargetPos;

public class TestKomoran {
	@Test
	public void test() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		String strToAnalyze = "아버지가 Room에 들어가신다. 아버지는 품에 네오를 안고 계신다";

		KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

		System.out.println(analyzeResultList.getPlainText());

		List<Token> tokenList = analyzeResultList.getTokenList();
		for (Token token : tokenList) {
			TargetPos dd = null;
			try {
				dd = TargetPos.valueOf(token.getPos());
			} catch (Exception e) {
				
			}
			if (dd != null) {
				System.out.format("%s/%s\n", token.getMorph(), token.getPos());
			}
		}
	}
}
