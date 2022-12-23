package com.vmarquezv.dev.sessionApi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vmarquezv.dev.sessionApi.domain.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	
	@Query(value = "SELECT * FROM VOTES WHERE USER_ID = ?1 AND SESSION_ID = ?2 ",
			nativeQuery = true)
	Optional<Vote> findByUserSession(Long user_id, Long session_id);
	
}

