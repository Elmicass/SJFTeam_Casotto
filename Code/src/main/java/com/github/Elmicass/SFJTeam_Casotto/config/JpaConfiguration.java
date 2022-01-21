package com.github.Elmicass.SFJTeam_Casotto.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.github.Elmicass.SFJTeam_Casotto.repository")
@EnableTransactionManagement
public class JpaConfiguration {

    @Bean
    public DataSource dataSource()  {
        DriverManagerDataSource  dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/DATABASENAME?createDatabaseIfNotExist=true");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(dataSource());
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter());
        lcemfb.setPackagesToScan("com.github.Elmicass.SFJTeam_Casotto.model");
        lcemfb.setJpaProperties(additionalProperties());
        return lcemfb;
    }

    Properties additionalProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
        return jpaProperties;
    }
    
}
