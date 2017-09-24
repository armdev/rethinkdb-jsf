package com.project.frontbeans;

import com.project.db.connnection.*;
import com.project.model.Person;
import com.rethinkdb.net.Cursor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Named(value = "personBean")
@ViewScoped
@AllArgsConstructor
@NoArgsConstructor
public class PersonBean implements Serializable {

    private static final long serialVersionUID = 8365126215315864419L;

    @Inject
    private DBConnection dbConnection;

    @PostConstruct
    public void init() {

    }

    public List<Person> getPersonList() {
        List<Person> personList = new ArrayList<>();
        try {
            Person person = new Person();
            Cursor cursor = dbConnection.getCursor();
            // Cursor cursor = dbConnection.getPersonTable().changes().run(dbConnection.getConnection());
            for (Object doc : cursor) {
                Map docMap = (Map) doc;
                person = new Person();
                person.setId((String) docMap.get("id"));
                person.setAddress((String) docMap.get("address"));
                person.setBusinessName((String) docMap.get("businessName"));
                person.setCity((String) docMap.get("city"));
                person.setFirstName((String) docMap.get("firstname"));
                person.setLastName((String) docMap.get("lastname"));
                person.setBirthDate((String) docMap.get("birthDate"));
                personList.add(0, person);
            }
        } catch (Exception e) {

        }

        return personList;
    }

}
