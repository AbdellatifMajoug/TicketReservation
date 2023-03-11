
package org.sid.springmvc.dao;

import org.sid.springmvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("select u from User u where u.username=:x ")
	public User findUserByUsername(@Param("x")String username);

}

