package com.vmarquezv.dev.userApi.resource;

import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vmarquezv.dev.userApi.domain.entity.Survey;
import com.vmarquezv.dev.userApi.domain.request.SurveyRequestDTO;
import com.vmarquezv.dev.userApi.domain.response.SurveyResponseDTO;
import com.vmarquezv.dev.userApi.service.SurveyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/surveys")
@RequiredArgsConstructor
public class SurveyResource {
	
	private static final String ID = "/{id}";
	
	@Autowired
	private SurveyService service;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@PostMapping
	public ResponseEntity<SurveyResponseDTO> insert(@RequestBody SurveyRequestDTO surveyRequestDTO) throws Exception {
		
		Survey res = service.insert(surveyRequestDTO);
		String routingKey = "user.v1.survey-created";
		Message message = new Message(res.toString().getBytes());
		rabbitTemplate.send(routingKey, message);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<CollectionModel<SurveyResponseDTO>> findAll() {
		
		List<SurveyResponseDTO> surveyResponseDTOList = service.findAll().stream()
				.map(survey -> service.getSurveyResponse(survey.getSurvey_id()))
				.map(survey -> addLink(survey)).toList();
		
		return ResponseEntity.ok().body(toCollectionModelList(surveyResponseDTOList));
	}
	
	@GetMapping(value = ID)
	public ResponseEntity<SurveyResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(addLink(service.getSurveyResponse(id)));
	}

	private SurveyResponseDTO addLink(SurveyResponseDTO res) {
		res.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(SurveyResource.class)
						.findById(res.getSurvey_id())).withSelfRel());
		
		res.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(SurveyResource.class)
						.findAll()).withRel(IanaLinkRelations.COLLECTION));
		
		res.getUserResponse().add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(UserResource.class)
						.findById(res.getUser_id())).withRel("users"));
		
		return res;
	}
	
	public CollectionModel<SurveyResponseDTO> toCollectionModelList(List<SurveyResponseDTO> surveyResponseDTO) {
		return CollectionModel.of(surveyResponseDTO);
	}
}
