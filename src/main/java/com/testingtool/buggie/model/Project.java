package com.testingtool.buggie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testingtool.buggie.jwt.model.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    @Id
    private String id;
    private String name;
    private String description;
    @ManyToMany
    @JsonIgnore
    private List<User> memberList;
    private String created_by;
    private String created_on;
    @ManyToMany
    @JsonIgnore
    private List<Member> members;
}
