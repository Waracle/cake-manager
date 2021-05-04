package com.waracle.cakemgr.apiserver.persistance;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="Cakes")
public class CakeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private UUID id;

    private String title;
    private String description;

    @Column(name="imageurl")
    private String imageUrl;

}
