package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	//delete
	void deletePost(Integer postId);
	
	
	//get all post 
//	List<PostDto> getAllPost();
	// get all post with pagination effect 
// OLD one now lets return new 	List<PostDto> getAllPost(Integer pageNumber , Integer pageSize);
	
	// updating the post result with a wrapper class post response;
	PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir);
	
	//get single post
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);

}
