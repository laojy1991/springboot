package laojy.idservice;

import java.util.Date;

import com.sun.org.apache.xpath.internal.operations.String;

public abstract class AbstractSequenceFactory {
	protected String lastType;
	protected String type;
	public void setFormat(String pattern) {
		this.sequenceFormat = SequenceFormat.newInstance(pattern);
	}
	
	private SequenceFormat sequenceFormat;
	
	public void setType(String type) {
		this.lastType = this.type;
		this.type  = type;
	}
	
	public Object create() {
		if(this.type == null) {
			throw new RuntimeException("you must setType(String) in SequenceFactory");
		}
		long[] result = internalGenerate();
		Object[] formatString = new String[] {format(result[0], result[1])};
		return formatString[0];
	}
	
	private String format(long id,long timestamp) {
		return this.sequenceFormat.format(id,new Date(timestamp));
	}
	
	protected abstract long[] internalGenerate();
}
