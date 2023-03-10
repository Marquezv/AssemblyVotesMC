package com.vmarquezv.dev.sessionApi.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.vmarquezv.dev.sessionApi.commons.status.AccessStatus;
import com.vmarquezv.dev.sessionApi.commons.status.SessionStatus;
import com.vmarquezv.dev.sessionApi.domain.response.SessionResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Table(name = "SESSIONS")
@ToString
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "SURVEY_ID")
    private Long survey_id;
	
	@JoinColumn(name = "USER_ID")
	private Long user_id;
	
	@Column(name = "STARTED_ON")
	private LocalDateTime started_on;
	
	@Column(name = "CLOSED_ON")
	private LocalDateTime closed_on;
	
	@Column(name = "CREATED_DATE")
	private LocalDateTime created_on;
	
	@ColumnDefault("0")
	@Column(name = "AMOUNT_VOTES", nullable = false)
	private Integer amount_votes;
	
	@ColumnDefault("0")
	@Column(name = "UP_VOTES", nullable = false)
	private Integer up_votes;
	
	@ColumnDefault("0")
	@Column(name = "DOWN_VOTES", nullable = false)
	private Integer down_votes;
	
	@Column(name = "ACCESS_STATUS")
	private AccessStatus access_status;
	
	@Column(name = "SESSION_STATUS")
	private SessionStatus session_status;
	
	public SessionResponseDTO toResponse() {
		SessionResponseDTO surveyRes = new SessionResponseDTO()
				.setSession_id(this.id)
				.setUser_id(this.user_id)
				.setSurvey_id(this.survey_id)
				.setStarted_on(this.started_on)
				.setClosed_on(this.closed_on)
				.setCreated_on(this.created_on)
				.setAmount_votes(this.amount_votes)
				.setUp_votes(this.up_votes)
				.setDown_votes(this.down_votes)
				.setAccess_status(this.access_status)
				.setSession_status(this.session_status);
		
		return surveyRes;
	}
	
}
