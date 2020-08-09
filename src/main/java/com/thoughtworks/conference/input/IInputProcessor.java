package com.thoughtworks.conference.input;


import com.thoughtworks.conference.event.IEventListener;
import com.thoughtworks.conference.exception.ConferenceEventException;

/**
 * It processes the input raw data of type V and converts them into domains object of <code>E</code>
 * Domain Objects created in the <code>processInputData</code> method is notified through the
 * interface <code>IEventListener<E></code>
 * @author Baratamr
 *
 */
public interface IInputProcessor<E,V> {
	/**
	 * sets the eventListener through which events created are notified
	 * @param eventListener
	 */
	public void setEventlistener(IEventListener<E> eventListener) ;
	
	/**
	 * process the input data and creates the domain objects.
	 * These domain objects are notified through the IEventListener.
	 * @param inputRawdata raw data which can be either text file or xml/json file etc.
	 * @throws ConferenceEventException Exception raised when there is any error on processing the input data
	 */
	public void processInputData(V inputRawdata) throws ConferenceEventException ;
}
