package com.waracle.cakemgr.apiserver.persistance;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="Cakes")
public class CakeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private int id;

    private String title;
    private String description;

    @Column(name="imageurl")
    private String imageUrl;

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getImageUrl() { return imageUrl; }}
