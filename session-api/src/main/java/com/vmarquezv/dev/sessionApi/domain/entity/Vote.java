package com.vmarquezv.dev.sessionApi.domain.entity;

import java.time.LocalDateTime;

import com.vmarquezv.dev.sessionApi.domain.response.VoteResponseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Table(name = "VOTES")
@ToString
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

	@Id
	private VoteId vote_id;
	
	@Column(name = "VOTE_ON")
	private LocalDateTime vote_in;
	
	public VoteResponseDTO toResponse() {
		VoteResponseDTO voteRes = new VoteResponseDTO()
			.setVote_id(this.vote_id)
			.setVoted_in(this.vote_in);
		return voteRes;
	}

}
	
