package com.waracle.cakemgr.dao;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@DynamicUpdate
@Table(name = "cake", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class CakeEntity implements Serializable {

  private static final long serialVersionUID = -2417760290457013668L;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @Column(name = "desc", nullable = false)
  private String desc;

  @Column(name = "image", nullable = false)
  private String image;
}
