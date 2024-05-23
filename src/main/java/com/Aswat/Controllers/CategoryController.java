package com.Aswat.Controllers;


import com.Aswat.Dtos.CategoryDto;
import com.Aswat.Dtos.TypeDto;
import com.Aswat.entity.Category;
import com.Aswat.entity.Type;
import com.Aswat.reposistories.CategoryRepo;
import com.Aswat.services.jwt.CategoryService;
import com.Aswat.services.jwt.TypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("/api/customer")
public class CategoryController {
    private final CategoryService categoryService;
    private  final TypeService typeService;

    public CategoryController(CategoryService categoryService, TypeService typeService) {
        this.categoryService = categoryService;
        this.typeService = typeService;
    }

    @PostMapping("cat")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }


@GetMapping("cats")
    public ResponseEntity<List<Category>>getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
}

    @PostMapping("type")
    public ResponseEntity<Type> createType(@RequestBody TypeDto typeDto) {
        Type type = typeService.createType(typeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(type);
    }


    @GetMapping("types")
    public ResponseEntity<List<Type>>getAllTypes(){
        return ResponseEntity.ok(typeService.getAllTypes());
    }


}
