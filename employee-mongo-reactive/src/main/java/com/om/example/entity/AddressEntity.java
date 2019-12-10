package com.om.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddressEntity {
    private String addressType;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private String zip;

}
