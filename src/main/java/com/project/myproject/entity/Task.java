package com.project.myproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.myproject.converter.GeoFilterValueConverter;
import com.project.myproject.enums.EGeoFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Length(min = 4, max = 56)
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String descForApproval;


    private int notWorkOnDaysOfWeek;

    // 0 - doesn't use geo, 1 - show only in this country, 2 - it must not show in this countries
    @Convert(converter = GeoFilterValueConverter.class)
    private EGeoFilter geoFilter;

    @Positive
    private int workTime;

    @Column(name="\"repeat\"")
    private boolean repeat;
    private int repeatTime;
    private boolean repeatBeforeCheck;

    private boolean enabled;


    @Column(scale=4)
    private BigDecimal balance;

    @Positive
    @NotNull
    @Column(precision=10, scale=4)
    private BigDecimal price;

    @Positive
    @NotNull
    @Column(precision=10, scale=4)
    private BigDecimal priceWithCommission;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_urls",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id"))
    private List<URL> urls = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_countries",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<Country> countries = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "status_id")
    private ModerationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany(mappedBy = "favoriteTasks", fetch = FetchType.LAZY)
    private List<User> favorites = new LinkedList<>();

    @ManyToMany(mappedBy = "hiddenTasks", fetch = FetchType.LAZY)
    private List<User> hidden = new LinkedList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<Report> reports = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
