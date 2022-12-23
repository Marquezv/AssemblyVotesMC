package com.vmarquezv.dev.sessionApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmarquezv.dev.sessionApi.domain.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}

