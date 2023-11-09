package com.protasevich.egor.learn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lessons")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Lesson name can't be blank")
    private String name;

    @ManyToMany(mappedBy = "lessons")
    private List<StudentEntity> students;

}