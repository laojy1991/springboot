package laojy.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import laojy.entity.User;


public interface UserDao extends JpaRepository<User, User>{
}
