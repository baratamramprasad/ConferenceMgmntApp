package com.thoughtworks.conference.event.scheduler;

import java.util.ArrayList;
import java.util.List;
/**
 * It schedules the set of the events of type E into list of schedules which can be type of ConferesionTrack.
 * 
 * @author Baratamr
 *
 * @param <E>
 */
public interface IEventScheduler<E ,V> {

	/**
	 * Gets the list of  schedule of type V.i.e List of tracks
	 * @param eventList list of events of type E
	 * @return list of scedules of type V.
	 */
	public ArrayList<V> schedule(List<E> eventList);
}
