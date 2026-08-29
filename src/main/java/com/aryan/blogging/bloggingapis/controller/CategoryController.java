package com.aryan.blogging.bloggingapis.controller;

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


import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.CategoryDTO;
import com.aryan.blogging.bloggingapis.services.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO catdto = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<CategoryDTO>(catdto, HttpStatus.CREATED);

    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id) {

        CategoryDTO updateCategory = this.categoryService.updateCategory(categoryDTO, id);
        return new ResponseEntity<CategoryDTO>(updateCategory, HttpStatus.OK);

    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {

       this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);

    }

    @GetMapping("/findCategory/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer id) {

        CategoryDTO category = this.categoryService.getCategory(id);
        return new ResponseEntity<CategoryDTO>(category, HttpStatus.OK);

    }

    @GetMapping("/categoryList")
    public ResponseEntity<List<CategoryDTO>> getAllCategory() {

        List<CategoryDTO> list = this.categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDTO>>(list, HttpStatus.OK);

    }
}
