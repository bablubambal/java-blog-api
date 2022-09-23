package com.codewithdurgesh.blog.services.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repositories.RoleRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.services.UserService;
import com.codewithdurgesh.blog.exceptions.*;

@Service
public class UserServiceImp implements UserService {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	// roles repo:
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userdto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userdto);
		User savvedUser = this.userRepo.save(user);
		

		return this.usertoDto(savvedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 =  this.usertoDto(updatedUser);
		
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		return this.usertoDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
	  List<User> users = 	 this.userRepo.findAll();
	 List<UserDto>userDtos = users.stream().map(user -> this.usertoDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
	User user = 	this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
	this.userRepo.delete(user);
		

	}

	
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
		
	}
	
	public UserDto usertoDto(User user) {
		UserDto userdto = this.modelMapper.map(user,UserDto.class);
//		UserDto userDto = new UserDto();
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setAbout(user.getAbout());
//		userdto.setPassword(user.getPassword());
		
		return userdto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user =  this.modelMapper.map(userDto, User.class);
		
		
		//encoding the password of user with password encoder  //we encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//roles
		//getting default role:
		Role role  =  this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		System.out.println("the roles here is "+role.toString());
		user.getRoles().add(role);
		
		
		User newUser = this.userRepo.save(user);
		
		
		
		
		return this.modelMapper.map(newUser, UserDto.class);
	}
	
}
