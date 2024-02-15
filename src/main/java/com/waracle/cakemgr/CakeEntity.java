package com.waracle.cakemgr;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "Employee", uniqueConstraints = {@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "EMAIL")})
public class CakeEntity implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer employeeId;

    @Column(name = "EMAIL", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "FIRST_NAME", unique = false, nullable = false, length = 100)
    private String description;

    @Column(name = "LAST_NAME", unique = false, nullable = false, length = 300)
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}