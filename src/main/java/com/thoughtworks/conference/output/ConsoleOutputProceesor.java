package com.thoughtworks.conference.output;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.exception.ConferenceEventException;
import com.thoughtworks.conference.track.ConferenceSession;
import com.thoughtworks.conference.track.ConferenceTrack;

/**
 * It prints the scedule of informatio in the console.All the time units are
 * considered as minutes
 * 
 * @author Baratamr
 * 
 */
public class ConsoleOutputProceesor implements
		IOutputProcessor<ConferenceTrack<ConferenceSession<IEvent>>> {

	private int trackID = 0;
	/**
	 * default start time of the schedule in the day.
	 */
	private String dayStartTime = "09:00";

	@SuppressWarnings("unused")
	private String lunchTime = "12:00";

	private int lunchBreakDuration = 60;

	/**
	 * It is calender which maintains the time at which every event shall start
	 */
	private Calendar calender = null;

	public ConsoleOutputProceesor(String startTime, String lunchTime,
			int lunchBreakDuration) {
		this.dayStartTime = startTime;
		this.lunchTime = lunchTime;
		this.lunchBreakDuration = lunchBreakDuration;
	}

	public void displaySchedule(
			ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList) throws ConferenceEventException {
		for (ConferenceTrack<ConferenceSession<IEvent>> track : trackList) {
			System.out.println("Track " + trackID++);
			initializeDate();
			System.out.println("----------------------------------------");
			ConferenceSession<IEvent> morningSession = track.getSessionList()
					.get(0);
			displayEventsInSession(morningSession);
			System.out.println(DateFormat.getTimeInstance(DateFormat.SHORT)
					.format(calender.getTime()) + " Lunch ");
			calender.add(Calendar.MINUTE, lunchBreakDuration);
			if (track.getSessionList().size() > 1) {
				ConferenceSession<IEvent> eveningSession = track
						.getSessionList().get(1);
				displayEventsInSession(eveningSession);
			}
			System.out.println("5:00 PM Network Event ");
		}
	}

	/**
	 * 
	 * @throws ConferenceEventException
	 */
	private void initializeDate() throws ConferenceEventException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:MM");
		Date date;
		try {
			date = dateFormat.parse(dayStartTime);
			// previouseEventStartTime = date;
		} catch (ParseException e) {
			throw new ConferenceEventException(
					ConferenceEventException.OUTPUT_PROCESS_EXCEPTION,
					"error on parsing Date", e.getCause());
		}
		calender = Calendar.getInstance();
		calender.setTime(date);

	}

	/**
	 * 
	 * @param session
	 * @throws ConferenceEventException
	 */
	private void displayEventsInSession(ConferenceSession<IEvent> session)
			throws ConferenceEventException {
		for (IEvent event : session.getEventList()) {
			System.out.println("" + getEventStartTime(event) + " "
					+ event.getTitle() + " " + event.getDuration() + "min");
			calender.add(Calendar.MINUTE, event.getBookedDuration());
		}
	}

	/**
	 * 
	 * @param event
	 * @return
	 * @throws ConferenceEventException
	 */
	private String getEventStartTime(IEvent event)
			throws ConferenceEventException {
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(
				calender.getTime());

	}
}
