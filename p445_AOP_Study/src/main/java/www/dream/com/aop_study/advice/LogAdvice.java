package www.dream.com.aop_study.advice;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAdvice {
	
	//@Before("execution(정규식으로 적용 대상 함수를 패턴으로 지정)")
	@Before("execution(* www.dream.com.aop_study.service.SomeService*.do*(..)) && args(a, b)")
	public void beforeAdvice(int a, int b) {
		System.out.println("LogAdvice::beforeAdvice 함수가 " + a + "," + b + "값으로 실행 됨");
	}
	
	@Around("execution(* www.dream.com.aop_study.service.SomeSer*.do*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		//앞에서 해야 할 일
		long start = System.currentTimeMillis();
		
		Object ret = null;
		try {
			ret = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		//뒤에서 간섭 할 일
		long end = System.currentTimeMillis();
		
		System.out.println("" + pjp.getTarget().toString() + Arrays.toString(pjp.getArgs()) + "Time : " + (end - start) + "millisec");
		return ret;
	}
}
