package com.vmarquezv.dev.userApi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmarquezv.dev.userApi.domain.entity.Survey;
import com.vmarquezv.dev.userApi.domain.request.SurveyRequestDTO;
import com.vmarquezv.dev.userApi.domain.response.SurveyResponseDTO;
import com.vmarquezv.dev.userApi.exceptions.ObjectNotFoundException;
import com.vmarquezv.dev.userApi.repository.SurveyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SurveyService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SurveyRepository repository;
	
	public Survey insert(SurveyRequestDTO surveyReq) {
		
		surveyReq.setUser(userService.findById(surveyReq.getUser_id()));
		log.info("[ SURVEY|SERVICE ] -" + "- [ FUNCTION : INSERT ]");
		
		return repository.save(surveyReq.build());
	}
	
	public Survey findById(Long id) {
		return repository.findById(id)
				.orElseThrow(
						() -> new ObjectNotFoundException("SURVEY_ID - NOT_FOUND"));
	}
	
	public SurveyResponseDTO getSurveyResponse(Long id) {
		SurveyResponseDTO surveyRes = findById(id).toResponse();
		surveyRes.setUserResponse(userService.findById(surveyRes.getUser_id()).toResponse());
		return surveyRes;
	}
	
	public List<SurveyResponseDTO> findAll() {
		return repository.findAll().stream().map(survey -> survey.toResponse())
			.collect(Collectors.toList());
	}
}
