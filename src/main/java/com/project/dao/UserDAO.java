package com.project.dao;

import com.project.db.connnection.*;
import com.project.model.UserMessage;
import com.rethinkdb.net.Cursor;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named(value = "userDAO")
@SessionScoped
@AllArgsConstructor
@NoArgsConstructor
public class UserDAO implements Serializable {

    private static final long serialVersionUID = 8365126215315864419L;

    @Inject
    //@ManagedProperty("#{databaseConnection}")
    private DBConnection dbConnection;

    @Setter
    @Getter
    private UserMessage userMessage;

    @PostConstruct
    public void init() {

    }

    public void doSave() {
        UserMessage msgs = new UserMessage();
        msgs.setMessage("aloha");
        msgs.setFrom("John Lee");
        msgs.setTime(String.valueOf(System.currentTimeMillis()));
        this.saveMessages(msgs);
    }

    public String saveMessages(UserMessage userMessage) {
        dbConnection.getUserMessagesTable().insert(dbConnection.getDatabaseAccess().array(
                dbConnection.getDatabaseAccess().hashMap("from", userMessage.getFrom())
                        .with("message", userMessage.getMessage()).with("time", userMessage.getTime())
                        .with("details",
                                dbConnection.getDatabaseAccess().array(
                                        dbConnection.getDatabaseAccess()
                                .hashMap("firsttoken", "58412585485###"), 
                                        
                                dbConnection.getDatabaseAccess().hashMap("secondtoken", "852###4854")))
        )).run(dbConnection.getConnection());

        return null;
    }

    public void printDocs() {

        Cursor cursor = dbConnection.getUserMessagesTable().run(dbConnection.getConnection());

        for (Object doc : cursor) {
            Map docMap = (Map) doc;
            System.out.println("from: " + docMap.get("from"));
            System.out.println("message: " + docMap.get("message"));
            System.out.println("time: " + docMap.get("time"));

            for (Object actorDoc : ((List) docMap.get("details"))) {
                Map actorDocMap = (Map) actorDoc;
                System.out.println("details: ");
                System.out.println("  firsttoken: " + actorDocMap.get("firsttoken"));
                System.out.println("  secondtoken: " + actorDocMap.get("secondtoken"));
            }

        }
    }

}
