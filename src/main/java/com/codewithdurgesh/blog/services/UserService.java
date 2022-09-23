package com.codewithdurgesh.blog.services;


import com.codewithdurgesh.blog.payloads.UserDto;



import java.util.List;

public interface UserService {
	
	// register user
	UserDto registerNewUser(UserDto user);
	
	 UserDto createUser(UserDto user);
	 UserDto updateUser(UserDto user,Integer userId);
	 UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
	 

}
