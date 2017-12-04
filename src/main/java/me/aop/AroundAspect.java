package me.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import me.biz.MockBizService;

public class AroundAspect {

	public Object doRound(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("Additional Concern Before calling actual method");
		System.out.println("Method: "+pjp.getSignature().getName());
		System.out.println("arg length: "+pjp.getArgs().length);
		System.out.println(pjp.getArgs()[0]==null);
		System.out.println("Kind: "+pjp.getKind());
		System.out.println("Target: "+pjp.getTarget().getClass().getName());
		System.out.println("currently executing object: "+pjp.getThis().getClass().getName());
		Object obj = pjp.proceed();
		System.out.println("Additional Concern After calling actual method");
		return obj;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");  
		MockBizService service = (MockBizService) context.getBean("mockService");  
        service.save(null);
        service.queryByName("haha");
	}
}
