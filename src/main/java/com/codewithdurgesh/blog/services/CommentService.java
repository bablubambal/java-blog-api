package com.codewithdurgesh.blog.services;

import com.codewithdurgesh.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto creaComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);

}
