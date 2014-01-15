package com.job.manager;

/**
 * 检查是否异常退出的策略接口
 * @author liudunxu
 *
 */
public interface IValidStrategy {
	/**
	 * 初始化
	 */
	public void create();
	
	/**
	 * 检查合法性
	 * @return
	 */
	public boolean check();

	/**
	 * 销毁
	 * @return
	 */
	public boolean destroy();
}
