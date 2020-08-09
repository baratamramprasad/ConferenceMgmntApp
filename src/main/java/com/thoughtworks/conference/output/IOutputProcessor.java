package com.thoughtworks.conference.output;



import java.util.ArrayList;

import com.thoughtworks.conference.exception.ConferenceEventException;

/**
 * It processes the sceduled event list and then creates the out put
 * @author Baratamr
 *
 * @param <E>
 */
public interface IOutputProcessor<E>{
/**
 * displays the scheduled event list.
 * @param scheduleList
 * @throws ConferenceEventException
 */
	public void displaySchedule(ArrayList<E> scheduleList) throws ConferenceEventException ;
}
