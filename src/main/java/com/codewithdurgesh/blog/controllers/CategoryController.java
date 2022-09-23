package com.codewithdurgesh.blog.controllers;

import java.security.PublicKey;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	//category service
	@Autowired
	private CategoryService categoryService;
	
	
	//create
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
		 
	}
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
		
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.CREATED);
		 
	}
	//delete api
	@DeleteMapping("/{catId}")
	
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successful", false,"success"),HttpStatus.CREATED);
		 
	}
	
	//get 
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer catId){
		CategoryDto getCategory = this.categoryService.getCategory(catId);
		
		return new ResponseEntity<CategoryDto>(getCategory,HttpStatus.CREATED);
		 
	}
	
	//get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> getCategory = this.categoryService.getCategories();
		
		return new ResponseEntity<List<CategoryDto>>(getCategory,HttpStatus.CREATED);
		 
	}


}
