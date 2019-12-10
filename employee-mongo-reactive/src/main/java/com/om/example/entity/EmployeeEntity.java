package com.om.example.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EmployeeEntity {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer salary;
    private List<AddressEntity> addressList;
}
