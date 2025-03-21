package web_hibernate.config;

import jakarta.annotation.Resource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("web_hibernate.repository")
@EnableTransactionManagement
@ComponentScan("web_hibernate")
@PropertySource("classpath:db.properties")
public class DatabaseConfig {

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan(env.getRequiredProperty("db.entity_package"));
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(getHibernateProperties());
        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));

        dataSource.setInitialSize(Integer.parseInt(env.getRequiredProperty("dbcp.initialSize")));
        dataSource.setMinIdle(Integer.parseInt(env.getRequiredProperty("dbcp.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(env.getRequiredProperty("dbcp.maxIdle")));
        dataSource.setDurationBetweenEvictionRuns(Duration.ofMillis(
                Integer.parseInt(env.getRequiredProperty("dbcp.timeBetweenEvictionRunsMillis"))));
        dataSource.setMinEvictableIdle(Duration.ofMillis(
                Integer.parseInt(env.getRequiredProperty("dbcp.minEvictableIdleTimeMillis"))));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty("dbcp.testOnBorrow")));
        dataSource.setValidationQuery(env.getRequiredProperty("dbcp.validationQuery"));

        return dataSource;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            InputStream propertiesStream = DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("hibernate.properties");
            properties.load(propertiesStream);
            return properties;
        } catch (IOException io_exc) {
            throw new IllegalArgumentException("Can't load hibernate.properties", io_exc);
        }
    }
}
