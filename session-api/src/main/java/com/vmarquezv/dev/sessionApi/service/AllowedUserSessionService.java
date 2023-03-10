package com.vmarquezv.dev.sessionApi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmarquezv.dev.sessionApi.commons.status.AccessStatus;
import com.vmarquezv.dev.sessionApi.domain.entity.AllowedUserSession;
import com.vmarquezv.dev.sessionApi.domain.entity.Session;
import com.vmarquezv.dev.sessionApi.domain.response.AllowedUserSessionResponseDTO;
import com.vmarquezv.dev.sessionApi.exceptions.DataIntegratyViolationException;
import com.vmarquezv.dev.sessionApi.exceptions.ObjectNotFoundException;
import com.vmarquezv.dev.sessionApi.repository.AllowedUserSessionRepository;

@Service
public class AllowedUserSessionService {
	
	@Autowired
	AllowedUserSessionRepository repository;
	
	public List<AllowedUserSessionResponseDTO> findAllUserSession(Long session_id) {
		return repository.findBySession(session_id).stream()
				.map(allowedUS -> allowedUS.toResponse()).collect(Collectors.toList());
	}
	
	public AllowedUserSessionResponseDTO findBySessionUser(Long session_id, Long user_id) {
		return repository.findBySessionUser(session_id, user_id)
				.orElseThrow(
						() -> new ObjectNotFoundException("SESSION_ID || USER_ID - NOT_FOUND")).toResponse();
	}
	
	public void addUserSession(Session session, Long user_id) {
		AllowedUserSession allowedUserSession = new AllowedUserSession()
				.setSession(session)
				.setUser_id(user_id);
		repository.save(allowedUserSession);
	}

	public boolean userCanVoteSession(Long session_id, Long user_id, AccessStatus accessStatus) {
		Optional<AllowedUserSession> allowedUserSession = repository.findBySessionUser(session_id, user_id);
		if(allowedUserSession.isEmpty() && accessStatus.equals(AccessStatus.PRIVATE) ) {
			return false;
		}
		return true;
	}
	
	public void userRegisterCheck(Long session_id, Long user_id) {
		Optional<AllowedUserSession> allowedUserSession = repository.findBySessionUser(session_id, user_id);
		if(allowedUserSession.isPresent()) {
			throw new DataIntegratyViolationException("USER_ID - ALREADY_REGISTERED");
		}
	}
}
