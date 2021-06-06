package www.dream.com.framwork.classAnalyzer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassAnalyzer {
	/**
	 * 주어진 클래스에서 정의되어 있는 모든 속성과 함수에 지정된 annotation을 붙여 놓은 것들을 
	 * 상속 구조 까지 분석하여 모아서 반환한다.
	 * @param targetClass
	 * @param targetAnno
	 * @return
	 */
	public static List<AccessibleObject> findFeatureByAnnotation(Class targetClass, Class targetAnno) {
		List<AccessibleObject> ret = new ArrayList<>();
		findFeatureByAnnotation(targetClass, targetAnno, ret);
		return ret;
	}
	
	private static void findFeatureByAnnotation(Class targetClass, Class targetAnno, List<AccessibleObject> list) {
		try {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field :  fields) {
				// targetAnno Annotation이 해당 필드에 들어있는지 확인 
				Annotation anno = field.getAnnotation(targetAnno);
				if (anno != null) {
					list.add(field);
				}
			}
			
			Method[] methods = targetClass.getDeclaredMethods();
			for (Method method : methods) {
				// targetAnno Annotation이 해당 필드에 들어있는지 확인 
				Annotation anno = method.getAnnotation(targetAnno);
				if (anno != null) {
					list.add(method);
				}
			}
		} catch (Exception e) {
		}
		Class targetSuper = targetClass.getSuperclass();
		if (targetSuper != Object.class) {
			findFeatureByAnnotation(targetSuper, targetAnno, list); // 상위클래스객체로 재귀함수 호출
		}
	}
	
}
