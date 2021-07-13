package www.dream.com.framework.langPosAnalyzer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import www.dream.com.framework.classAnalyzer.ClassAnalyzer;

/**
 * 품사 분석기가 정의한 Annotation을 달아 놓은 객체의 속성에 들어있는 정보를 Komoran을 활용하여 
 * 품사 분석하고 해쉬 태그로 활용할 만한 단어이 몇 번 사용되었는지 까지를 ComparablePair의 List로 반환할 것입니다.
 */
public class PosAnalyzer {
	private static Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

	/** 주어진 객체에서 각종 속성 및 함수위에 @HashTarget를 달아서 노출시킨 정보를 바탕으로
	 * 단어 분석기를 통하여 나온 것들을 출현 횟수까지 찾아 반환 */
	public static Map<String, Integer> getHashTags(Object obj) {
		Map<String, Integer> ret = new HashMap<>();
		getHashTags(obj, ret);
		return ret;
	}

	private static void getHashTags(Object obj, Map<String, Integer> map) {
		if (obj == null) {
			return;
		} else if (obj instanceof String) {
			analyzeHashTag((String) obj, map);
		} else if (obj instanceof Iterable) {
			((Iterable) obj).forEach(ele->getHashTags(ele, map));
		} else if (obj instanceof Map) {
			((Map) obj).entrySet().forEach(ele->getHashTags(ele, map));
		} else {
			List<AccessibleObject> listFeature = ClassAnalyzer.findFeatureByAnnotation(obj.getClass(), HashTarget.class);
			
			for (AccessibleObject ao : listFeature) {
				if (ao instanceof Field) {
					Field field = (Field) ao;
					try {
						field.setAccessible(true);
						Object fieldValue = field.get(obj);
						getHashTags(fieldValue, map);
					} catch (IllegalArgumentException | IllegalAccessException e) {
					}
				} else if (ao instanceof Method) {
					Method method = (Method) ao;
					try {
						Object returnValue = method.invoke(obj, null);
						getHashTags(returnValue, map);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 품사 분석기 Komoran을 활용하여 지정한 품사들을 대상으로 몇번 나타났는지 까지 정보를 모아준다.
	 * @param analysisTargetString
	 * @param ret
	 */
	private static void analyzeHashTag(String analysisTargetString, Map<String, Integer> ret) {
		if (analysisTargetString == null)
			return;
		KomoranResult analyzeResultList = komoran.analyze(analysisTargetString);
		if (analyzeResultList == null) {
			return;
		}

		List<Token> tokenList = analyzeResultList.getTokenList();
		for (Token token : tokenList) {
			TargetPos pos = null;	//품사
			try {
				pos = TargetPos.valueOf(token.getPos());
			} catch (Exception e) {
			}
			if (pos != null) {
				String hashTag = token.getMorph();
				if (ret.containsKey(hashTag)) {
					ret.put(hashTag, (ret.get(hashTag) + 1));
				} else {
					ret.put(hashTag, 1);
				}
			}
		}
	}
}
