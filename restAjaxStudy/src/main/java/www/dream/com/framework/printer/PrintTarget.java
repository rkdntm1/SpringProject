package www.dream.com.framework.printer;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface PrintTarget {
	//출력 순서
	int order() default 0;
	//Caption
	String caption() default "";
	//날짜패턴
	String pattern() default ""; 
	//anchor가 달렸는지 확인
	boolean withAnchor() default false;
}
