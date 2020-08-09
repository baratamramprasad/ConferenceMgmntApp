package com.thoughtworks.conference.event;


/**
 * 
 * During input processing, the events are created and these are notified by this interface.
 * @author Baratamr
 *
 */
public interface IEventListener<E> {

	public void eventCreated(E event,Object payload);
}
