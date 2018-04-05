package laojy.mapper;

import org.apache.ibatis.annotations.Mapper;

import laojy.entity.Teacher;

@Mapper
public interface TeacherMapper {
    int insert(Teacher record);

    int insertSelective(Teacher record);
}