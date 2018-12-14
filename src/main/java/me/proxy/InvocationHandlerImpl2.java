package me.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import me.biz.MockBizService;
import me.biz.MockDTO;

/**
 * 动态代理<br>
 * 该代理目的：在提前定义好的业务实现类之前和之后均执行一些代码
 * @author Orion
 */
public class InvocationHandlerImpl2 implements InvocationHandler {
	private Object realBizImpl;
	
	public InvocationHandlerImpl2(Object realBizImpl) {
		super();
		this.realBizImpl = realBizImpl;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before execute: "+method.getName());
		Object result = method.invoke(realBizImpl, args);
		System.out.println("after execute: "+method.getName());
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		MockBizService realBizImpl = new MockBizService(){
			@Override
			public MockDTO save(MockDTO dto) {
				System.out.println("save mockDTO biz implement");
				return null;
			}
			@Override
			public List<MockDTO> queryByName(String name) {
				System.out.println("queryByName mockDTO biz implement");
				return null;
			}
		};
		InvocationHandler proxyHandler = new InvocationHandlerImpl2(realBizImpl);
		Class<?> proxyClass = Proxy.getProxyClass(MockBizService.class.getClassLoader(), MockBizService.class);
		MockBizService service = (MockBizService) proxyClass.getConstructor(InvocationHandler.class).newInstance(proxyHandler);
		service.save(null);
		service.queryByName(null);
	}
}
