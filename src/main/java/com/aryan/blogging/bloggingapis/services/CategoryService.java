package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import com.aryan.blogging.bloggingapis.payload.CategoryDTO;
import com.aryan.blogging.bloggingapis.payload.SubscriptionDTO;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    
    CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer id);

    void deleteCategory(Integer id);

    CategoryDTO getCategory(Integer id);

    List<CategoryDTO> getAllCategory();
    
    List<CategoryDTO> getUserCategory(int userid);
    
    
    
    boolean subscribeCategories(SubscriptionDTO subscriptionDTO);
    
    boolean unsubscribeCategories(SubscriptionDTO subscriptionDTO);


}


