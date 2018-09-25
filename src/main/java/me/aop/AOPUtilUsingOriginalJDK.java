package me.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 使用JDK原生实现AOP封装的工具
 * @author Orion
 * 2018年9月25日
 */
public class AOPUtilUsingOriginalJDK {
    public static Object getProxyInstance(Object targetObject, List<AbstractAOPHandler> aopHandlers) {
        if ( targetObject == null || aopHandlers==null || aopHandlers.size()==0 )
            return targetObject;
        
        Object proxyObject = targetObject;
        for ( int i=aopHandlers.size(); i>0; i-- ) {
            aopHandlers.get(i-1).setTargetObject(proxyObject);
            proxyObject = Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), aopHandlers.get(i-1));
        }
        
        return proxyObject;
    }
}

/**
 * AOP抽象handler，规定要设置真正的执行对象
 * <pre>
 * 注：第一层AOP的targetObject是业务实现类
 * 第二层AOP的targetObject是第一层AOP的代理类，以此类推
 * </pre>
 * @author Orion
 * 2018年9月25日
 */
abstract class AbstractAOPHandler implements InvocationHandler {
    private Object targetObject;
    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }
    public Object getTargetObject() {
        return targetObject;
    }
}

/**
 * AOP抽象handler，使用AOP before时要继承该类并对before方法作具体实现
 * @author Orion
 * 2018年9月25日
 */
abstract class BeforeHandler extends AbstractAOPHandler {
    public abstract void before(Object proxy, Method method, Object[] args) throws Throwable;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before(proxy, method, args);
        return method.invoke(getTargetObject(), args);
    }
}

/**
 * AOP抽象handler，使用AOP after时要继承该类并对after方法作具体实现
 * @author Orion
 * 2018年9月25日
 */
abstract class Afterhandler extends AbstractAOPHandler {
    public abstract void after(Object proxy, Method method, Object[] args, Object invokeRS) throws Throwable;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invokeRS = method.invoke(getTargetObject(), args);
        after(proxy, method, args, invokeRS);
        return invokeRS;
    }
    
}