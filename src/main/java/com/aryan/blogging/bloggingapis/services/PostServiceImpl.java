package com.aryan.blogging.bloggingapis.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.Category;
import com.aryan.blogging.bloggingapis.entities.Post;
import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.payload.PostDto;
import com.aryan.blogging.bloggingapis.payload.PostResponse;
import com.aryan.blogging.bloggingapis.repositories.CategoryRepo;
import com.aryan.blogging.bloggingapis.repositories.PostRepo;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private  PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;//For getting the user

    @Autowired
    private CategoryRepo categoryRepo;//For getting the category

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));

        Post post=this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=this.postRepo.save(post);

        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post newPost=this.postRepo.save(post);
        //post.setCategory(postDto.getCategory());
        return this.modelMapper.map(postDto, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        
        Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts=this.postRepo.findAll();

        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());        
        return postDtos;
    }

  

    @Override
    public PostDto getPostById(Integer postId) {
   Post post=postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category id", categoryId));
        List<Post> posts=this.postRepo.findByCategory(cat);
        List<PostDto> postsDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postsDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user id", userId));
        List<Post> posts=this.postRepo.findByUser(user);
        List<PostDto> postsDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postsDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map(post-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection) {
     
        Sort sort=null;
        if(sortDirection.equalsIgnoreCase("asc"))
        {
           sort= Sort.by(sortBy).ascending();
        }
        else
        sort=Sort.by(sortBy).descending();
        Pageable page=PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> pagePost=this.postRepo.findAll(page);
        List<Post> allPosts=pagePost.getContent();
        List<PostDto> postDtos=allPosts.stream().map((post)-> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        
        PostResponse postResponse=new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }
    
}
