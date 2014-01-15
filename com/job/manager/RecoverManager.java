package com.job.manager;

/**
 * job异常退出情况的错误处理
 * 
 * @author liudunxu
 * 
 */
public class RecoverManager {

	// 单例实例
	private static RecoverManager _instanceManager = null;

	// 验证依据策略,默认使用文件验证策略
	private IValidStrategy _validStategy = new FileValidStrategy();

	/**
	 * 设置验证策略
	 */
	public void SetValidStrategy(IValidStrategy strategy){
		if(null != strategy){
			_validStategy = strategy;
		}
	}
	
	/**
	 * 将构造函数私有化，不能任意创建实例
	 */
	private RecoverManager() {
	};

	/**
	 * 获取实例对象， 双重检查锁定
	 * 
	 * @return
	 */
	public static RecoverManager getInstance() {
		if (null == _instanceManager) {
			synchronized (RecoverManager.class) {
				if (null == _instanceManager) {
					_instanceManager = new RecoverManager();
				}
			}
		}
		return _instanceManager;
	}

	/**
	 * 开始恢复过程
	 */
	public synchronized void start(Runnable recoverRunnable) {
		
		// 如果是非法退出,进入非法退出恢复流程
		if (!_validStategy.check()) {
			//建立恢复线程
			Thread recoverThread = new Thread(recoverRunnable);
			recoverThread.start();
			//销毁旧的依据
			_validStategy.destroy();
		}
		
		// 创建依据
		_validStategy.create();
		// 注册关闭依据，销毁建立的依据
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				_validStategy.destroy();
			}
		});
		Runtime.getRuntime().addShutdownHook(t);
	}
	
	public static void main(String []args){
		RecoverManager.getInstance().start(new Runnable() {
			@Override
			public void run() {
				System.out.println("oh,no!!!!!");
			}
		});
	}
}