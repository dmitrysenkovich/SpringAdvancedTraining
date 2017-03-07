package com.epam.spring.core.app;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.epam.spring.core")
@PropertySource("classpath:/application.properties")
public class DataAccessConfig {
	
    private static final String PROP_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROP_NAME_DATABASE_URL = "db.url";
    private static final String PROP_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROP_NAME_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_HIBERNATE_HDM_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	 
    @Autowired 
    private Environment env;
	
	@Bean
	public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getRequiredProperty(PROP_NAME_DATABASE_DRIVER));
	    dataSource.setUrl(env.getRequiredProperty(PROP_NAME_DATABASE_URL));
	    dataSource.setUsername(env.getRequiredProperty(PROP_NAME_DATABASE_USERNAME));
	    dataSource.setPassword(env.getRequiredProperty(PROP_NAME_DATABASE_PASSWORD));
	    return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
	    return jdbcTemplate;
	}
	 
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());	        
		return entityManagerFactoryBean;
	 }

	@Bean
	public JpaTransactionManager transactionManager(@Autowired DataSource dataSource,
													@Autowired EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		jpaTransactionManager.setDataSource(dataSource);

		return jpaTransactionManager;
	}
	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
		properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
		properties.put(PROP_HIBERNATE_HDM_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HDM_AUTO));
		return properties;
	}
	
}
