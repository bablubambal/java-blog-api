package com.codewithdurgesh.blog.services.imp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.spi.DirStateFactory.Result;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.InitBinder;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.repositories.PostRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.services.PostService;

//important pagaglble import
import org.springframework.data.domain.Pageable;


import net.bytebuddy.asm.Advice.This;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Service
public class PostServiceImpl implements PostService {
	 
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
		
		 Post post =  this.modelMapper.map(postDto, Post.class);
		 post.setImageName("default.png");
		 post.setAddedDate(new Date());
		 post.setUser(user);
		 post.setCategory(category);
		 
		Post newPost = this.postRepo.save(post);
		 
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
	Post updatedPost =	this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
	Post post = 	this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
	this.postRepo.delete(post);
		

	}

	@Override
	//this is old just list return of the post now updating wrapper resoponse 
//	public List<PostDto> getAllPost(Integer pageNumber , Integer pageSize) {
//		to get The paginated Result 
//		get, page number and page Size 
//		int pageNumber = 2;
//		int pageSize = 5;
	public PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {
		//next we need to pass pagageable object to it
		
		//Request pagagble for pagination 
//		Pageable  p =  PageRequest.of(pageNumber, pageSize);
		
		
		// making sort obj
		Sort sort = null;
//		Sorting based on the Direction 
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
//		Making the if else case into ternary operator;
		Sort sortingSort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :  Sort.by(sortBy).descending();
		
		
		//request for pagianation and sort also updating it 
//				Pageable  p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()); 
		Pageable  p = PageRequest.of(pageNumber, pageSize, sortingSort); 
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post>  posts = pagePost.getContent();
		
		
		

		
//		List<Post> posts = this.postRepo.findAll();
	 List<PostDto> postDtos = 	posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	 PostResponse postResponse = new PostResponse();
	 postResponse.setContent(postDtos);
	 postResponse.setPageNumber(pagePost.getNumber());
	 postResponse.setPageSize(pagePost.getSize());
	 postResponse.setTotalElements(pagePost.getTotalElements());
	 postResponse.setTotalPages(pagePost.getTotalPages());
	 postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		
	List<Post> posts =	this.postRepo.findByCategory(cat);
	List<PostDto> postDtos =  posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		List<Post>  posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
	List<Post> posts = 	this.postRepo.searchByTitle("%"+keyword+"%");
	
	List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	public List<PostDto> searchPostsByTitle(String keyword) {
		List<Post> posts = 	this.postRepo.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
			return postDtos;
		}
	

}
