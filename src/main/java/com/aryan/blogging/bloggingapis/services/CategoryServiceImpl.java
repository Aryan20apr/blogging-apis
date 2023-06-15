package com.aryan.blogging.bloggingapis.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.Category;
import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.payload.CategoryDTO;
import com.aryan.blogging.bloggingapis.payload.SubscriptionDTO;
import com.aryan.blogging.bloggingapis.repositories.CategoryRepo;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;




@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private FirebaseFcmServiceImpl firebaseFcmService;

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
    public List<CategoryDTO> getAllUnSubscribedCategory(int id) {
        List<Category> categories=this.categoryRepo.getUnsubscribedCategories(id);
        
       return categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
    }
    
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories=this.categoryRepo.findAll();
        
       return categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
    }
    
    @Override
    public List<CategoryDTO> getUserCategory(int userid) {
        
        User user=userRepo.findById(userid).get();
        
        List<Category> categories=new ArrayList<>(user.getCategories());
        
       return categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
    }
    
    @Override
    public boolean subscribeCategories(SubscriptionDTO subscriptions) {
       
        User user=userRepo.findById(subscriptions.getUserid()).get();
        
        for(int id:subscriptions.getCatids())
        {
            Category category=categoryRepo.findById(id).get();
            
            user.getCategories().add(category);
            firebaseFcmService.subscribeNotification(subscriptions.getFcmtoken(), category.getCategoryId().toString());
        }
        userRepo.save(user);
        return true;
    }
    @Override
    public boolean unsubscribeCategories(SubscriptionDTO subscriptions) {
       
        User user=userRepo.findById(subscriptions.getUserid()).get();
        Set<Category> oldCategories= user.getCategories();
       // user.getCategories().removeAll(subscriptions.getCatids());
        //Set<Category> newCategories=new HashSet<>();
        
        for(int id:subscriptions.getCatids())
        {
           for(Category c:oldCategories)
           {
               System.out.println("Id="+id);
               if(id==c.getCategoryId())
               {
                   System.out.println("### Id="+c.getCategoryId());
                      oldCategories.remove(c);
                      firebaseFcmService.unsubscribeNotification(subscriptions.getFcmtoken(), c.getCategoryId().toString());
                   break;}
           }
           // System.out.println("b="+b);
        }
        user.setCategories(oldCategories);
        userRepo.save(user);
        return true;
    }

    
    
    
}
