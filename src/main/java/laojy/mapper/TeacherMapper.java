package laojy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import laojy.entity.Teacher;

@Mapper
public interface TeacherMapper {
    int insert(Teacher record);

    int insertSelective(Teacher record);
    
    List<Teacher> queryAll();
}