package cn.jee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Movie> movies = new ArrayList<>();
}