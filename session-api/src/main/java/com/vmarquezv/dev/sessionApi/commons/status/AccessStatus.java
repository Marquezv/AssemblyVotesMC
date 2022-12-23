package com.vmarquezv.dev.sessionApi.commons.status;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum AccessStatus {

	NONE("NONE"),
	PUBLIC("PUBLIC"),
	PRIVATE("PRIVATE");
	
	private String description;

	public String getDescription() {
	        return description;
	}
}
