package www.dream.com.testKomoran;

import java.util.List;

import org.junit.Test;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

public class TestKomoran {
	private enum TargetPos { NNG, NNP, SL, SH };

	@Test
	public void test() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		String strToAnalyze = "아버지가방에들어가신다";

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
