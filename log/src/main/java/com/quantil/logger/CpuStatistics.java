package com.quantil.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CpuStatistics {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private long timeStampInSeconds;
	private int cpuUsage;
	public long getTimeStampInSeconds() {
		return timeStampInSeconds;
	}
	public void setTimeStampInSeconds(long timeStamp) {
		this.timeStampInSeconds = timeStamp;
	}
	public int getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(int cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public CpuStatistics(long timeStamp, int cpuUsage) {
		super();
		this.timeStampInSeconds = timeStamp;
		this.cpuUsage = cpuUsage;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(").append(dateFormat.format(new Date(timeStampInSeconds * 1000)).toString()).append(",").append(cpuUsage).append("%").append(")");
		return builder.toString();
	}

	
}
