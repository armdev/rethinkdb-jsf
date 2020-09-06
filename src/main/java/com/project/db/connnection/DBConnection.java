package com.project.db.connnection;

import com.github.javafaker.Faker;
import com.project.model.Person;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.DbCreate;
import com.rethinkdb.gen.ast.Table;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

    private final String databaseUrl = "127.0.0.1";
    private final Integer databasePort = 28015;
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
        try {
            System.out.println("RUNNNN");

            connection = databaseAccess.connection().hostname(databaseUrl).port(databasePort).connect();

            if (!connection.isOpen()) {
                System.out.println("Could not open  connection to " + databaseUrl + " " + databasePort);
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

            if (!tables.contains("person")) {

                databaseAccess.db("socnet").tableCreate("person").run(connection);

            } else {
                System.out.println("Table person already exist");
            }
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new GenerateData(), 0, 15, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();

        }
       

        //generateDataAndSaveToDatabase();
       // generateDataAndSaveToDatabase1() ;
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
    }

    public boolean generateDataAndSaveToDatabase() {
       Faker faker = new Faker();
        Person person = new Person();
        for (int i = 0; i < 10; i++) {
            person = new Person();
            person.setAddress(faker.address().streetAddress());
            person.setBusinessName(faker.business().creditCardNumber());
            person.setCity(faker.address().cityName());
            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
            person.setBirthDate(faker.date().toString());

            getPersonTable().insert(databaseAccess.array(
                    databaseAccess.hashMap("firstname", person.getFirstName())
                            .with("lastname", person.getLastName())
                            .with("businessName", person.getBusinessName())
                            .with("address", person.getAddress())
                            .with("birthDate", person.getBirthDate())
                            .with("city", person.getCity()))
            ).run(connection);

        }

        return true;
    }

    public boolean generateDataAndSaveToDatabase1() {
        Faker faker = new Faker();
        Person person = new Person();
        for (int i = 0; i < 5; i++) {
            person = new Person();
            person.setAddress(faker.address().streetAddress());
            person.setBusinessName(faker.business().creditCardNumber());
            person.setCity(faker.address().cityName());
            person.setFirstName(faker.name().firstName());
            person.setLastName(faker.name().lastName());
           
            person.setBirthDate(faker.date().toString());
            getPersonTable().insert(databaseAccess.array(
                    databaseAccess.hashMap("firstname", person.getFirstName())
                            .with("lastname", person.getLastName())
                            .with("businessName", person.getBusinessName())
                            .with("address", person.getAddress())
                            .with("birthDate", person.getBirthDate())
                            .with("city", person.getCity()))
            ).run(connection);

        }

        return true;
    }

    private class GenerateData implements Runnable {

        @Override
        public void run() {
            System.out.println("RUNNNN RUN");
            generateDataAndSaveToDatabase1();
            generateDataAndSaveToDatabase();
        }
    }

    public Table getPersonTable() {
        return databaseAccess.db("socnet").table("person");
    }

    public Cursor getCursor() {
        final Cursor cursor = getPersonTable().run(connection);
        return cursor;
    }

}
