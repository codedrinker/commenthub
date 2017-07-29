package com.codedrinker.migration;

import org.flywaydb.core.Flyway;

/**
 * Created by codedrinker on 29/07/2017.
 */
public class Migrations {
    public static void main(String[] args) throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"),
                System.getenv("JDBC_DATABASE_USERNAME"),
                System.getenv("JDBC_DATABASE_PASSWORD"));
        flyway.migrate();
    }

    public void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(System.getenv("JDBC_DATABASE_URL"),
                System.getenv("JDBC_DATABASE_USERNAME"),
                System.getenv("JDBC_DATABASE_PASSWORD"));
        flyway.repair();// repair migration data schema before migrating
        flyway.migrate();
    }
}
