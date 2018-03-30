package me.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	
	public void doAfter(JoinPoint jp, Object retVal) {  
        System.out.println("log Ending method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
        System.out.println("return value: " + new GsonBuilder().create().toJson(retVal) );
    }
	
	public void doBefore(JoinPoint jp) {  
        System.out.println("log Begining method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());  
    }  
  
    public void doThrowing(JoinPoint jp, Throwable ex) {  
        System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");  
        System.out.println(ex.getMessage());  
    }
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");  
		MockBizService service = (MockBizService) context.getBean("mockService");  
        service.save(null);
        service.queryByName("haha");
	}
	/**
    <aop:config>  
       <aop:aspect id="TestAspect" ref="aspectBean">  
           <!--配置com.spring.service包下所有类或接口的所有方法-->  
           <aop:pointcut id="businessService" expression="execution(* com.spring.service.*.*(..))" />  
           <aop:before pointcut-ref="businessService" method="doBefore"/>  
           <aop:after-returning pointcut-ref="businessService" method="doAfter" returning="retVal"/>
           <aop:around pointcut-ref="businessService" method="doAround"/>  
           <aop:after-throwing pointcut-ref="businessService" method="doThrowing" throwing="ex"/>  
       </aop:aspect>  
   </aop:config>  
     
   <bean id="aspectBean" class="com.spring.aop.TestAspect" />  
   <bean id="aService" class="com.spring.service.AServiceImpl"></bean>  
   <bean id="bService" class="com.spring.service.BServiceImpl"></bean>
    */
}
