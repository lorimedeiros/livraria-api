package io.github.lorimedeiros.livraria_api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driverClassName}")
    String driver;

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1);
        config.setPoolName("livreria-db-pool");
        config.setMaxLifetime(600000);
        config.setConnectionTimeout(100000);
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }

}
