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
 * 품사 분석기가 정의한 Annotation을 달아 놓은 객체의 속성에 들어 있는 정보를 Komoran을 활용하여
 * 품사 분석하고 해쉬 태그로 활용할 만한 단어가 몇번 사용 되었는지 까지를 Pair의 List로 반환할 것입니다.
 */
public class PosAnalyzer {
	private static Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
	
	public static Map<String, Integer> getHashTags(Object obj) {
		Map<String, Integer> ret = new HashMap<>();
		getHashTags(obj, ret);
		return ret;
	}
	
	private static void getHashTags(Object obj, Map<String, Integer> map) {
		List<AccessibleObject> listFeature = ClassAnalyzer.findFeatureByAnnotation(obj.getClass(), HashTarget.class); //정보가담긴 obj 
		
		for(AccessibleObject ao : listFeature) {
			if (ao instanceof Field) {
				Field field = (Field) ao;
				Class type = field.getType();
				Annotation anno = type.getAnnotation(HashTarget.class);
				if (type == String.class) {
					try {
						field.setAccessible(true);
						String analysisTargetString = (String) field.get(obj);
						analyzeHashTag(analysisTargetString, map);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}	
				} else if (type == List.class) {
					try {
						field.setAccessible(true);
						Object attachObj = field.get(obj);
						for (Object contained : (List) attachObj) {
								getHashTags(contained, map);						
							}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}	
				} else if (anno != null) {
					try {
						field.setAccessible(true);
						Object attachObj = field.get(obj);
						getHashTags(attachObj, map);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}	
				}
			} else if (ao instanceof Method) {
				Method method = (Method) ao;
				Class type = method.getReturnType();
				Annotation anno = type.getAnnotation(HashTarget.class);
				if (type == String.class) {
					try {
						String analysisTargetString = (String) method.invoke(obj, null);
						analyzeHashTag(analysisTargetString, map);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if (type == List.class) {
					try {
						Object attachObj = method.invoke(obj, null);
						for (Object contained : (List) attachObj) {
							getHashTags(contained, map);						
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}else if (anno != null) {
					try {
						Object attachObj = method.invoke(obj, null);
						getHashTags(attachObj, map);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private static void analyzeHashTag(String analysisTargetString, Map<String, Integer> ret) {
		KomoranResult analyzeResultList = komoran.analyze(analysisTargetString);
		List<Token> tokenList = analyzeResultList.getTokenList();
		
		for (Token token : tokenList) {
			TargetPos pos = null; // 품사
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
