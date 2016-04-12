package com.quantil.logger;

public class LogEntry {

	private Processor processor;
	private CpuStatistics cpuStatistics;
	
	public LogEntry(Processor processor, CpuStatistics cpuStatistics) {
		super();
		this.processor = processor;
		this.cpuStatistics = cpuStatistics;
	}
	
	public Processor getProcessor() {
		return processor;
	}
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}
	public CpuStatistics getCpuStatistics() {
		return cpuStatistics;
	}
	public void setCpuStatistics(CpuStatistics cpuStatistics) {
		this.cpuStatistics = cpuStatistics;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append("\n");
		builder.append("processor=").append(processor).append("\n");;
		builder.append("cpuStats=").append(cpuStatistics).append("\n");;
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
