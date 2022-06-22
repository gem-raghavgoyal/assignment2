package com.raghav.assignment2student.model;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentProjectMapping {
    private Integer id;
    private Integer studentId;
    private Integer projectId;
    private Boolean isActive;
    private Timestamp lastModifiedOn;
    private Integer lastModifiedBy;
}
