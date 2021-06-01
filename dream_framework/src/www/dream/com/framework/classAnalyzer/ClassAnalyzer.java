package www.dream.com.framework.classAnalyzer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassAnalyzer {
	
	public static List<AccessibleObject> findFeatureByAnnotation(Class targetClass, Class targetAnno) {
		List<AccessibleObject> ret = new ArrayList<>();
		findFeatureByAnnotation(targetClass, targetAnno, ret);
		return ret;
	}
	
	public static void findFeatureByAnnotation(Class targetClass, Class targetAnno, List<AccessibleObject> list) {
		try {			
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				Annotation anno = field.getAnnotation(targetAnno);
				if (anno != null) {
					list.add(field);
				}
			}
			
			Method[] methods = targetClass.getDeclaredMethods();
			for (Method method : methods) {
				Annotation anno = method.getAnnotation(targetAnno);
				if (anno != null) {
					list.add(method);
				}
			}
		} catch (Exception e) {
		}
		Class targetSuper = targetClass.getSuperclass();
		if (targetSuper != Object.class) {
			findFeatureByAnnotation(targetSuper, targetAnno, list); //재귀함수
		}
	}
}
