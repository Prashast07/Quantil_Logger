package com.quantil.logger;

import java.util.Date;

import org.junit.Test;

public class LogGeneratorTest {

	@Test
	public void test1() {
		LogGenerator logGenerator = new LogGenerator();
		String logFilePath = this.createOutputFileFullPath("log-for-1-hour.in");
		Date startDate = new Date(1460006411000L);
		Date endDate = new Date(1460010012000L);
		logGenerator.generate(logFilePath, startDate, endDate);
	}

	
	@Test
	public void test2() {
		LogGenerator logGenerator = new LogGenerator();
		String logFilePath = this.createOutputFileFullPath("log-for-1-day.in");
		Date startDate = new Date(1460355066000L);
		Date endDate = new Date(1460441466000L);
		logGenerator.generate(logFilePath, startDate, endDate);
	}

	@Test
	public void test3() {
		LogGenerator logGenerator = new LogGenerator();
		String logFilePath = this.createOutputFileFullPath("log-for-1-day-default.in");
		logGenerator.generate(logFilePath);
	}

	public String createOutputFileFullPath(String fileName) {
		StringBuilder outputFilePathBuilder = new StringBuilder();
		String userDirectory = System.getProperty("user.dir");
		outputFilePathBuilder.append(userDirectory);
		String fileSeparator = System.getProperty("file.separator");
		outputFilePathBuilder.append(fileSeparator);
		outputFilePathBuilder.append("target").append(fileSeparator);
		outputFilePathBuilder.append(fileName);
		String outputFilePath = outputFilePathBuilder.toString();
		System.out.println("output ... "  + outputFilePath);
		return outputFilePath;
	}

}
