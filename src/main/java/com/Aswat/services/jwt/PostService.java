package com.Aswat.services.jwt;

import com.Aswat.Dtos.PostDTO;
import com.Aswat.entity.Post;
import io.micrometer.observation.ObservationFilter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface PostService {
  PostDTO createPost(PostDTO postDTO) throws IOException;
  List<PostDTO> getAllPosts();
  PostDTO reapproveAndRepostPost(Long id);

  List<PostDTO> getPostsByCategory(Long categoryId);

  PostDTO markPostAsPosted(Long id);
  PostDTO getApprovedPost(Long id);
  PostDTO sendPost(Long id);
  List<PostDTO> getAllCategoriesByTitle(String title);
  PostDTO approvePost(Long id);
  List<PostDTO> getPostedPosts();
  List<PostDTO> getApprovedPostsByCategory(String category);


  Optional<Post> getPostById(Long id);

}
