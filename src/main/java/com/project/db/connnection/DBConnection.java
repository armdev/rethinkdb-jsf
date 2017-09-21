package com.project.db.connnection;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.DbCreate;
import com.rethinkdb.gen.ast.Table;
import com.rethinkdb.net.Connection;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Named(value = "databaseConnection")
@ApplicationScoped
@AllArgsConstructor
@NoArgsConstructor
public class DBConnection implements Serializable {

    private static final long serialVersionUID = 8365126215315864419L;

    @Getter
    private final RethinkDB databaseAccess = RethinkDB.r;
    @Getter
    private Connection connection;

    private final String databaseUrl = "localhost";
    private final Integer databasePort = 28015;

    @PostConstruct
    public void init() {

        connection = databaseAccess.connection().hostname(databaseUrl).port(databasePort).connect();

        if (!connection.isOpen()) {
            System.out.println("Could not open connection to " + databaseUrl + " " + databasePort);
            return;
        }

        List<String> databasesList = databaseAccess.dbList().run(connection);

        if (!databasesList.contains("socnet")) {
            DbCreate db = databaseAccess.dbCreate("socnet").run(connection);
            System.out.println("Created database socnet");
        } else {
            System.out.println("Database socnet exist, no need create new one");
        }

        List<String> tables = databaseAccess.db("socnet").tableList().run(connection);

        if (!tables.contains("usermessages")) {
            
            databaseAccess.db("socnet").tableCreate("usermessages").run(connection);
            
        } else {
            System.out.println("Table usermessages already exist");
        }

    }

    public Table getUserMessagesTable() {
        return databaseAccess.db("socnet").table("usermessages");
    }

}
