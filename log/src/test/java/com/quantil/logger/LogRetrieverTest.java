package com.quantil.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class LogRetrieverTest {

	@Test
	public void test01() throws ParseException {
		LogRetriever logRetriever = new LogRetriever("/log-for-1-hour.in");
		Query query = logRetriever.getQuery("QUERY 192.168.1.10 1 2016-04-06 22:30 2016-04-06 22:35");
		assertNotNull(query);
		assertEquals(QueryType.QUERY, query.getQueryType());
		assertEquals("192.168.1.10", query.getProcessor().getIp());
		assertEquals(1, query.getProcessor().getCpu());
		System.out.println(query.getStartDate().getTime());
		System.out.println(query.getEndDate().getTime());
		assertNotNull(query.getStartDate());
		assertNotNull(query.getEndDate());
		
	}

	@Test
	public void test02() throws ParseException {
		LogRetriever logRetriever = new LogRetriever("/log-for-1-hour.in");
		Query query = logRetriever.getQuery("EXIT");
		assertNotNull(query);
		assertEquals(QueryType.EXIT, query.getQueryType());
	}

	@Test
	public void test1() {
		LogRetriever logRetriever = new LogRetriever("/log-for-1-hour.in");
		Processor processor = new Processor("192.168.0.1", 0);
		List<CpuStatistics> stats = logRetriever.getStats(processor);
		System.out.println(stats.size());
		System.out.println(stats);
	}

	@Test
	public void test2() {
		long time1 = System.currentTimeMillis();
		LogRetriever logRetriever = new LogRetriever("/log-for-1-day.in");
		Processor processor = new Processor("192.168.0.2", 0);
		long time2 = System.currentTimeMillis();
		System.out.println("initTime ... " + (time2 - time1) + " ms.");
		List<CpuStatistics> stats = logRetriever.getStats(processor);
		long time3 = System.currentTimeMillis();;
		System.out.println("queryTime ... " + (time3 - time2) + " ms.");
		System.out.println(stats.size());
		System.out.println(stats);
	}

	@Test
	public void test3() {
		LogRetriever logRetriever = new LogRetriever("/log-for-1-hour.in");
		Processor processor = new Processor("192.168.0.1", 0);
		Date startDate = new Date(1460006500000L);
		Date endDate =   new Date(1460009000000L);
		List<CpuStatistics> stats = logRetriever.getStats(processor, startDate, endDate);
		System.out.println(stats.size());
		System.out.println(stats);
	}

	@Test
	public void test4() {
		LogRetriever logRetriever = new LogRetriever("/log-for-1-hour.in");
		Processor processor = new Processor("192.168.0.10", 1);
		Date startDate = new Date(1460007000000L);
		Date endDate =   new Date(1460007300000L);
		List<CpuStatistics> stats = logRetriever.getStats(processor, startDate, endDate);
		System.out.println(stats.size());
		System.out.println(stats);
	}

}
