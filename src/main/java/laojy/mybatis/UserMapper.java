package laojy.mybatis;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import laojy.entity.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from user")
	public List<User> findAll();
	
	@Insert("insert into user(name,pass) values(#{name},#{pass})")
	public int insert(User user);
	
}
