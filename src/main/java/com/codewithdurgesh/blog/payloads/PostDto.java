package com.codewithdurgesh.blog.payloads;

import java.util.Date;



import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

//	private String title;
//	private String content;
////	private String imageNameString = "default.png";
	
	private Integer postId;
	private String title;
	
	
	private String content;
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	
	private UserDto user;  
	
	
}
