package laojy.idservice;


public class TimeService {
	private long outerTimeMillis;
	private long systemTimeMillis;
	private int sychronizeInterval;
	public TimeService() {
		this.sychronizeInterval = 60000;
	}
	
	public final void reset() {
		this.outerTimeMillis = remoteTimeMillis();
		this.systemTimeMillis = localTimeMillis();
	}
	
	protected long localTimeMillis() {
		return System.currentTimeMillis();
	}
	private long getInterval() {
		return System.currentTimeMillis() - this.systemTimeMillis;
	}
	protected long remoteTimeMillis() {
		return System.currentTimeMillis();
	}
	
	public final synchronized long currentTimeMillis() {
		Long interval = getInterval();
		if(interval > this.sychronizeInterval) {
			reset();
		}
		return this.outerTimeMillis+getInterval();
	}
}
