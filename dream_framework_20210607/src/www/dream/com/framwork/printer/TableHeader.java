package www.dream.com.framwork.printer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import www.dream.com.framwork.classAnalyzer.ClassAnalyzer;
import www.dream.com.framwork.util.ComparablePair;

public class TableHeader {
	public static String print(Class cls) {
		//HeaderTarget가 추가된 모든 AccessibleObject를 모집
		TreeSet<ComparablePair<Integer, String>> ordered = new TreeSet<>();
		
		collectHeaders(cls, HeaderTarget.class, ordered); 
		
		//문자열 출력
		StringBuilder sb = new StringBuilder();
		for (ComparablePair<Integer, String> cp : ordered) {
			sb.append("<th>" + cp.getSecond() +  "</th>");
		}
		return sb.toString();
	}
	
	private static void collectHeaders(Class cls, Class anno, TreeSet<ComparablePair<Integer, String>> ordered) {
		if (cls.getAnnotation(ClassHeaderTarget.class) == null)
			return;
		List<AccessibleObject> listFeature = ClassAnalyzer.findAllFeature(cls);
		for (AccessibleObject ao : listFeature) {
			HeaderTarget ht = (HeaderTarget) ao.getAnnotation(anno);
			if (ht != null) {
				ordered.add(new ComparablePair<>(ht.order(), ht.caption()));	
			} else { //annotation 못찾았으면
				if (ao instanceof Field) {
					collectHeaders(((Field) ao).getType(), anno, ordered);
				} else if (ao instanceof Method) {
					collectHeaders(((Method) ao).getReturnType(), anno, ordered);
				}
			}
		}
	}
}
