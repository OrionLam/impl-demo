package me.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 基于JDK原生的AOP实现<p>
 * 切面对所有方法都有效
 * @author Orion
 * 2018年9月25日
 */
public class CalculatorAOPUsingOriginalJDK {
    public static void main(String[] args) {
        ClassLoader classLoader = CalculatorAOPUsingOriginalJDK.class.getClassLoader();
        Class<?>[] interfaces = {Calculator.class};
        CalculatorAOPHandler aopHandler = new CalculatorAOPHandler(new CalculatorImpl());
        Calculator proxyCalculator = (Calculator)Proxy.newProxyInstance(classLoader, interfaces, aopHandler);
        
        System.out.println(proxyCalculator.calculate(6, 2));
        System.out.println(proxyCalculator.calculate(7, 0));
        
        System.out.println(proxyCalculator.add(6, 2));
        System.out.println(proxyCalculator.add(7, 0));
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
     * @author Orion
     * 2018年9月25日
     */
    int calculate( int a , int b );
    
    int add( int a , int b );
}


/**
 * 计算器实现类
 * @author Orion
 * 2018年9月25日
 */
class CalculatorImpl implements Calculator {
    @Override
    public int calculate(int a, int b) {
        return a/b;
    }

    @Override
    public int add(int a, int b) {
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
        System.out.println("proxy class: " + proxy.getClass().getName());
        Object invoke = method.invoke(calculatorImpl, args);
        return invoke;
    }
}