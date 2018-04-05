package laojy.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="jpa")
@RequestMapping("/jpa")
public class TestController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/query")
	@ResponseBody
	public Object findAll() {
		return userDao.findAll();
	}
}
