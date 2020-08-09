package com.thoughtworks.conference.output;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.conference.output.ConsoleOutputProceesor;
import com.thoughtworks.conference.event.ConferenceEvent;
import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.exception.ConferenceEventException;
import com.thoughtworks.conference.track.ConferenceSession;
import com.thoughtworks.conference.track.ConferenceTrack;

public class ConsoleOutputProceesorTest  {

	ConsoleOutputProceesor outputProcccesor = null;

	@Before
	public void setUp() throws Exception {
	
		outputProcccesor = new ConsoleOutputProceesor("10:00","12:00",60);

	}

	@After
	public void tearDown() throws Exception {
	
		outputProcccesor = null;
	}
	
	@Test	
	public void testDisplaySchedule() throws ConferenceEventException{
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = new ArrayList<ConferenceTrack<ConferenceSession<IEvent>>>();
		trackList.add(createTrack());
		trackList.add(createTrack());
		outputProcccesor.displaySchedule(trackList);
	}

	private ConferenceTrack<ConferenceSession<IEvent>> createTrack() {
		IEvent evt1 = new ConferenceEvent(1, "check format event", 45);
		IEvent evt2 = new ConferenceEvent(1, "check format event", 75);
		IEvent evt3 = new ConferenceEvent(1, "check format event", 45);
		IEvent evt4 = new ConferenceEvent(1, "check format event", 135);
		ConferenceSession<IEvent> morningSession = new ConferenceSession<IEvent>(180);
		morningSession.addEvent(evt1);
		morningSession.addEvent(evt2);
		ConferenceSession<IEvent> eveningSession = new ConferenceSession<IEvent>(180);
		eveningSession.addEvent(evt3);
		eveningSession.addEvent(evt4);
		ConferenceTrack<ConferenceSession<IEvent>> track = new  ConferenceTrack<ConferenceSession<IEvent>>();
		track.addSession(morningSession) ;
		track.addSession(eveningSession);
		return track;
	}
}
