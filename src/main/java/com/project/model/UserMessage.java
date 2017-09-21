package com.project.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
public class UserMessage {

    private String id;
    private String message;
    private String from;
    private String time;   
    
}
