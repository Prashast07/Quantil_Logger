package com.quantil.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogFileReader {

	private static final String SPACE = " ";

	public List<LogEntry> read(String logFileName) {
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		InputStream resourceAsStream = this.getClass().getResourceAsStream(logFileName);
		if (resourceAsStream==null) {
			File file = new File(logFileName);
			try {
				resourceAsStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("log file not found : " + logFileName);
			}
		}
		@SuppressWarnings("resource")
		Scanner cin = new Scanner(resourceAsStream);
		while(cin.hasNextLine()) {
			String logString = cin.nextLine();
			LogEntry logEntry = this.toLogEntry(logString);
			logEntries.add(logEntry);
		}
		return logEntries;
	}

	private LogEntry toLogEntry(String logString) {
		String[] logEntryCharacters = logString.trim().split(SPACE);
		CpuStatistics cpuStatistics = new CpuStatistics(
				Long.parseLong(String.valueOf(logEntryCharacters[0])), 
				Integer.parseInt(String.valueOf(logEntryCharacters[3])));
		Processor processor = new Processor(
				String.valueOf(logEntryCharacters[1]), 
				Integer.parseInt(String.valueOf(logEntryCharacters[2])));
		LogEntry logEntry = new LogEntry(processor, cpuStatistics);
		return logEntry;
	}
}
