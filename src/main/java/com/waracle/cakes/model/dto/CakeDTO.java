package com.waracle.cakes.model.dto;


import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@EqualsAndHashCode
@ToString
public class CakeDTO implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -9089461472324909474L;
	
	
    private String title;
    private String desc;
    private String image;
}
