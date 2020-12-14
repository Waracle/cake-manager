package com.waracle.cakemgr.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "CAKE")
public class Cake implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "TITLE", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "DESC", nullable = false, length = 100)
    private String desc;

    @Column(name = "IMAGE", nullable = false, length = 300)
    private String image;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cake cake = (Cake) o;
        return Objects.equals(title, cake.title) &&
                Objects.equals(desc, cake.desc) &&
                Objects.equals(image, cake.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, desc, image);
    }
}