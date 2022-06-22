package com.raghav.assignment2student.model;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
    private Integer studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String destination;
    private String location;
    private Boolean isActive;
    private Timestamp lastModifiedOn;
    private Integer lastModifiedBy;
}
