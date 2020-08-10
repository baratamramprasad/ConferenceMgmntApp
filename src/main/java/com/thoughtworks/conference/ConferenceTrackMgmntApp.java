package com.thoughtworks.conference;

import java.io.File;
import java.util.ArrayList;

import com.thoughtworks.conference.event.EventModel;
import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.event.scheduler.BestFitEventScheduler;
import com.thoughtworks.conference.exception.ConferenceEventException;
import com.thoughtworks.conference.input.InputProcessor;
import com.thoughtworks.conference.output.ConsoleOutputProceesor;
import com.thoughtworks.conference.track.ConferenceSession;
import com.thoughtworks.conference.track.ConferenceTrack;
import com.thoughtworks.logger.Logger;

/**
 * This main class which is entry point for conference track management.
 * @author Baratamr
 *
 */
public class ConferenceTrackMgmntApp {

	/**
	 * main method and entry point to application
	 * @param strings
	 */
	public static void main(String[] strings ){
		if(Logger.LOGGER_ON){
			Logger.printLog("ConferenceTrackMgmApp", "main", "vm argumenent:"+(strings.length > 0? strings[0]:" no arg"));
		}
		if(strings.length < 1){
			System.out.println("App exiting ...insufficient arguments");
			System.exit(0);
		}
		File file = new File(strings[0]);
		ConferenceTrackMgmntApp mainApp = new ConferenceTrackMgmntApp();
		mainApp.startApp(file);	
	}
/**
 * starts the app
 * @param file
 */
	private  void startApp(File file) {
		EventModel eventModel = new EventModel();
		InputProcessor inputprocessor = new InputProcessor("min",eventModel);
		try {
			if(Logger.LOGGER_ON){
				Logger.printLog("ConferenceTrackMgmApp", "main", "starting input processing");
			}
			inputprocessor.processInputData(file);
			BestFitEventScheduler eventScheduler = new BestFitEventScheduler();
			if(Logger.LOGGER_ON){
				Logger.printLog("ConferenceTrackMgmApp", "main", "start schduling...");
			}
			System.out.println("please wait...Event Scheduling started");
			ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = eventScheduler.schedule(eventModel.getEventList());
			ConsoleOutputProceesor consolOutput = new ConsoleOutputProceesor("09:00",60);
			System.out.println("Event Scheduling done...");
			consolOutput.displaySchedule(trackList);
		} catch (ConferenceEventException e) {
			e.printStackTrace();
			System.out.println("due to input process error,App exiting ...");
			System.exit(1);
		}
	}
}
