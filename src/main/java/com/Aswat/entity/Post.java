package com.Aswat.entity;

import com.Aswat.Dtos.PostDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column (length = 5000)
    private String content;



    private  String postedBy;


    private Date date;

    private boolean approved;


    private boolean posted;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name= "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
@JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name= "type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
private Type type;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @Column(name = "pic_path")
    private String picPath;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }




    // ... code précédent ...

    public PostDTO getDto(){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(id);
        postDTO.setName(name);
        postDTO.setContent(content);
        postDTO.setPostedBy(postedBy);
        postDTO.setDate(date);
        if (category != null) {
            postDTO.setCategoryId(category.getId());
            postDTO.setCategoryName(category.getCategory()); // Ajoutez cette ligne
        }
        if (type != null) {
            postDTO.setTypeId(type.getId());
            postDTO.setTypeName(type.getType()); // Ajoutez cette ligne
        }
        postDTO.setByteImg(img);
        postDTO.setApproved(approved);
        postDTO.setPosted(posted);
        return postDTO;
    }

// ... code suivant ...

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public Long getTypeId() {
        return type != null ? type.getId() : null;
    }


    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public byte[] getByteImg() {
        return new byte[0];
    }


}
