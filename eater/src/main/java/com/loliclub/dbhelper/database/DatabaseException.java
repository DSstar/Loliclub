package com.loliclub.dbhelper.database;

/**
 * 数据库异常类,该类继承于{@link RuntimeException} <br>
 * 用于抛出自定义的异常信息。
 * 
 */
public class DatabaseException extends RuntimeException {

	public static final String TAG = "DatabaseExcception";

	private static final long serialVersionUID = 1L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public DatabaseException(String detailMessage) {
		super(detailMessage);
	}

	public DatabaseException(Throwable throwable) {
		super(throwable);
	}

}
