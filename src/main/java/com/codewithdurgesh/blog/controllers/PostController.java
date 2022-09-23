package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.services.PostService;

import net.bytebuddy.asm.Advice.This;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			){
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	//  Get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
	List<PostDto> postDtos = 	this.postService.getPostsByUser(userId);
	return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
//  Get by category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
	List<PostDto> postDtos = 	this.postService.getPostsByCategory(categoryId);
	return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	
	//get all posts
/***	
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize
			
			){

		      List<PostDto> postDtos = this.postService.getAllPost( pageNumber ,pageSize);
		
		
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	****/
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			){
	
		   PostResponse allPostResponse = this.postService.getAllPost( pageNumber ,pageSize,sortBy,sortDir);
		
		
		return new ResponseEntity<PostResponse>(allPostResponse,HttpStatus.OK);
	}
	// get single post
	
	@GetMapping("/posts/{post_id}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer post_id){
		PostDto postsingle = this.postService.getPostById(post_id);
		return new ResponseEntity<PostDto>(postsingle,HttpStatus.OK);
	}
	
	/// delete single post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post Deleted ",true,"success");
		
	}
	
	//update post by Id
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
	PostDto updatePostDto = 	this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
		
	}
	

	
	//serach
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		List<PostDto> resultDtos = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(resultDtos,HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
}
