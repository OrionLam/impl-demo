package me.aop;

import me.biz.MockBizService;

public class AroundAspect {

	public Object doRound(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("Additional Concern Before calling actual method");
		Object obj = pjp.proceed();
		System.out.println("Additional Concern After calling actual method");
		return obj;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new classPathXmlApplicationContext("spring-aop.xml");  
		MockBizService service = (MockBizService) context.getBean("mockService");  
        service.save(null);
        service.queryByName("haha");
	}
}
