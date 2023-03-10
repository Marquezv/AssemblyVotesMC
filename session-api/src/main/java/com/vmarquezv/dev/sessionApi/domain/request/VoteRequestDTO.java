package com.vmarquezv.dev.sessionApi.domain.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmarquezv.dev.sessionApi.commons.status.VoteStatus;
import com.vmarquezv.dev.sessionApi.domain.entity.Vote;
import com.vmarquezv.dev.sessionApi.domain.entity.VoteId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestDTO {
	
	
	private Long session_id;
	private Long user_id;
	
	private String password;
	
	private VoteStatus vote_status;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private LocalDateTime voted_in = LocalDateTime.now();
	
	@JsonIgnore
	private VoteId voteId;
	
	public Vote build() {
		Vote vote = new Vote()
			.setVote_id(this.voteId)
			.setVote_in(this.voted_in);
		return vote;
	}
}
