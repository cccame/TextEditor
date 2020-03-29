package com.cccame.texteditor.utils;

public class TestUtil {
	private static long startTime = 0;
	private static long endTime = 0;

	// 在调用测试代码之前调用
	public static long start() {
		startTime = System.currentTimeMillis();
		return startTime;
	}


	// 在调用测试代码之后调用
	public static long end() {
		endTime = System.currentTimeMillis();
		return endTime;
	}


	// 返回运行测试代码的耗时
	public static long getSpendTime() {
		return (endTime - startTime);
	}

}
