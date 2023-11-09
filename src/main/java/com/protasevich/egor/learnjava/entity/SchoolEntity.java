package com.protasevich.egor.learnjava.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "schools")
@NoArgsConstructor
@Getter
@Setter
public class SchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "School name can't be blank")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "school")
    private List<StudentEntity> students;

    public List<StudentEntity> getStudents() {
        if (students == null) {
            students = new ArrayList<>();
        }
        return students;
    }
}
