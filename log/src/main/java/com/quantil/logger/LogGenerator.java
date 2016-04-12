package com.quantil.logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LogGenerator {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final long CURRENT_TIME = System.currentTimeMillis();
	private static final long MILLISECONDS_IN_1_DAY = 86400000L;
	private static final Date DEFAULT_START_DATE = new Date(CURRENT_TIME - MILLISECONDS_IN_1_DAY);
	private static final Date DEFAULT_END_DATE =  new Date(CURRENT_TIME);
	
	private List<Processor> processors = new ArrayList<Processor>();
	private Random randomGenerator = new Random();
	private LogFileWriter logFileWriter = new LogFileWriter();
	
	public LogGenerator() {
		this.initProcessors();
	}
	
	private void initProcessors() {
		for (int processorCount = 0; processorCount < 1000; processorCount++) {
			int mod = processorCount / 256;
			int rem = processorCount % 256;
			this.processors.add(new Processor("192.168." + mod + "." + rem, 0));
			this.processors.add(new Processor("192.168." + mod + "." + rem, 1));
		}
	}
	
	public int getRandomCpuUsage(int endRange) {
		return randomGenerator.nextInt(endRange);
	}

	public void generate(String logFilePath) {
		Date startDate = DEFAULT_START_DATE;
		Date endDate = DEFAULT_END_DATE;
		this.generate(logFilePath, startDate, endDate);
	}

	public void generate(String logFilePath, Date startDate, Date endDate) {
		System.out.println("logFilePath ... "  + logFilePath);
		System.out.println("startDate ... " + dateFormat.format(startDate).toString());
		System.out.println("endDate ... " + dateFormat.format(endDate).toString());
		List<LogEntry> allLogs = this.generate(startDate, endDate);
		this.logFileWriter.write(logFilePath, allLogs);
	}
	
	public List<LogEntry> generate(Date startDate, Date endDate) {
		List<LogEntry> logsForGivenRange = new ArrayList<LogEntry>();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		for (long currentTime = startTime; currentTime < endTime; currentTime += 1000*60) {
			logsForGivenRange.addAll(this.generate(currentTime));
		}
		return logsForGivenRange;
	}
	
	public List<LogEntry> generate(long timeStamp) {
		List<LogEntry> logsPerMinute = new ArrayList<LogEntry>();
		for (Processor processor : processors) {
			CpuStatistics cpuStatistics = new CpuStatistics(timeStamp, this.getRandomCpuUsage(100));
			logsPerMinute.add(new LogEntry(processor, cpuStatistics));
		}
		return logsPerMinute;
	}
	
	public static void main(String[] args) {
		LogGenerator logGenerator = new LogGenerator();
		if (args.length != 1) {
			System.out.println("invalid arguments ... "); 
			System.exit(1);
		}
		String logFilePath = args[0];
		logGenerator.generate(logFilePath);
	}
}
