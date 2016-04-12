package com.quantil.logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LogRetriever {

	private Map<Processor, List<CpuStatistics>> processor2Stats = new HashMap<Processor, List<CpuStatistics>>();
	private LogFileReader logFileReader = new LogFileReader();
	
	public LogRetriever(String logFileName) {
		List<LogEntry> logEntries = this.logFileReader.read(logFileName);
		this.init(logEntries);
	}

	private void init(List<LogEntry> logEntries) {
		for (LogEntry currentLogEntry : logEntries) {
			Processor currentProcessor = currentLogEntry.getProcessor();
			List<CpuStatistics> cpuStatsForCurrentProcessor;
			if (this.processor2Stats.containsKey(currentProcessor)) {
				cpuStatsForCurrentProcessor = this.processor2Stats.get(currentProcessor);
			} else {
				cpuStatsForCurrentProcessor = new ArrayList<CpuStatistics>();
			}
			cpuStatsForCurrentProcessor.add(currentLogEntry.getCpuStatistics());
			this.processor2Stats.put(currentProcessor, cpuStatsForCurrentProcessor);
		}
	}
	
	public List<CpuStatistics> getStats(Processor processor) {
		return this.processor2Stats.get(processor);
	}
	
	public List<CpuStatistics> getStats(Processor processor, Date startDate, Date endDate) {
		List<CpuStatistics> cpuStatisticsForGivenProcessorWithinRange = new ArrayList<CpuStatistics>();
		List<CpuStatistics> cpuStatsForGivenProcessor = this.processor2Stats.get(processor);
		int indexOfStartDate = this.searchIndexOf(cpuStatsForGivenProcessor, startDate.getTime());
		int indexOfEndDate = this.searchIndexOf(cpuStatsForGivenProcessor, endDate.getTime());
		for (int i=indexOfStartDate; i< indexOfEndDate; i++) {
			cpuStatisticsForGivenProcessorWithinRange.add(cpuStatsForGivenProcessor.get(i));
		}
		return cpuStatisticsForGivenProcessorWithinRange;
	}

	private int searchIndexOf(List<CpuStatistics> cpuStatsForGivenProcessor, long time) {
		return this.searchIndexOfRecursively(cpuStatsForGivenProcessor, time, 0, cpuStatsForGivenProcessor.size() - 1);
	}

	private int searchIndexOfRecursively(List<CpuStatistics> cpuStatsForGivenProcessor, long timeToSearch, int start, int end) {
		if (start > end) {
			return start; 
		}
		int mid = (start + end) / 2;
		CpuStatistics midCpuStatistics = cpuStatsForGivenProcessor.get(mid);
		long timeStampInMillis = midCpuStatistics.getTimeStampInSeconds() * 1000;
		if (timeStampInMillis == timeToSearch) {
			return mid;
		} else if (timeStampInMillis < timeToSearch) {
			return this.searchIndexOfRecursively(cpuStatsForGivenProcessor, timeToSearch, mid + 1, end);
		} else {
			return this.searchIndexOfRecursively(cpuStatsForGivenProcessor, timeToSearch, start, mid -1);			
		}
	}
	
	public Query getQuery(String queryString) throws ParseException {
		String[] queryStringArray = queryString.split(" ");
		QueryType queryType = QueryType.valueOf(queryStringArray[0].toUpperCase().trim());
		if (queryType.equals(QueryType.EXIT)) {
			return new Query(queryType); 
		}
		Processor processor = new Processor(queryStringArray[1].trim(), Integer.parseInt(queryStringArray[2].trim()));
		Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(queryStringArray[3].trim() + " " + queryStringArray[4].trim());
		Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(queryStringArray[5].trim() + " " + queryStringArray[6].trim());
		return new Query(queryType, processor, startDate, endDate);
	}
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("invalid arguments ... "); 
			System.exit(1);
		}
		String logFileName = args[0];
		LogRetriever logRetriever = new LogRetriever(logFileName);
		@SuppressWarnings("resource")
		Scanner cin = new Scanner(System.in);
		while(cin.hasNext()) {
			String queryString = cin.nextLine();
			Query query = null;
			try {
				query = logRetriever.getQuery(queryString);
			} catch (ParseException e) {
				System.out.println("invalid query ... exiting");
				System.exit(1);
			}
			if (query.getQueryType().equals(QueryType.EXIT)) {
				System.exit(1);
			}
			List<CpuStatistics> stats = logRetriever.getStats(query.getProcessor(), query.getStartDate(), query.getEndDate());
			System.out.println(query.getProcessor());
			for (CpuStatistics stat : stats) {
				System.out.print(stat.toString());
				System.out.print(",");
			}
			System.out.println();
		}
	}
}
