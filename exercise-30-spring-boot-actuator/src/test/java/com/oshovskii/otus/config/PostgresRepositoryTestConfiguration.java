package com.oshovskii.otus.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@SpringBootConfiguration
@EnableAutoConfiguration
@ActiveProfiles(value = {"integration"})
public class PostgresRepositoryTestConfiguration {

    @Bean(initMethod = "start")
    public PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));
    }

    @Bean
    public DataSource dataSource() {
        final PostgreSQLContainer<?> postgresContainer = postgresContainer();

        final HikariConfig config = new HikariConfig();
        config.setDriverClassName(postgresContainer.getDriverClassName());
        config.setJdbcUrl(postgresContainer.getJdbcUrl());
        config.setUsername(postgresContainer.getUsername());
        config.setPassword(postgresContainer.getPassword());

        return new HikariDataSource(config);
    }
}
