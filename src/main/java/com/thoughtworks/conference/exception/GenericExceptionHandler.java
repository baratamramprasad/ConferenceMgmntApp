package com.thoughtworks.conference.exception;

import com.thoughtworks.logger.Logger;

/**
 * 
 * It handles all the exception that might be raised in the execution of
 * application. It basically logs the exception error message in debug mode i.e Logger.logger_on is set true
 * and prints  the stack trace optionally.
 * 
 *  
 * @author Baratamr
 * 
 */
public class GenericExceptionHandler {

	/**
	 * Handles the exceptions raised during finding of the number of word in the specified portion of source file
	 * 
	 * @param exception
	 */
	public static void handleException(ConferenceEventException exception,
			boolean printStack) {
		if (Logger.LOGGER_ON) {
			Logger.printLog("GenericExceptionHandler", "handleParseException",
					"Error MSG" + exception.getMessage());
			if(printStack){
				exception.printStackTrace();
			}
		}
	}

	
}
