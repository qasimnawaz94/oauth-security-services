package com.security.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.security.services.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 *
	 * @param username
	 * @return @{@link User}
	 */
	User findOneByUsername(String username);
}
