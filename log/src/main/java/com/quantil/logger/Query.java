package com.quantil.logger;

import java.util.Date;

public class Query {

	private Processor processor;
	private Date startDate;
	private Date endDate;
	private QueryType queryType;
	
	public Query(QueryType queryType) {
		this(queryType, null, null, null);
	}
	
	public Query(QueryType queryType, Processor processor, Date startDate, Date endDate) {
		super();
		this.queryType = queryType;
		this.processor = processor;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	public QueryType getQueryType() {
		return queryType;
	}
	public Processor getProcessor() {
		return processor;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(processor).append(",");
		queryBuilder.append(startDate).append(",");
		queryBuilder.append(endDate);
		return queryBuilder.toString();
	}
	
	
}

enum QueryType {
	QUERY("QUERY"),
	EXIT("EXIT");
	
	private String queryType;
	
	private QueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryType() {
		return queryType;
	}
}
