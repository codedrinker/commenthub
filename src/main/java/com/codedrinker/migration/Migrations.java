package com.codedrinker.migration;

import org.flywaydb.core.Flyway;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by codedrinker on 29/07/2017.
 */
public class Migrations {
    public static void main(String[] args) throws Exception {
        Flyway flyway = new Flyway();
        URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
        flyway.setDataSource(System.getenv(dbUrl),
                System.getenv(username),
                System.getenv(password));
        flyway.migrate();
    }

    public void migrate() {
        try {
            Flyway flyway = new Flyway();
            URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
            flyway.setDataSource(System.getenv(dbUrl),
                    System.getenv(username),
                    System.getenv(password));
            flyway.repair();// repair migration data schema before migrating
            flyway.migrate();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
