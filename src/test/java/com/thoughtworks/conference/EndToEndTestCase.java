package com.thoughtworks.conference;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import com.thoughtworks.conference.event.EventModel;
import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.event.scheduler.BestFitEventScheduler;
import com.thoughtworks.conference.exception.ConferenceEventException;
import com.thoughtworks.conference.input.InputProcessor;
import com.thoughtworks.conference.output.ConsoleOutputProceesor;
import com.thoughtworks.conference.track.ConferenceSession;
import com.thoughtworks.conference.track.ConferenceTrack;
import com.thoughtworks.logger.Logger;

public class EndToEndTestCase {

	@Test
	public void testConferenceTrackMgmtApp() throws ConferenceEventException {
		if (Logger.LOGGER_ON) {
			Logger.printLog("EndToEndTestCase", "testConferenceTrackMgmtApp", "started ...");
		}
		EventModel eventModel = new EventModel();
		InputProcessor inputprocessor = new InputProcessor("min", eventModel);
		File inputFile = new File("./src/test/resources/conferenceinput.txt");
		inputprocessor.processInputData(inputFile);
		BestFitEventScheduler eventScheduler = new BestFitEventScheduler();
		
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = eventScheduler
				.schedule(eventModel.getEventList());
		ConsoleOutputProceesor consolOutput = new ConsoleOutputProceesor("09:00", "12:00", 60);
		consolOutput.displaySchedule(trackList);
		if (Logger.LOGGER_ON) {
			Logger.printLog("EndToEndTestCase", "testConferenceTrackMgmtApp", "ended ...");
		}
	}

}
