package com.testingtool.buggie.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Team {
    @Id
    String id;
    String name;
    String createdBy;
}
