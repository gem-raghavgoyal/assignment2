package com.raghav.assignment2student.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentProjectDetails {
    private Integer studentId;
    private String studentName;
    private String email;
    private String phoneNo;
    private String destination;
    private String location;
    List<Project> projects = new ArrayList<>();
}
