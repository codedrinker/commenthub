package com.codedrinker.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by codedrinker on 23/07/2017.
 */
@Component
public class DBDataSource implements InitializingBean {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    private static DataSource dataSource;

    public DataSource getInstance() {
        if (dataSource != null) {
            return dataSource;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dbUrl == null || dbUrl.isEmpty()) {
            dataSource = new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            dataSource = new HikariDataSource(config);
        }
    }
}
