package com.thoughtworks.conference.track;

import java.util.ArrayList;
/**
 * It is track of events that are scheduled for single days.
 * It has two sessions i.e morning and evening session.
 * @author Baratamr
 *
 * @param <E>
 */
public class ConferenceTrack <E>{
	private ArrayList<E> sessionList;
	public ConferenceTrack(ArrayList<E> eventList){
		this.sessionList = eventList;
	}

	public ConferenceTrack(){
		this.sessionList = new ArrayList<E>();
	}
	/**
	 * adds the session sequentially at the end
	 * @param session
	 * @return
	 */
	public boolean addSession(E session){
		if(!sessionList.contains(session)){
			sessionList.add(session);
			return true;
		}
		return false;
	}
	/**
	 * removes the session in the session from the end
	 * @param session
	 * @return
	 */
	public boolean removeSession(E session){
		if(sessionList.contains(session)){
			sessionList.remove(session);
			return true;
		}
		return false;
	}
	
	public ArrayList<E> getSessionList(){
		return sessionList;
	}
}
