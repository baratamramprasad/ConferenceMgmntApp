package com.thoughtworks.conference.input;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.conference.event.EventModel;
import com.thoughtworks.conference.exception.ConferenceEventException;
import com.thoughtworks.logger.Logger;

public class InputProcessorTest{
	InputProcessor inputProcessor = null;
	EventModel eventModel =null;
	
	@Before
	public void setUp() throws Exception {
		Logger.printLog(this, "setup", "started");
		eventModel = new EventModel();
		inputProcessor  = new InputProcessor("min",eventModel);
		
	}

	@After
	public void tearDown() throws Exception {
		Logger.printLog(this, "tearDown", "tearing down");
		
		eventModel.clearModel();
		eventModel = null;
		inputProcessor = null;
	}
	
	@Test
	public void testWorkSpace() {
	
		Logger.printLog(this, "testWorkSpace", "testing the eclipse setup for junit");
	}
	
	@Test
	public void testProcessInputFile() throws ConferenceEventException, FileNotFoundException {
		File inputFile = new File("./src/test/resources/conferenceinput.txt");
		try (Scanner scanner = new Scanner(inputFile)) {
			int numLines = 0;
			while (scanner.hasNextLine()) {
				scanner.nextLine();
				numLines++;
			}
			inputProcessor.processInputData(inputFile);
			assertEquals(eventModel.getEventList().size(), numLines);
		}

	}
}

