package com.thoughtworks.conference.event;

import java.util.ArrayList;

import com.thoughtworks.logger.Logger;




/**
 * 
 * Model mainstains the list of events that are created by Input Processor
 * @author Baratamr
 *
 */
public class EventModel implements IEventListener<IEvent>{
	
	ArrayList<IEvent> eventList = null;
	
	@Override
	public void eventCreated(IEvent event, Object payload) {
		if (Logger.LOGGER_ON) {
			Logger.printLog("EventModel", "eventCreated", "eventlist :"
					+ ((eventList != null) ? "size =" + eventList.size()
							: "null"));
		}
		if(eventList == null){
			eventList = new ArrayList<IEvent>();
		}
		eventList.add(event);
	}

	public void clearModel(){
		if (Logger.LOGGER_ON) {
			Logger.printLog("EventModel", "clearModel", "eventlist :"
					+ ((eventList != null) ? "size =" + eventList.size()
							: "null"));
		}
		if(eventList != null) {
			eventList.clear();
		}
		eventList = null;
	}
	/**
	 * returns the copy of the eventlist
	 * @return
	 */
	public ArrayList<IEvent> getEventList(){
		ArrayList<IEvent> eventListCopy = new ArrayList<IEvent>();
		for(IEvent evt:eventList){
			eventListCopy.add(evt);
		}
		return eventListCopy;
	}
}
