package com.thoughtworks.conference.event.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.conference.event.IEvent;
import com.thoughtworks.conference.track.ConferenceSession;
import com.thoughtworks.conference.track.ConferenceTrack;
import com.thoughtworks.logger.Logger;

/**
 * It schedules the list of conferenceEvents into Sessions which are grouped as
 * ConferenceTracks. It schedules the events based on its duration specified by
 * <code> IEvent.getBookedDuration</code>and availability of the Session time.
 * 
 * It first finds the sub set of events in the given event list that can be
 * occupied in the session such that sum of the event duration is equal to the
 * duration of the session.This subset of the events is called as perfect fit
 * sublist. In case,it does not find the subset of the events that are perfectly
 * fit in to session, it tries to get sub set of events which fits best i.e sub
 * set of events whose sum of event duration is nearly equal to session duration
 * 
 * 
 * @author Baratamr
 * 
 */
public class BestFitEventScheduler implements IEventScheduler<IEvent, ConferenceTrack<ConferenceSession<IEvent>>> {

	/**
	 * first session is session before lunch break;
	 */
	private final int FIRST_SESSION_DURATION = 180;
	/**
	 * second session is after lunch break and before maintainance time
	 */
	private final int SECOND_SESSION_DURATION = 240;

	/**
	 * while finding the sublist of events which will fit in Session exactly,in
	 * getperfectEventList method, we will keep tract of events which can occupy
	 * into Session with minimal left over time. i.e Diffrence of Session time and
	 * sum of the Events duration in bestFitEventList is minimal.It is maintained by
	 * smallestRemainingTimeDuration
	 */
	private ArrayList<IEvent> bestFitEventList = null;
	private int smallestRemainingTimeDuration = -1;



	/**
	 * schedules the list of events into list of ConferenceTrack
	 */
	@Override
	public ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> schedule(List<IEvent> eventList) {
		if (Logger.LOGGER_ON) {
			Logger.printLog("BestFitEventScheduler", "schedule",
					"eventlist :" + ((eventList != null) ? "size =" + eventList.size() : "null"));
		}
		if (eventList == null || eventList.size() == 0)
			return null;
		ArrayList<ConferenceTrack<ConferenceSession<IEvent>>> trackList = new ArrayList<ConferenceTrack<ConferenceSession<IEvent>>>();
		while (eventList.size() > 0) {
			if (Logger.LOGGER_ON) {
				Logger.printLog("BestFitEventScheduler", "schedule",
						" inside while eventlist :" + ((eventList != null) ? "size =" + eventList.size() : "null"));
			}
			trackList.add(createConferenceTrack(eventList));
		}
		if (Logger.LOGGER_ON) {
			Logger.printLog("BestFitEventScheduler", "schedule",
					"trackList :" + ((trackList != null) ? "size =" + trackList.size() : "null"));
		}
		return trackList;
	}

	/**
	 * creates the conferencetrack that consists of two ConferenceSessions The input
	 * specified by eventList will be reduced.
	 * 
	 * @param eventList
	 * @return
	 */
	private ConferenceTrack<ConferenceSession<IEvent>> createConferenceTrack(List<IEvent> eventList) {
		if (Logger.LOGGER_ON) {
			Logger.printLog("BestFitEventScheduler", "createConferenceTrack",
					"eventList :" + ((eventList != null) ? "size =" + eventList.size() : "null"));
		}
		ConferenceTrack<ConferenceSession<IEvent>> track = new ConferenceTrack<ConferenceSession<IEvent>>();
		if (eventList.size() > 0) {
			ConferenceSession<IEvent> morningSession = new ConferenceSession<IEvent>(FIRST_SESSION_DURATION);
			fillSession(morningSession, eventList);
			track.addSession(morningSession);
			if (Logger.TRACE_ON) {
				Logger.printLog("BestFitEventScheduler", "createConferenceTrack",
						" after creation of morning session eventList :"
								+ ((eventList != null) ? "size =" + eventList.size() : "null"));
			}
		}
		if (eventList.size() > 0) {
			ConferenceSession<IEvent> eveningSession = new ConferenceSession<IEvent>(SECOND_SESSION_DURATION);
			fillSession(eveningSession, eventList);
			track.addSession(eveningSession);
		}
		if (Logger.TRACE_ON) {
			Logger.printLog("BestFitEventScheduler", "createConferenceTrack",
					" after creation of evening session eventList :"
							+ ((eventList != null) ? "size =" + eventList.size() : "null"));
		}

		if (Logger.LOGGER_ON) {
			Logger.printLog("BestFitEventScheduler", "createConferenceTrack",
					" track->session list size: " + track.getSessionList().size());
		}
		return track;
	}

	/**
	 * fills the conference session by events from given eventlist.It fills as per
	 * event duration and session duration under assumption that no event will
	 * exceed the session durations
	 * 
	 * @param session
	 * @param eventList
	 */
	private void fillSession(ConferenceSession<IEvent> session, List<IEvent> eventList) {
		if (Logger.LOGGER_ON) {
			Logger.printLog("BestFitEventScheduler", "fillSession",
					"eventList :" + ((eventList != null) ? "size =" + eventList.size() : "null"));
		}
		clearBestFitEventlist();
		ArrayList<IEvent> subList = getPerfectSubEventList(0, eventList, session.getSessionDuration(),
				new ArrayList<IEvent>());
		if (Logger.TRACE_ON) {
			Logger.printLog("BestFitEventScheduler", "fillSession",
					" perfect fit subList :" + ((subList != null) ? "size =" + subList.size() : "null"));
		}
		// if perfect EventList is not found,then go with Bestfiteventlist
		if (subList.size() == 0) {
			subList = getBestfitEventList();
		}
		if (Logger.TRACE_ON) {
			Logger.printLog("BestFitEventScheduler", "fillSession",
					" Best fit subList :" + ((subList != null) ? "size =" + subList.size() : "null"));
		}
		for (IEvent e : subList) {
			session.addEvent(e);
			eventList.remove(e);
		}
	}

	/**
	 * clears the data cached during scheduling
	 */
	private void clearBestFitEventlist() {
		if (bestFitEventList != null) {
			bestFitEventList.clear();
			bestFitEventList = null;
		}
		smallestRemainingTimeDuration = -1;
	}

	/**
	 * List of events that fits the Session best i.e after scheduling the list of
	 * events into session the time left in session is very minimal which can not be
	 * used for any other event. This data is cached during finding the perfect fir
	 * event list
	 * 
	 * @return
	 */
	private ArrayList<IEvent> getBestfitEventList() {
		if (bestFitEventList != null) {
			return bestFitEventList;
		}
		return new ArrayList<IEvent>();
	}

	/**
	 * It searches the eventList and finds the sublist of events whose sum of event
	 * durations is equal to duration of sessions.When it finds the first subset of
	 * events combination, then it stops to process further During this process, it
	 * also caches the sublist of events whose sum of duration is nearly equal to
	 * session duration. Here the "nearly equals to" means the time difference
	 * between the session duration and sum of event duration of the sublist is
	 * minimal.this cached sublist will be used as best-fit event list.This is
	 * bestFitEventList which will be used as secondary case when we do not find
	 * bestfiteventlist
	 * 
	 * @param startIndex               index of the events in the eventList.It is
	 *                                 initially set as 0;
	 * @param eventList                list of the events to be scheduled
	 * @param remainingSessionDuration time left in the session as scheduling is
	 *                                 proceeded i.e the events are occupied in
	 *                                 session initialy it is set as
	 *                                 SessionDuration.
	 * @param targetEventList          sublist of the events that are occupied in
	 *                                 session during sorting
	 * @return list of the events whose sum of durations is equal to session
	 *         duration.
	 */
	private ArrayList<IEvent> getPerfectSubEventList(int startIndex, List<IEvent> eventList,
			int remainingSessionDuration, ArrayList<IEvent> targetEventList) {
		if (Logger.TRACE_ON) {
			Logger.printLog("BestFitEventScheduler", "getPerfectSubEventList",
					"eventList :" + ((eventList != null) ? "size =" + eventList.size() : "null")
							+ "smallestRemainingTimeDuration :" + smallestRemainingTimeDuration);
			Logger.printLog("BestFitEventScheduler", "getPerfectSubEventList",
					"startIndex :" + startIndex + " remainingSessionDuration:" + remainingSessionDuration);
			Logger.printLog("BestFitEventScheduler", "getPerfectSubEventList",
					"targetEventList :" + ((targetEventList != null) ? "size =" + targetEventList.size() : "null"));
			Logger.printLog("", "", "Target eventlist:");
			for (IEvent evt : targetEventList) {
				System.out.print(" -->" + evt.getBookedDuration());
			}
			Logger.printLog("", "", "");
		}

		ArrayList<IEvent> returnList = new ArrayList<IEvent>();
		if (smallestRemainingTimeDuration == -1) {
			smallestRemainingTimeDuration = remainingSessionDuration;
			bestFitEventList = new ArrayList<IEvent>();
		}
		// fill the BestFitEventList when we are trying to get perfect list
		if (smallestRemainingTimeDuration > remainingSessionDuration) {
			smallestRemainingTimeDuration = remainingSessionDuration;
			bestFitEventList.clear();
			bestFitEventList.addAll(targetEventList);
			
		}

		// return list
		if (startIndex >= eventList.size() || remainingSessionDuration == 0 || eventList.size() == 0) {

			return returnList;
		}
		for (int i = startIndex; i < eventList.size(); i++) {
			if (Logger.TRACE_ON) {
				Logger.printLog("BestFitEventScheduler", "getPerfectSubEventList", "startIndex :" + startIndex
						+ " remainingSessionDuration:" + remainingSessionDuration + " i :" + i);
			}
			IEvent targetEvent = eventList.get(i);
			// check if the event can be accommodated in specified session
			
			if (remainingSessionDuration - targetEvent.getBookedDuration() >= 0) {
				remainingSessionDuration -= targetEvent.getBookedDuration();
			} else {
				continue;
			}

			returnList.add(eventList.get(i));
			targetEventList.add(eventList.get(i));
			// if no time in this session is left, it is perfect sublist
			if (remainingSessionDuration == 0) {
				break;
			}

			ArrayList<IEvent> subList = getPerfectSubEventList(i + 1, eventList, remainingSessionDuration,
					targetEventList);
			if (Logger.TRACE_ON) {
				Logger.printLog("BestFitEventScheduler", "getPerfectSubEventList", "subList :" + subList.size());
			}
			// if perfectList is not found,then iterate further in eventList
			if (subList.size() > 0) {
				returnList.addAll(subList);
				eventList.removeAll(subList);
				// This is required to break here to return the perfectList with reduced eventList.
				break;
			} else {
				targetEventList.remove(eventList.get(i));
				returnList.remove(eventList.get(i));
				remainingSessionDuration += eventList.get(i).getBookedDuration();
			}
		}
		if (Logger.TRACE_ON) {
			Logger.printLog("BestFitEventScheduler", "getPerfectSubEventList", "returnList :" + returnList.size());
		}
		return returnList;
	}

}
