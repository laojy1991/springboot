package laojy.mapper;

import org.apache.ibatis.annotations.Mapper;

import laojy.entity.Student;

@Mapper
public interface StudentMapper {
    int insert(Student record);

    int insertSelective(Student record);
}