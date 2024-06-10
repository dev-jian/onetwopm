package me.devjian.onetwopm.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "project")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "\"key\"")
    private String key;

    @Column(name = "\"value\"")
    private String value;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
