package laojy.mapper;

import laojy.entity.SeqNo;

public interface SeqNoMapper {
	int insert(SeqNo seqNo);
	int updateByPrimaryKey(SeqNo seqNo);
	SeqNo selectByType(String type);
}
