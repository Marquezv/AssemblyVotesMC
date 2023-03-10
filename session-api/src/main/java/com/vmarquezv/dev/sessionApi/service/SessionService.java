package com.vmarquezv.dev.sessionApi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vmarquezv.dev.sessionApi.commons.status.AccessStatus;
import com.vmarquezv.dev.sessionApi.commons.status.SessionStatus;
import com.vmarquezv.dev.sessionApi.commons.status.VoteStatus;
import com.vmarquezv.dev.sessionApi.commons.util.CheckService;
import com.vmarquezv.dev.sessionApi.domain.entity.Session;
import com.vmarquezv.dev.sessionApi.domain.request.SessionRequestDTO;
import com.vmarquezv.dev.sessionApi.domain.response.SessionResponseDTO;
import com.vmarquezv.dev.sessionApi.exceptions.DataIntegratyViolationException;
import com.vmarquezv.dev.sessionApi.exceptions.ObjectNotFoundException;
import com.vmarquezv.dev.sessionApi.exceptions.StatusArgumentExceptionException;
import com.vmarquezv.dev.sessionApi.repository.SessionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

	private final AllowedUserSessionService allowedUserSessionService;
	
	private final CheckService checkService;
	
	
	private final SessionRepository repository;
	
	public Session insert(SessionRequestDTO sessionReq) {
		LocalDateTime date = LocalDateTime.now();
		if(sessionReq.getAccess_status() == null || sessionReq.getAccess_status() == AccessStatus.NONE ) {
			sessionReq.setAccess_status(AccessStatus.PUBLIC);
		}
		else if(sessionReq.getStarted_on().isAfter(sessionReq.getClosed_on()) ||
				sessionReq.getStarted_on().isBefore(date) ||
				sessionReq.getClosed_on().isBefore(sessionReq.getStarted_on()) ||
				sessionReq.getClosed_on().isBefore(date)) {
			log.error("[ SESSION|SERVICE ] -" + "- [ FUNCTION : INSERT ]");
			throw new DataIntegratyViolationException("SESSION - INVALID DATE");
		}
	
//		sessionReq.setUser(userService.findById(sessionReq.getUser_id()));
//		sessionReq.setSurvey(surveyService.findById(sessionReq.getSurvey_id()));
		sessionReq.setAmount_votes(0);
		sessionReq.setUp_votes(0);
		sessionReq.setDown_votes(0);
		sessionReq.setSession_status(SessionStatus.NONE);
		log.info("[ SESSION|SERVICE ] -" + "- [ FUNCTION : INSERT ]");
		return repository.save(sessionReq.build());
	}
	
	public SessionResponseDTO addUserSession(SessionRequestDTO sessionReq) {
		
		Session session = repository.findById(sessionReq.getSession_id())
				.orElseThrow(
						() -> new ObjectNotFoundException("SESSION_ID - NOT_FOUND"));
//		User user = userService.findById(sessionReq.getUser_id());
		
		if(!checkService.accessStatus(session.getAccess_status().ordinal())) {
			throw new DataIntegratyViolationException("SESSION_ID - NOT_PERMITED_ADD_USER");
		}
		else if(checkService.hourState(session.getStarted_on())) {
			throw new DataIntegratyViolationException("SESSION_ID - HAS_BEEN_STARTED");
		}
//		allowedUserSessionService.userRegisterCheck(session.getId(), user.getId());
//		allowedUserSessionService.addUserSession(session, user);

		log.info("[ SESSION|SERVICE ] -" +"- [ FUNCTION : ADDUSERSESSION ]"+ "- [ SESSION_ID : "+ session.getId() +" ]");
		return findById(sessionReq.getSession_id());
	}
	
	public List<SessionResponseDTO> findAll() {
		return 	repository.findAll().stream()
				.map(session -> session.toResponse())
				.map(session -> findById(session.getSession_id()))
				.collect(Collectors.toList());
	}
	
	public List<SessionResponseDTO> findAllUserCanVote(Long user_id){
//		userService.findById(user_id);
		return findAll().stream()
				.filter(session -> allowedUserSessionService.userCanVoteSession(session.getSession_id(), user_id, session.getAccess_status()))
				.toList();		
	}
	
	public List<SessionResponseDTO> findAllSurvey(Long survey_id){
		return findAll().stream()
				.filter(session -> session.getSurvey_id().equals(survey_id))
				.toList();		
	}
	
	public SessionResponseDTO findById(Long id) {
		SessionResponseDTO res = repository.findById(id)
				.orElseThrow(
						() -> new ObjectNotFoundException("SESSION_ID - NOT_FOUND")).toResponse();
		res.setAllowedUserSession(allowedUserSessionService.findAllUserSession(id));
//		res.setUserResponse(userService.findById(res.getUser_id()).toResponse());
		
		return res;
	}
	
	public void votingSession(VoteStatus voteStatus, Long session_id) {
		Session session = repository.findById(session_id).orElseThrow(
					() -> new ObjectNotFoundException("SESSION_ID - NOT_FOUND"));
		
		if(session.getSession_status().equals(SessionStatus.FINALIZED) || session.getSession_status().equals(SessionStatus.NONE)) {
			log.error("[ SESSION|SERVICE ] -" +"- [ FUNCTION : VOTINGSESSION ]"+ "- [ SESSION_ID : "+ session.getId() +" ]");
			throw new DataIntegratyViolationException("SESSION_ID - NOT_IN_PROGRESS");
		}
		
		Integer upVotes = session.getUp_votes();
		Integer downVotes = session.getDown_votes();
		Integer amountVotes = session.getAmount_votes();
		switch (voteStatus.ordinal()){
		case 1: {
			session.setUp_votes(upVotes + 1);
			session.setAmount_votes(amountVotes + 1);
			repository.save(session);
			log.info("[ SESSION|SERVICE ] -"  + "- [ SESSION_ID : "+ session.getId() +" ]"  +"- [ FUNCTION : VOTINGSESSION ]");
			break;
		}
		case 2: {
			session.setDown_votes(downVotes + 1);
			session.setAmount_votes(amountVotes + 1);
			repository.save(session);
			log.info("[ SESSION|SERVICE ] -"  + "- [ SESSION_ID : "+ session.getId() +" ]"  +"- [ FUNCTION : VOTINGSESSION ]");
			break;
		}
		default:
			throw new StatusArgumentExceptionException("Unexpected value: " + voteStatus);
		}
	}
	
}