package com.project.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author armdev
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
public class Person {

    private String id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String businessName;
    private String birthDate;

}
