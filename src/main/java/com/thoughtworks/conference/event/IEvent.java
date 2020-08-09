package com.thoughtworks.conference.event;

/**
 * 
 * 
 * @author Baratamr
 *
 */
public interface IEvent {
/**
 * gets the identifier of the event
 * @return int as identifier
 */
	public int getID();
	/**
	 * gets the title of the event
	 * @return String as title
	 */
	public String getTitle();
	
	/**
	 * gets the duration of the event in minutes.
	 * @return int as duration  in minutes 
	 */
	public int getDuration();
	
	/**
	 * gets the booked duration of the event in minutes.
	 * @return int as duration  in minutes 
	 */
	public int getBookedDuration();
}
