package cn.jee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString

public class Movie {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @NotBlank(message = "观看时间不能为空")
  @Size(min = 10, max = 10)
  private String watchTime;

  @Min(1)
  private double price;

  @Size(min = 20)
  @Column(name = "review")
  private String comment;

  private String name;

  @ElementCollection
  @ToString.Exclude
  private List<String> images = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private User user;
}