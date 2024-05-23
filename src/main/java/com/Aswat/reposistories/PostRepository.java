package com.Aswat.reposistories;


import com.Aswat.entity.Category;
import com.Aswat.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByNameContaining(String title);
    //List<Category> findApprovedAndPostedCategories();
    List<Post> findByPosted(boolean posted);
    List<Post> findByApprovedTrueAndPostedTrue();


    List<Post> findByCategoryAndApprovedIsTrue(Category category);



    List<Post> findByCategory_Id(Long categoryId);


}
