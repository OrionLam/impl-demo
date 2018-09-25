package me.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 基于JDK原生的AOP实现<p>
 * 切面对所有方法都有效
 * @author Orion
 * 2018年9月25日
 */
public class CalculatorAOPUsingOriginalJDK {
    public static void main(String[] args) {
        proxyBeforeAfter();
    }
    
    public static void proxyDirectly() {
        ClassLoader classLoader = CalculatorAOPUsingOriginalJDK.class.getClassLoader();
        Class<?>[] interfaces = {Calculator.class};
        CalculatorAOPHandler aopHandler = new CalculatorAOPHandler(new CalculatorImpl());
        Calculator proxyCalculator = (Calculator)Proxy.newProxyInstance(classLoader, interfaces, aopHandler);
        
        System.out.println("经过AOP,并且未触发AOP特殊逻辑,结果: "+proxyCalculator.division(6, 2)+"; 经过AOP,并且触发了AOP特殊逻辑,结果: "+proxyCalculator.division(7, 0));
        System.out.println("经过AOP,并且未触发AOP特殊逻辑,结果: "+proxyCalculator.plus(6, 2)+"; 经过AOP,并且触发了AOP特殊逻辑,结果: "+proxyCalculator.plus(7, 0));
    }
    
    public static void proxyBeforeAfter() {
        Calculator calculator = new CalculatorImpl();
        
        AbstractAOPHandler[] handlers = {new CalculatorBeforeLog(), new CalculatorBeforeCache(), new CalculatorCannotDivisionZero(), new CalculatorAfter()};
        Calculator proxyCalculator = (Calculator)AOPUtilUsingOriginalJDK.getProxyInstance(calculator, Arrays.asList(handlers));
        
        System.out.println("经过AOP,并且未触发AOP特殊逻辑,结果: " + proxyCalculator.division(6, 2));
        try {
            proxyCalculator.division(7, 0);
        } catch(RuntimeException ex) {
            ex.printStackTrace();
            System.err.println("经过AOP,并且触发了AOP特殊逻辑并出现异常: " + ex.getMessage());
        }
        
        System.out.println("经过AOP,并且未触发AOP特殊逻辑,结果: " + proxyCalculator.plus(6, 2));
        System.out.println("经过AOP,并且未触发AOP特殊逻辑,结果: " + proxyCalculator.plus(7, 0));
    }
}

/**
 * 计算器接口
 * @author Orion
 * 2018年9月25日
 */
interface Calculator {
    /**
     * 除法计算 a/b
     */
    int division( int a , int b );
    
    /**
     * 加法计算 a+b
     */
    int plus( int a , int b );
}


/**
 * 计算器实现类
 * @author Orion
 * 2018年9月25日
 */
class CalculatorImpl implements Calculator {
    @Override
    public int division(int a, int b) {
        return a/b;
    }

    @Override
    public int plus(int a, int b) {
        return a+b;
    }
}


/**
 * 计算器的切面处理器
 * @author Orion
 * 2018年9月25日
 */
class CalculatorAOPHandler implements InvocationHandler {
    private final Calculator calculatorImpl;
    public CalculatorAOPHandler(Calculator calculatorImpl) {
        this.calculatorImpl = calculatorImpl;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ( args!=null && args.length==2 && "0".equals(String.valueOf(args[1])) ) {
            return -1;
        }
        Object invoke = method.invoke(calculatorImpl, args);
        return invoke;
    }
}

/**
 * 计算器的切面Before-记录日志
 * @author Orion
 * 2018年9月25日
 */
class CalculatorBeforeLog extends BeforeHandler{
    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("This is log for "+method.getName()+"!");
    }
}

/**
 * 计算器的切面Before-缓存数据（模拟）
 * @author Orion
 * 2018年9月25日
 */
class CalculatorBeforeCache extends BeforeHandler{
    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Cache the instance "+getTargetObject().getClass().getName()+" to MongoDB !");
    }
}

/**
 * 计算器的切面Before-不能除以零
 * @author Orion
 * 2018年9月25日
 */
class CalculatorCannotDivisionZero extends BeforeHandler{
    @Override
    public void before(Object proxy, Method method, Object[] args) throws Throwable {
        if ( method.getName().equals("division") && "0".equals(String.valueOf(args[1])) )
            throw new RuntimeException("You cannot do this, "+args[0]+"/"+args[1]);
    }
}

/**
 * 计算器的切面After
 * @author Orion
 * 2018年9月25日
 */
class CalculatorAfter extends Afterhandler {
    @Override
    public void after(Object proxy, Method method, Object[] args, Object invokeRS) throws Throwable {
        System.out.println("This is log for "+method.getName()+", the result is "+invokeRS);
    }
    
}