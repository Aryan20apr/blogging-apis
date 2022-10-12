package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import com.aryan.blogging.bloggingapis.payload.CategoryDTO;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    
    CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer id);

    void deleteCategory(Integer id);

    CategoryDTO getCategory(Integer id);

    List<CategoryDTO> getAllCategory();


}


