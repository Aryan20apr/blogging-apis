package com.aryan.blogging.bloggingapis.services;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.Category;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.payload.CategoryDTO;
import com.aryan.blogging.bloggingapis.repositories.CategoryRepo;




@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        
       Category category= this.modelMapper.map(categoryDTO, Category.class);
       Category addedCategory=this.categoryRepo.save(category); 
       return this.modelMapper.map(addedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id) {
        
        Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category"," Category Id ",id));
            category.setCategoryTitle((categoryDTO.getCategoryTitle()));
            category.setCategoryDescription((categoryDTO.getCategoryDescription()));
            
            Category updCategory=this.categoryRepo.save(category);

        return this.modelMapper.map(updCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer id) {
        
        Category category=this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", id));
        this.categoryRepo.delete(category);
       
    }

    @Override
    public CategoryDTO getCategory(Integer id) {
        Category category=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", id));
        
       
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories=this.categoryRepo.findAll();
        
       List<CategoryDTO> categoryDTOs= categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
       
        return categoryDTOs;
    }
    
}
