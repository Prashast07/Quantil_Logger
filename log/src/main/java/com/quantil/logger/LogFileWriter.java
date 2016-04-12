package com.quantil.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class LogFileWriter {

	private static final String SPACE = " ";

	public void write(String outputFilePath, List<LogEntry> logs) {
		File file = new File(outputFilePath);
		if(!file.exists()) {
		    try {
		    	file.getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				String message = String.format("can't create file at path [%s]", outputFilePath);
				throw new RuntimeException(message, e);
			}
		}		
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e) {
			String message = String.format("file not found at path [%s]", outputFilePath);
			throw new RuntimeException(message, e);
		} catch (UnsupportedEncodingException e) {
			String message = String.format("unsupported encoding for file [%s]", outputFilePath);
			throw new RuntimeException(message, e);
		}
		for (LogEntry log : logs) {
			writer.println(this.toLogString(log));			
		}
		writer.close();
	}
	
	public String toLogString(LogEntry logEntry) {
		Processor processor = logEntry.getProcessor();
		CpuStatistics cpuStatistics = logEntry.getCpuStatistics();
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append(cpuStatistics.getTimeStampInSeconds() / 1000).append(SPACE);
		logBuilder.append(processor.getIp()).append(SPACE);
		logBuilder.append(processor.getCpu()).append(SPACE);
		logBuilder.append(cpuStatistics.getCpuUsage());
		return logBuilder.toString();
	}
}
