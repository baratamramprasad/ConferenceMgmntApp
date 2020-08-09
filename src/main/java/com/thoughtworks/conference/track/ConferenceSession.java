package com.thoughtworks.conference.track;

import java.util.ArrayList;
/**
 * It is session of the day in conference.It is composed of many events/Talks.
 * There are two sessions in a day.one is morning session before launch break and evening session after launch
 * @author Baratamr
 *
 */
public class ConferenceSession<E> {
	private ArrayList<E> eventList;
	private final int sessionDuration ;
	public ConferenceSession(int sessionDuration,ArrayList<E> eventList){
		this.eventList = eventList;
		this.sessionDuration = sessionDuration;
	}

	public ConferenceSession(int sessionDuration){
		this(sessionDuration, new ArrayList<E>());
	}
	/**
	 * adds the evnt sequentially at the end
	 * @param event
	 * @return
	 */
	public boolean addEvent(E event){
		if(!eventList.contains(event)){
			eventList.add(event);
			return true;
		}
		return false;
	}
	/**
	 * removes the event in the session from the end
	 * @param event
	 * @return
	 */
	public boolean removeEvent(E event){
		if(eventList.contains(event)){
			eventList.remove(event);
			return true;
		}
		return false;
	}
	/**
	 * gets the duration of the session in minutes
	 * @return
	 */
	public int getSessionDuration(){
		return this.sessionDuration;
	}
	
	public ArrayList<E> getEventList(){
		return eventList;
	}
}
