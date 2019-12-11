/**
 * 
 */
package com.htf.bigdata.invest.indicatormanage.component.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程上下文
 * 
 * @author zhairp createDate: 2019-06-14
 */
public final class ThreadContext {
	private static final ExecutorService executorService = Executors.newFixedThreadPool(16);

	private ThreadContext() {
	}

	public static ExecutorService getThreadPool() {
		return executorService;
	}

}
