package com.Aswat.services.jwt;

import com.Aswat.Dtos.PostDTO;
import com.Aswat.entity.Category;
import com.Aswat.entity.Post;
import com.Aswat.entity.Type;
import com.Aswat.reposistories.CategoryRepo;
import com.Aswat.reposistories.PostRepository;
import com.Aswat.reposistories.TypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepo categoryRepo;


    private final TypeRepository typeRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepo categoryRepo, TypeRepository typeRepository){
        this.postRepository = postRepository;
        this.categoryRepo = categoryRepo;
        this.typeRepository = typeRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public PostDTO createPost(PostDTO postDTO) throws IOException {
        Post post = new Post();
        post.setName(postDTO.getName());
        post.setContent(postDTO.getContent());
        post.setPostedBy(postDTO.getPostedBy());
        post.setDate(postDTO.getDate());
        post.setImg(postDTO.getImg().getBytes());

        Type type = typeRepository.findById(postDTO.getTypeId()).orElseThrow();
        post.setType(type);

        Category category = categoryRepo.findById(postDTO.getCategoryId()).orElseThrow();
        post.setCategory(category);
        return postRepository.save(post).getDto();
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    PostDTO postDTO = post.getDto();
                    Type type = post.getType();
                    Category category = post.getCategory();

                    if (category != null) {
                        postDTO.setCategoryName(category.getCategory());
                    }
                    if (type != null) {
                        postDTO.setTypeName(type.getType());
                    }

                    return postDTO;
                })
                .collect(Collectors.toList());
    }

    private List<PostDTO> mapPostsToPostDTO(List<Post> posts) {
        return posts.stream().map(post -> {
            PostDTO postDTO = post.getDto();
            if (post.getCategory() != null) {
                postDTO.setCategoryId(post.getCategory().getId());
                postDTO.setCategoryName(post.getCategory().getCategory());
            }


            if (post.getType() != null) {
                postDTO.setTypeId(post.getType().getId());
                postDTO.setTypeName(post.getType().getType());
            }
            return postDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public PostDTO approvePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setApproved(true);
            postRepository.save(post);

            PostDTO postDTO = post.getDto();
            if (post.getCategory() != null) {
                postDTO.setCategoryId(post.getCategoryId());
                postDTO.setCategoryName(post.getCategory().getCategory());
            }

            if (post.getType() != null) {
                postDTO.setTypeId(post.getTypeId());
                postDTO.setTypeName(post.getType().getType());
            }
            return postDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<PostDTO> getPostedPosts() {
        List<Post> postedPosts = postRepository.findByPosted(true);
        return mapPostsToPostDTO(postedPosts);
    }



    @Override
    public List<PostDTO> getPostsByCategory(Long categoryId) {
        // Assuming you have a method to get posts by category ID
        List<Post> posts = postRepository.findByCategory_Id(categoryId);
        return mapPostsToPostDTO(posts);
    }

    @Override
    public PostDTO markPostAsPosted(Long id) {
        PostDTO approvedPostDTO = getApprovedPost(id);
        approvedPostDTO.setPosted(true);

        Post updatedPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        updatedPost.setPosted(true);
        postRepository.save(updatedPost);

        return approvedPostDTO;
    }

    @Override
    public PostDTO getApprovedPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.isApproved()) {
                PostDTO postDTO = post.getDto();
                if (post.getCategory() != null) {
                    postDTO.setCategoryId(post.getCategoryId());
                    postDTO.setCategoryName(post.getCategory().getCategory());
                }
                if (post.getType() != null) {
                    postDTO.setTypeId(post.getTypeId());
                    postDTO.setTypeName(post.getType().getType());
                }
                return postDTO;
            } else {
                throw new RuntimeException("The post is not approved");
            }
        } else {
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public PostDTO sendPost(Long id) {
        PostDTO approvedPostDTO = getApprovedPost(id);
        approvedPostDTO.setPosted(true);

        Post updatedPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
        updatedPost.setPosted(true);
        postRepository.save(updatedPost);

        return approvedPostDTO;
    }

    @Override
    public List<PostDTO> getAllCategoriesByTitle(String title) {
        // Implement if necessary
        return null;
    }

    @Override
    public PostDTO reapproveAndRepostPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.isApproved()) {
                post.setPosted(false);
                postRepository.save(post);

                PostDTO postDTO = post.getDto();
                if (post.getCategory() != null) {
                    postDTO.setCategoryId(post.getCategoryId());
                    postDTO.setCategoryName(post.getCategory().getCategory());
                }

                if (post.getType() != null) {
                    postDTO.setTypeId(post.getTypeId());
                    postDTO.setTypeName(post.getType().getType());
                }
                return postDTO;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public List<PostDTO> getApprovedPostsByCategory(String categoryName) {
        Category category = categoryRepo.findByCategory(categoryName);
        if (category == null) {
            // Handle the case where the category is not found, for example by throwing an exception
            throw new IllegalArgumentException("Category not found: " + categoryName);
        }
        List<Post> posts = postRepository.findByCategoryAndApprovedIsTrue(category);
        return mapPostsToPostDTO(posts);
    }



    public Optional<Post> getPostById(Long id) {
        if (id == null) {
            logger.error("Post ID is null");
            throw new IllegalArgumentException("Post ID cannot be null");
        }

        try {
            Optional<Post> post = postRepository.findById(id);
            if (post.isPresent()) {
                logger.info("Post with ID {} found", id);
            } else {
                logger.warn("Post with ID {} not found", id);
            }
            return post;
        } catch (Exception e) {
            logger.error("Error retrieving post with ID {}", id, e);
            throw new RuntimeException("Error retrieving post with ID " + id, e);
        }
    }



}
