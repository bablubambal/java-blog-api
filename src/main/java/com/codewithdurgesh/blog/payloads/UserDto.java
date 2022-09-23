package com.codewithdurgesh.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4,message = "Username must be min of 4 characters")
	private String name;
	
	@NotEmpty
	@Email(message = "Email is invalid !")
	private String email;
	@NotEmpty(message = "Password Can't be empty")
	@Size(min=3,max = 10,message = "password must be min 3 char and max 10 chars")
	private String password;
	@NotNull @NotEmpty
	private String about;

}
