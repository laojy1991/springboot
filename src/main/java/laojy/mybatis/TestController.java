package laojy.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import laojy.dynamicDS.DS;
import laojy.dynamicDS.DataSourceType;
import laojy.entity.User;


@RestController(value="mybatis")
@RequestMapping("/mybatis")
public class TestController {

	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/query")
	@ResponseBody
	@DS(DataSourceType.SLAVE)
	public Object findAll() {
		return userMapper.findAll();
	}
	
	
	@RequestMapping(value="insert",method=RequestMethod.POST)
	@ResponseBody
	@DS
	public Object insert(@RequestBody User user) {
		return userMapper.insert(user);
	}
}
