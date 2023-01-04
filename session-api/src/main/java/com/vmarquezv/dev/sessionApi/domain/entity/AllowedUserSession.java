package com.vmarquezv.dev.sessionApi.domain.entity;

import com.vmarquezv.dev.sessionApi.domain.response.AllowedUserSessionResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Table(name = "ALLOWEDUSERSESSION")
@ToString
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllowedUserSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "SESSION_ID")
    private Session session;
	
	@JoinColumn(name = "USER_ID")
	private Long user_id;
	
	public AllowedUserSessionResponseDTO toResponse() {
		AllowedUserSessionResponseDTO allowedUserSessionRes = new AllowedUserSessionResponseDTO()
				.setSession_id(this.session.getId())
				.setUser_id(this.user_id);
		
		return allowedUserSessionRes;
	}
	
}
