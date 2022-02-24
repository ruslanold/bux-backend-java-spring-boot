package com.project.myproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private double exp;
    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate birthDate;
    @Email
    private String email;
    @CreationTimestamp
    @JoinColumn(name = "last_visit")
    private LocalDateTime lastVisit; ///?


    private String phone;
    private String gender;
    private String image;
    private double rating;

    @Column(scale = 4)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "referrer_id")
    private User referrer;

    @OneToMany(mappedBy = "referrer", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST } )
    private List<User> referrals = new LinkedList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks = new LinkedList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Report> reports = new LinkedList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities = new LinkedList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorites_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> favoriteTasks = new LinkedList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hidden_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Task> hiddenTasks = new LinkedList<>();

    @Override
    public String getUsername(){ return username; }

    @Override
    public String getPassword(){ return password; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.addAll(authorities.stream().map(a -> new SimpleGrantedAuthority(a.getName().getDbValue())).collect(Collectors.toList()));
        simpleGrantedAuthorities.addAll(roles.stream().map( r -> new SimpleGrantedAuthority(r.getName().getDbValue())).collect(Collectors.toList()));
        return simpleGrantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //@ManyToMany(fetch = FetchType.LAZY)
    //@JoinTable(name = "completed_tasks",
    //        joinColumns = @JoinColumn(name = "user_id"),
    //        inverseJoinColumns = @JoinColumn(name = "task_id"))
    //private List<Task> completedTasks = new LinkedList<>();


}
