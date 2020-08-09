package com.thoughtworks.logger;


import java.util.StringJoiner;

/**
 * Logger class prints the log on console with specified class name and method name
 * logger class shall be used in following format
 * <p><code>
 * if(Logger.LOGGER_ON){<BR>
 * 	Logger.print("classname","methodname","logging msg");<BR>
 * }
 * </code></p>
 * @author Baratamr
 *
 */
public class Logger {
	/**
	 * This value can set set true to get the logs.
	 * in debug mode this attribute is set true 
	 */
	public static boolean LOGGER_ON = true;
	
	
	/**
	 * This value can set set true to get the logs.
	 * in debug mode this attribute is set true 
	 */
	public static boolean TRACE_ON = false;
	/**
	 * Prints the logging msg.
	 * 
	 * This method shall not be directly called.i.e without checking the value of <code>LOGGER_ON</code>
	 * 
	 * @param classname
	 * @param methodName
	 * @param msg
	 */
	public static void printLog(String classname,String methodName ,String msg){
		
		StringJoiner joiner = new StringJoiner("][","[","] ");
		joiner.add(classname)
		.add(methodName);
		
		System.out.println(joiner+msg);
	}
	/**
	 * Prints the logging msg.
	 * @param obj "this" reference can be passed from caller
	 * @param methodName
	 * @param msg
	 */
	public static void printLog(Object obj ,String methodName,String msg){
		StringJoiner joiner = new StringJoiner("][","[","] ");
		joiner.add(obj.getClass().toString())
		.add(methodName);
		
		System.out.println(joiner+msg);
	}

}
