package me.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import me.biz.MockBizService;

public class InvocationHandlerImpl implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("proxy handler: "+method.getName());
		return null;
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		InvocationHandler proxyHandler = new InvocationHandlerImpl();
		Class<?> proxyClass = Proxy.getProxyClass(MockBizService.class.getClassLoader(), MockBizService.class);
		MockBizService service = (MockBizService) proxyClass.getConstructor(InvocationHandler.class).newInstance(proxyHandler);
		service.save(null);
		service.queryByName(null);
	}

}
