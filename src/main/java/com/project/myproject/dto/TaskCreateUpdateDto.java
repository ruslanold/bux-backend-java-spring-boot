package com.project.myproject.dto;

import com.project.myproject.enums.ECountry;
import com.project.myproject.enums.EGeoFilter;
import com.project.myproject.enums.EWeek;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskCreateUpdateDto {
    @NotBlank
    private String title;
    @NotEmpty
    private List<@URL String> urls;
    @NotBlank
    private String description;
    @NotBlank
    private String descForApproval;
    private List<EWeek> notWorkOnDaysOfWeek;
    private int geoFilter;
    private List<ECountry> countries;
    @Min(value = 600) // 10 minutes in seconds
    @Max(value = 2419200) // 1 month in seconds
    private int workTime;
    private boolean repeat;
    private int repeatTime;
    private boolean repeatBeforeCheck;
    @Positive
    @Digits(integer = 10, fraction = 8)
    private BigDecimal price;
    @Positive
    private int categoryId;
}
