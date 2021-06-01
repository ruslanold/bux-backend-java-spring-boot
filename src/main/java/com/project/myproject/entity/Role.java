package com.project.myproject.entity;

import com.project.myproject.converter.RoleNameConverter;
import com.project.myproject.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   @Convert(converter = RoleNameConverter.class)
   private ERole name;
   @ManyToMany(fetch = FetchType.LAZY)
   @JoinTable(name = "role_authorities",
           joinColumns = @JoinColumn(name = "role_id"),
           inverseJoinColumns = @JoinColumn(name = "authority_id"))
   private List<Authority> authorities = new LinkedList<>();


}
