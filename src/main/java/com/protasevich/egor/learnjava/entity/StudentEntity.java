package com.protasevich.egor.learnjava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    @NotBlank(message = "First name can't be blank")
    private String firstname;

    @Column(name = "lastname")
    @NotBlank(message = "Last name can't be blank")
    private String lastname;

    @Column(name = "grade")
    @NotBlank(message = "Grade can't be blank")
    private int grade;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;

    @ManyToMany
    @JoinTable(
            name = "students_lessons",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private List<LessonEntity> lessons;

    public List<LessonEntity> getLessons() {
        if (lessons == null) {
            lessons = new ArrayList<>();
        }
        return lessons;
    }
}
