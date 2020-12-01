package com.waracle.cakemgr;

import java.io.Serializable;

import javax.persistence.*;

@Entity
//@Table(name = "CAKE", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
@Table(name = "CAKE")
public class Cake implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "TITLE", unique = false, nullable = false, length = 100)//todo: changed this from being unique temporarily
    private String title;

    @Column(name = "DESC", unique = false, nullable = false, length = 100)
    private String desc;

    @Column(name = "IMAGE", unique = false, nullable = false, length = 300)
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

}