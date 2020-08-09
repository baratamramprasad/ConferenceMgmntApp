package com.thoughtworks.conference.event.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.conference.event.ConferenceEvent;
import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.track.ConferenceSession;
import com.thoughtworks.conference.track.ConferenceTrack;

/**
 * Unit test for BestFitEventScheduler.
 */

public class BestFitEventSchedulerTest {
	BestFitEventScheduler bestFitScheduler = null;

	@Before
	public void setUp() throws Exception {
	
		bestFitScheduler = new BestFitEventScheduler();

	}

	@After
	public void tearDown() throws Exception {
		
		bestFitScheduler = null;
	}

	@Test
	public void testSchedule_WithNULLEventList() {
		ArrayList<IEvent> eventList = null;
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = bestFitScheduler
				.schedule(eventList);
		assertNull(trackList);
	}

	@Test
	public void testSchedule_WithEMPTYEventList() {
		ArrayList<IEvent> eventList = new ArrayList<IEvent>();
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = bestFitScheduler
				.schedule(eventList);
		assertNull(trackList);
	}
	
	@Test
	public void testSceduleWithUNSorterList() {
		ArrayList<IEvent> eventList = createUnSortedEventList();
		int expectedDuration = 0;
		int expectedNumEvents = eventList.size();
		for (IEvent evt : eventList) {
			expectedDuration += evt.getBookedDuration();
		}
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = bestFitScheduler
				.schedule(eventList);

		int totalDuration = 0;
		int numEvents = 0;
		for (ConferenceTrack<ConferenceSession<IEvent>> track : trackList) {
			for (ConferenceSession<IEvent> session : track.getSessionList()) {
				int sessionDur = 0;
				for (IEvent event : session.getEventList()) {
					totalDuration += event.getBookedDuration();
					sessionDur += event.getBookedDuration();
					numEvents++;
				}
				// check that sum of event duration does not exceed total
				// duration
				assertTrue(sessionDur <= session.getSessionDuration());
			}
			// check that there can be mamximum of two sessions in track.
			assertTrue(track.getSessionList().size() <= 2);
		}
		assertEquals(0, eventList.size());
		assertEquals(expectedNumEvents, numEvents);
		assertEquals(expectedDuration, totalDuration);
	}

	@Test
	public void testSceduleWithSorterList() {
		ArrayList<IEvent> eventList = createSortedEventList();
		int expectedDuration = 0;
		int expectedNumEvents = eventList.size();
		for (IEvent evt : eventList) {
			expectedDuration += evt.getBookedDuration();
		}
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = bestFitScheduler
				.schedule(eventList);

		int totalDuration = 0;
		int numEvents = 0;
		for (ConferenceTrack<ConferenceSession<IEvent>> track : trackList) {
			for (ConferenceSession<IEvent> session : track.getSessionList()) {
				int sessionDur = 0;
				for (IEvent event : session.getEventList()) {
					totalDuration += event.getBookedDuration();
					sessionDur += event.getBookedDuration();
					numEvents++;
				}
				// check that sum of event duration does not exceed total
				// duration
				assertTrue(sessionDur <= session.getSessionDuration());
			}
			// check that there can be mamximum of two sessions in track.
			assertTrue(track.getSessionList().size() <= 2);
		}
		assertEquals(0, eventList.size());
		assertEquals(expectedNumEvents, numEvents);
		assertEquals(expectedDuration, totalDuration);
	}

	@Test
	public void testSceduleOutput() {
		ArrayList<IEvent> eventList = createUnSortedEventList();
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = bestFitScheduler
				.schedule(eventList);
		for (ConferenceTrack<ConferenceSession<IEvent>> track : trackList) {
			System.out
					.println("...........................Track begin.......................");
			for (ConferenceSession<IEvent> session : track.getSessionList()) {
				System.out
						.println("--------------session----------------------");
				for (IEvent event : session.getEventList()) {
					System.out.println("Title :" + event.getTitle()
							+ " Duration::" + event.getBookedDuration());
				}

			}
			System.out
					.println("..........................track ends........................... ");
		}
	}

	@Test
	public void testSceduleOutputForSortedList() {
		ArrayList<IEvent> eventList = createSortedEventList();
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = bestFitScheduler
				.schedule(eventList);
		for (ConferenceTrack<ConferenceSession<IEvent>> track : trackList) {
			System.out
					.println("...........................Track begin.......................");
			for (ConferenceSession<IEvent> session : track.getSessionList()) {
				System.out
						.println("--------------session----------------------");
				for (IEvent event : session.getEventList()) {
					System.out.println("Title :" + event.getTitle()
							+ " Duration::" + event.getBookedDuration());
				}

			}
			System.out
					.println("..........................track ends........................... ");
		}
	}

	private ArrayList<IEvent> createUnSortedEventList() {
		ArrayList<IEvent> eventList = new ArrayList<IEvent>();
		IEvent event = new ConferenceEvent(1, "Talk by Ravisankar", 37);
		eventList.add(event);
		event = new ConferenceEvent(2, "Talk On Clean India", 50);
		eventList.add(event);
		event = new ConferenceEvent(3, "Discussion on Jayalalitha Arrest", 35);
		eventList.add(event);
		event = new ConferenceEvent(4, "Conference on MARS satelite launch ",
				80);
		eventList.add(event);
		event = new ConferenceEvent(5, "Success Talk on MOM", 10);
		eventList.add(event);
		event = new ConferenceEvent(6, "Talk On Spiritual Sucess", 65);
		eventList.add(event);
		event = new ConferenceEvent(7, "Talk On Green bangalore", 30);
		eventList.add(event);
		event = new ConferenceEvent(8, "Talk On Make In India Campaign", 130);
		eventList.add(event);
		event = new ConferenceEvent(9, "Talk on AIDS awareness program", 15);
		eventList.add(event);
		event = new ConferenceEvent(10, "Talk On xxxxx", 90);
		eventList.add(event);
		event = new ConferenceEvent(11, "Talk On xxxxxxxxxxxxxyy", 110);
		eventList.add(event);
		event = new ConferenceEvent(12, "Talk On yyyyyyyyyyy", 25);
		eventList.add(event);
		event = new ConferenceEvent(13, "Talk On uuuuuuuuuu", 85);
		eventList.add(event);
		return eventList;
	}

	private ArrayList<IEvent> createSortedEventList() {
		ArrayList<IEvent> eventList = new ArrayList<IEvent>();
		IEvent event = new ConferenceEvent(1, "Srikrishna Talk", 97);
		eventList.add(event);
		event = new ConferenceEvent(2, "Talk On sriram", 70);
		eventList.add(event);
		event = new ConferenceEvent(3, "Talk On Jaya hanuman", 55);
		eventList.add(event);
		event = new ConferenceEvent(4, "Talk On Durga", 45);
		eventList.add(event);
		event = new ConferenceEvent(5, "Talk On clean Bangalore", 40);
		eventList.add(event);
		event = new ConferenceEvent(6, "Talk On Majjigouri", 35);
		eventList.add(event);
		event = new ConferenceEvent(7, "Talk On ddddddddd", 30);
		eventList.add(event);
		event = new ConferenceEvent(8, "Talk On gayatri", 25);
		eventList.add(event);
		return eventList;
	}
}
