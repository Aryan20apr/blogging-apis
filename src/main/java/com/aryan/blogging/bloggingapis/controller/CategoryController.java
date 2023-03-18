package com.aryan.blogging.bloggingapis.controller;

import java.util.List;

import jakarta.validation.Valid;

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


import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.CategoryDTO;
import com.aryan.blogging.bloggingapis.payload.SubscriptionDTO;
import com.aryan.blogging.bloggingapis.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO catdto = this.categoryService.createCategory(categoryDTO);
        ApiResponse<CategoryDTO> apiResponse=new ApiResponse<CategoryDTO>(catdto,"Category Created Successfully",true);
        return new ResponseEntity<ApiResponse<CategoryDTO>>(apiResponse, HttpStatus.CREATED);

    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id) {

        CategoryDTO updateCategory = this.categoryService.updateCategory(categoryDTO, id);
        ApiResponse<CategoryDTO> apiResponse=new ApiResponse<CategoryDTO>(updateCategory,"Category Updated Successfully",true);
        return new ResponseEntity<ApiResponse<CategoryDTO>>(apiResponse, HttpStatus.CREATED);
        //return new ResponseEntity<CategoryDTO>(updateCategory, HttpStatus.OK);

    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ApiResponse<Integer>> deleteCategory(@PathVariable Integer id) {

       this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse<Integer>>(new ApiResponse<Integer>(id,"Category Deleted Successfully", true), HttpStatus.OK);

    }

    @GetMapping("/findCategory/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategory(@PathVariable Integer id) {

        CategoryDTO category = this.categoryService.getCategory(id);
        ApiResponse<CategoryDTO> apiResponse=new ApiResponse<CategoryDTO>(category,"Category Obtained Successfully",true);
        return new ResponseEntity<ApiResponse<CategoryDTO>>(apiResponse, HttpStatus.CREATED);
        

    }

    @GetMapping("/newcategories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getUnsubscibedCategories(@RequestParam int id) {

        List<CategoryDTO> list = this.categoryService.getAllUnSubscribedCategory(id);
        ApiResponse<List<CategoryDTO>> apiResponse=new ApiResponse<List<CategoryDTO>>(list,"Category Obtained Successfully",true);
        return new ResponseEntity<ApiResponse<List<CategoryDTO>>>(apiResponse, HttpStatus.OK);

    }
    
    @GetMapping("/categoryList")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getAllCategory() {

        List<CategoryDTO> list = this.categoryService.getAllCategories();
        ApiResponse<List<CategoryDTO>> apiResponse=new ApiResponse<List<CategoryDTO>>(list,"Category Obtained Successfully",true);
        return new ResponseEntity<ApiResponse<List<CategoryDTO>>>(apiResponse, HttpStatus.OK);

    }
    
    @GetMapping("/usercategories")
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getUserCategory(@RequestParam int userid) {

        List<CategoryDTO> list = this.categoryService.getUserCategory(userid);
        ApiResponse<List<CategoryDTO>> apiResponse=new ApiResponse<List<CategoryDTO>>(list,"Category Obtained Successfully",true);
        return new ResponseEntity<ApiResponse<List<CategoryDTO>>>(apiResponse, HttpStatus.OK);

    }
    
    @PostMapping("/category/subscribe")
    public ResponseEntity<ApiResponse<List<Integer>>> subscribeCategories(@RequestBody SubscriptionDTO subscriptions)
    {
        boolean b=categoryService.subscribeCategories(subscriptions);
       
            ApiResponse<List<Integer>> response=new ApiResponse<List<Integer>>(subscriptions.getCatids(),"Subscription successfull",true);
            
        return ResponseEntity.ok(response);
    }
    @PutMapping("/category/unsubscribe")
    public ResponseEntity<ApiResponse<List<Integer>>> unsubscribeCategories(@RequestBody SubscriptionDTO subscriptions)
    {
        boolean b=categoryService.unsubscribeCategories(subscriptions);
       
            ApiResponse<List<Integer>> response=new ApiResponse<List<Integer>>(subscriptions.getCatids(),"Subscription removal successfull",true);
            
        return ResponseEntity.ok(response);
    }
}
