package com.Aswat.Controllers;

import com.Aswat.Dtos.PostDTO;
import com.Aswat.entity.Post;
import com.Aswat.reposistories.PostRepository;
import com.Aswat.services.jwt.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/customer")
public class PostController {
    private final PostService postService;

    @Autowired
    private PostRepository postRepository;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/post")
    public ResponseEntity<PostDTO> createPost(@ModelAttribute PostDTO postDTO)throws IOException{
        PostDTO postDTO1 = postService.createPost(postDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(postDTO1);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        List<PostDTO> postDTOS = postService.getAllPosts();
        return ResponseEntity.ok(postDTOS);
    }

    @PutMapping("/post/{id}/approve")
    public ResponseEntity<PostDTO> approvePost(@PathVariable Long id) {
        PostDTO approvedPostDTO = postService.approvePost(id);
        return ResponseEntity.ok(approvedPostDTO);
    }

    // Contrôleur pour réapprouver et reposter un post
    @PutMapping("/post/{id}/reapprove-repost")
    public ResponseEntity<PostDTO> reapproveAndRepostPost(@PathVariable Long id) {
        try {
            // Réapprouver et reposter le post avec l'identifiant spécifié
            PostDTO reapprovedPostDTO = postService.reapproveAndRepostPost(id);
            if (reapprovedPostDTO != null) {
                return ResponseEntity.ok(reapprovedPostDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Contrôleur pour envoyer un post
    @PostMapping("/post/{id}/send")
    public ResponseEntity<PostDTO> sendPost(@PathVariable Long id) {
        try {
            // Récupérer le post approuvé avec l'identifiant spécifié
            PostDTO approvedPostDTO = postService.getApprovedPost(id);
            if (approvedPostDTO != null) {
                // Envoyer le post approuvé à une autre forme (ou à une autre API)
                // Vous pouvez implémenter la logique pour envoyer le post où vous en avez besoin
                return ResponseEntity.ok(approvedPostDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @CrossOrigin(origins = "http://localhost:3000/*")
    @GetMapping("/posts/approved-by-category")
    public ResponseEntity<List<PostDTO>> getApprovedPostsByCategory(@RequestParam String category) {
        List<PostDTO> approvedPosts = postService.getApprovedPostsByCategory(category);
        return ResponseEntity.ok(approvedPosts);
    }
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
