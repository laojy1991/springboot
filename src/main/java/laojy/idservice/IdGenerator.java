package laojy.idservice;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import laojy.mapper.SeqNoMapper;

@Component
public class IdGenerator {
	
	@Autowired
	SeqNoMapper seqNoMapper;
	
	HashMap<String, CachedTableSequenceFactory> hashMap = new HashMap<>();
	
	
	@Transactional(rollbackFor = RuntimeException.class)
	public String generate(String type,String pattern) {
		return generateWithDateCutoff(type,pattern,true);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public String generateWithDateCutoff(String type,String pattern,boolean dateCutoff) {
		return generateWithStep(type,pattern,dateCutoff,1);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public String generateWithStep(String type,String pattern,boolean dateCutoff,int step) {
		return generateWithCacheSize(type,pattern,dateCutoff,step,200);
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public String generateWithCacheSize(String type,String pattern,boolean dateCutoff,int step,long cacheSize) {
		CachedTableSequenceFactory factory = hashMap.get(type);
		if(factory==null) {
			factory = new CachedTableSequenceFactory(seqNoMapper);
			hashMap.put(type, factory);
		}
		factory.setType(type);
		factory.setFormat(pattern);
		factory.setDateCutoff(dateCutoff);
		factory.setCacheSize(cacheSize);
		factory.setStep(step);
		return factory.create().toString();
	}
}
