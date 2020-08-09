package com.thoughtworks.conference.event;
/**
 * conference event which has title,duration.
 * It also maintains the booked time of the conference event.
 * BookedTime is computed from the event duration.
 * @author Baratamr
 *
 */
public class ConferenceEvent implements IEvent{
	
	private int bookedDurationModulo = 5;

	private int eventID = -1;
	
	private String eventTitle = "";
	
	private int eventDuration = 0;
	
	private int bookedDuration =0;
	
	/**
	 * Constructor for event with id,title,duration
	 * @param id
	 * @param title
	 * @param durationinMin
	 */
	public ConferenceEvent(int id,String title,int durationinMin){
		this.eventID = id;
		this.eventTitle = title;
		this.eventDuration = durationinMin;
		initBookedDuration();
	}
	
	/**
	 * Constructor for event with id,title,duration and bookedDurationModulo 
	 * i.e minimum duration booked for the smallest event
	 * @param id
	 * @param title
	 * @param durationinMin
	 */
	public ConferenceEvent(int id,String title,int durationinMin,int bookedDurationModulo){
		this.eventID = id;
		this.eventTitle = title;
		this.eventDuration = durationinMin;
		this.bookedDurationModulo = bookedDurationModulo;
		initBookedDuration();
	}
	
	/**
	 * conference shall be booked 50 min for 47min event
	 */
	private void initBookedDuration() {
		int reminder =eventDuration%bookedDurationModulo;
		if (reminder == 0) {
			bookedDuration = eventDuration;
		} else {
			bookedDuration = eventDuration + (bookedDurationModulo - reminder);
		}
		
	}

	@Override
	public int getID() {
		return eventID;
	}

	@Override
	public String getTitle() {
		
		return eventTitle;
	}

	@Override
	public int getDuration() {
		return eventDuration;
	}

	@Override
	public int getBookedDuration() {
		return bookedDuration;
	}

}
