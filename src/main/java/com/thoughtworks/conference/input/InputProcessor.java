package com.thoughtworks.conference.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.thoughtworks.conference.event.ConferenceEvent;
import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.event.IEventListener;
import com.thoughtworks.conference.exception.ConferenceEventException;
import com.thoughtworks.logger.Logger;

/**
 * It reads the input text file and creates the objects of code>IEvent</code>.
 * 
 * @author Baratamr
 * 
 */
public class InputProcessor implements IInputProcessor<IEvent, File> {

	private String mins = "MINS";
	private String LIGHTING = "lightning";
	private String MIN_DURATION ="5min";

	private static int eventCount = 1;

	private IEventListener<IEvent> eventListener = null;

	/**
	 * sets the event listener
	 * 
	 * @param eventListener
	 */

	public InputProcessor(String mins,IEventListener<IEvent> eventListener) {
		this.mins = mins;
		this.eventListener = eventListener;
	}

	public void setEventlistener(IEventListener<IEvent> eventListener) {
		this.eventListener = eventListener;
	}

	/**
	 * processes File
	 * 
	 * @param inputFile
	 * @throws ConferenceEventException
	 */
	public void processInputData(File inputFile) throws ConferenceEventException {
		if (Logger.LOGGER_ON) {
			Logger.printLog("InputProcessor", "processInputFile", "inputFile :" + inputFile.getAbsolutePath());
		}
		try (Scanner scanner = new Scanner(inputFile)) {
			while (scanner.hasNextLine()) {
				processLine(scanner.nextLine());
			}

		} catch (FileNotFoundException e) {
			throw new ConferenceEventException(ConferenceEventException.INPUT_PROCESS_EXCEPTION,
					"Error while accessing input file", e);
		}

	}

	private void processLine(String next) {
		if (Logger.LOGGER_ON) {
			Logger.printLog("InputProcessor", "processLine", "next :" + next);
		}
		try (Scanner scanner = new Scanner(next)) {
			String token = scanner.next();
			StringBuffer evtTitle = new StringBuffer(token);
			evtTitle.append(" ");
			while (scanner.hasNext()) {
				token = scanner.next();
				if (!token.endsWith(mins)) {
					evtTitle.append(token);
					evtTitle.append(" ");

				}
			}
			if(token.equalsIgnoreCase(LIGHTING)) {
				token=MIN_DURATION;
			}
			createEvent(evtTitle.toString(), token);
		}
	}

	/**
	 * Creates Event
	 * 
	 * @param title
	 * @param previousWord
	 */
	private void createEvent(String title, String timeUnitStr) {
		int endIndex = timeUnitStr.length() - mins.length();
		String time = timeUnitStr.substring(0, endIndex);
		if (Logger.LOGGER_ON) {

			Logger.printLog("InputProcessor", "createEvent",
					"time :" + time + " endIndex:" + endIndex + " timeUnitStr:" + timeUnitStr);
		}
		
		int timeDur= Integer.parseInt(time);
		IEvent event = new ConferenceEvent(eventCount++, title,timeDur);
		if (eventListener != null) {
			eventListener.eventCreated(event, null);
		}
	}
}
