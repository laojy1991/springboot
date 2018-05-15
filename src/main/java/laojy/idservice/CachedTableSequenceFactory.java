package laojy.idservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import laojy.entity.SeqNo;
import laojy.mapper.SeqNoMapper;


public class CachedTableSequenceFactory extends AbstractSequenceFactory {

	private long cacheSize = 10;
	private volatile AtomicLong currentIndex = null;
	private volatile Long cacheIndex;
	private int step = 1;
	boolean dateCutoff;
	private SeqNoMapper seqNoMapper;
	private long accountingDate = 0;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	private String date;
	private TimeService timeService = new TimeService();
	
	
	public CachedTableSequenceFactory(SeqNoMapper seqNoMapper) {
		super();
		this.seqNoMapper = seqNoMapper;
	}

	@Override
	protected long[] internalGenerate() {
		synchronized (this) {
			long index = 0L;
			String today = simpleDateFormat.format(new Date());
			if(dateCutoff) {
				if(!today.equals(date)) {
					refresh();
					date = simpleDateFormat.format(new Date());
				}
			}
			if(currentIndex==null) {
				refresh();
			}
			index = currentIndex.addAndGet(step);
			if(index>cacheIndex) {
				refresh();
				index = currentIndex.addAndGet(step);
			}
			return new long[] {index,accountingDate};
		}
	}
	
	private void refresh() {
		accountingDate = getCurrentAccountingDate();
		SeqNo seqNo = seqNoMapper.selectByType(type);
		if(seqNo==null) {
			cacheIndex = Long.valueOf(0)+cacheSize;
			currentIndex = new AtomicLong(Long.valueOf(0));
			SeqNo seqNoTemp = new SeqNo();
			seqNoTemp.setId(cacheIndex);
			seqNoTemp.setDateTime(new Date(accountingDate));
			seqNoTemp.setType(type);
			seqNoMapper.insert(seqNoTemp);
		}else {
			Date lastAccountingDate = seqNo.getDateTime();
			if(dateCutoff&&DateTime.compareDateWithoutTime(new Date(accountingDate), lastAccountingDate)>0) {
				date = simpleDateFormat.format(new Date(accountingDate));
				cacheIndex = cacheSize;
				currentIndex = new AtomicLong();
			}else {
				cacheIndex = seqNo.getId() + cacheSize;
				currentIndex = new AtomicLong(seqNo.getId());
				if(DateTime.compareDateWithoutTime(new Date(accountingDate), lastAccountingDate)<0) {
					accountingDate = lastAccountingDate.getTime();
				}
			}
			forUpdate(cacheIndex, accountingDate);
		}
	}

	private void forUpdate(Long index,long accountingDate) {
		SeqNo seqNo = new SeqNo();
		seqNo.setId(index);
		seqNo.setDateTime(new Date(accountingDate));
		seqNo.setType(type);
		seqNoMapper.updateByPrimaryKey(seqNo);
	}
	
	private long getCurrentAccountingDate() {
		return timeService.currentTimeMillis();
	}
	
	public long getAccountingDate() {
		return accountingDate;
	}

	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setDateCutoff(boolean dateCutoff) {
		this.dateCutoff = dateCutoff;
	}
}
