package com.thoughtworks.conference.exception;
/**
 * The exception class which wrapps over the Exceptions that are raised during File operation.
 * 
 * @author Baratamr
 *
 */
public class ConferenceEventException extends Exception{

	/**
	 * serial version ID
	 */
	private static final long serialVersionUID = 1L;

	public static final int INPUT_PROCESS_EXCEPTION = 1;
	
	public static final int OUTPUT_PROCESS_EXCEPTION = 2;
	
	public  static final int SCHEDULING_EXCEPTION = 3;
	private int errorCode = -1;
	public ConferenceEventException(int errorCode,String errMsg,Throwable cause){
		super(errMsg,cause);
		this.errorCode = errorCode;
	}
	/**
	 * gets the error code
	 * @return
	 */
	public int getErrorCode(){
		return errorCode;
	}
}
