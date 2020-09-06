package com.project.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armdev
 */
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@NoArgsConstructor
@Data
public class Person implements Serializable{

    private String id;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String businessName;
    private String birthDate;

}
