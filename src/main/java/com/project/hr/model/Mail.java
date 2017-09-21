package com.project.hr.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public String from;
    public String subject;
    public String body;
    public Date date;
    
   
}
