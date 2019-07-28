package me.java8;

import org.junit.Test;

/**
 * Lambda简单测试
 * @author orion
 */
public class LambdaTester {
	
	/**
	 * 基于数字的简单测试
	 */
	@Test
	public void numbericSimpleTest() {
		/*
		 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）
		 * 在本例子中：
		 * 首先定义一个函数式接口（即只有一个方法的接口）
		 * 然后定义addition，subtraction，multiplication，division四个函数同时他们也能等同于参数
		 * 最后测试这四个函数作为参数传递给另一个方法
		 */
		
		// 声明方法参数类型
		MathOperation addition = (int a, int b) -> a + b;
		// 不声明方法类型（由JDK自行识别）
		MathOperation subtraction = (a, b) -> a - b;
		// 大括号中的返回语句
		MathOperation multiplication = (int a, int b) -> { return a * b; };
		// 没有大括号及返回语句
		MathOperation division = (int a, int b) -> a / b;
		
		System.out.println("10 + 5 = " + this.operate(10, 5, addition));
		System.out.println("10 - 5 = " + this.operate(10, 5, subtraction));
		System.out.println("10 x 5 = " + this.operate(10, 5, multiplication));
		System.out.println("10 / 5 = " + this.operate(10, 5, division));
	}
	
	/**
	 * 证明Lambda只是使代码变的更加简洁紧凑
	 */
	@Test
	public void insipidUsage() {
		/*
		 * Lambda并未能提供更多特性，只是写法比较新颖
		 * 比较适合内部匿名且无需复用的函数
		 */
		
		MathOperation addition_1 = (int a, int b) -> a + b;
		MathOperation addition_2 = new MathOperation() {
			@Override
			public int operation(int a, int b) {
				return a + b;
			}
		};
		
		System.out.println("10 + 5 = " + this.operate(10, 5, addition_1));
		System.out.println("10 + 5 = " + this.operate(10, 5, addition_2));
		
	}
	
	interface MathOperation {
		int operation(int a, int b);
	}
	
	private int operate(int a, int b, MathOperation mathOperation){
		return mathOperation.operation(a, b);
	}
}
