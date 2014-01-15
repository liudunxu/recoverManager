package com.job.manager;

import java.io.File;
import java.io.IOException;


/**
 * 文件方式实现的判定合法性策略
 * @author liudunxu
 *
 */
public class FileValidStrategy implements IValidStrategy{

	/**
	 * 创建的文件名
	 */
	private static final String FILE_NAME = "./recover.tmp";
	
	/**
	 * file成员变量
	 */
	private File _tmpFile = new File(FILE_NAME);
	
	@Override
	public void create() {
		try {
			_tmpFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean check() {
		return !_tmpFile.exists();
	}

	@Override
	public boolean destroy() {
		_tmpFile.delete();
		return true;
	}
}
