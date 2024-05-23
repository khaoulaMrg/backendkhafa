package com.Aswat.reposistories;

import com.Aswat.entity.Category;
import com.Aswat.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo  extends JpaRepository<Category, Long> {


    Category findByCategory(String categoryName);
}
