package com.example.employee_management_system.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotNull(message = "Cannot be null.")
    @Min(value = 2,message = "Length must be more than 2 characters. ")
    private int id;
    @NotEmpty(message = "Cannot be null. ")
    @Size(min = 4,message = "Length must be more than 4 characters.")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "- Must contain only characters (no numbers). ")
    private String name;
    @Email
    private String email;
    @NotEmpty(message = "Phone number cannot be empty.")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be exactly 10 digits long.")
    private String phoneNumber;
    @NotNull(message = "Cannot be null.")
    @Min(value = 25 , message = "Must be more than 25.")
    private int age;
    @NotEmpty(message = "Cannot be null. ")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator' only.")
    private String position;
    @NotNull(message = "onLeave cannot be null.")
    private boolean onLeave = false;
    @NotNull(message = "Hire date cannot be null.")
    @PastOrPresent(message = "Hire date must be in the present or the past.")
    private LocalDate hireDate;
    @NotNull(message = "Annual leave cannot be null.")
    @Min(value = 0, message = "Annual leave must be zero or greater.")
    private int annualLeave;
}
