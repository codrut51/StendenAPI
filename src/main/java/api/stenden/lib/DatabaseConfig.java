package api.stenden.lib;

import com.mysql.cj.Session;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement // Required for Hibernate
@EnableJpaRepositories("api.steden.data")
@NoArgsConstructor
public class DatabaseConfig {

    private final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/rapidml";
    private final String USER_NAME = "root";
    private final String PASS_WORD = "";


    @Bean
    public DataSource dataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.addConnectionProperty("driverClassName", DRIVER_CLASS_NAME);
        dataSource.addConnectionProperty("url",URL);
        dataSource.addConnectionProperty("username", USER_NAME);
        dataSource.addConnectionProperty("password", PASS_WORD);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource)
    {
        System.out.println("Fuck off!!!!");
        System.out.println(dataSource);
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan("api.stenden.data.model");
        Properties props = new Properties();
        props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        sfb.setHibernateProperties(props);

        System.out.println(sfb);
        return sfb;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    /**
     * Whhn just using JPA, you could also use this transaction manager.
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return jpaTransactionManager;
    }
    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        System.out.println(dataSource.toString());
        System.out.println(jpaVendorAdapter.toString());
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("api.stenden.data.model");
        return entityManagerFactoryBean;
    }


//    @Bean
//    public DataSource dataSource()
//    {
//        MysqlDataSource dataSource = null;
//        try{
//            dataSource = new MysqlDataSource();
//            dataSource.setUrl(URL);
//            dataSource.setUser(USER_NAME);
//            dataSource.setPassword(PASS_WORD);
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return dataSource;
//    }
//
//
//    @Bean
//    public SessionFactory sessionFactory(DataSource dataSource)
//    {
//        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
//        sessionBuilder.scanPackages("api.stenden.data.model");
//        return sessionBuilder.buildSessionFactory();
//    }
//
//
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
//    {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
//        return transactionManager;
//    }
//
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final  DataSource dataSource)
//    {
//        final DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        return initializer;
//    }
//
//    @Bean
//    public BeanPostProcessor persistenceTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
//        adapter.setDatabase(Database.MYSQL);
//        return adapter;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource);
//        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
//        entityManagerFactoryBean.setPackagesToScan("api.stenden.data.model");
//        return entityManagerFactoryBean;
//    }




////    @Bean
////    public SessionFactory sessionFactory() {
////        try{
////            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
////            StandardServiceRegistry registry;
////            Map<String, String> settings  = new HashMap<>();
////            settings.put(Environment.DRIVER, "java.sql.Driver");
////            settings.put(Environment.URL, "jdbc:mysql://localhost/test");
////            settings.put(Environment.USER, "root");
////            settings.put(Environment.PASS,"");
////            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
////            registryBuilder.applySettings(settings);
////            registry = registryBuilder.build();
////
////            MetadataSources sources = new MetadataSources(registry);
////
////            // Create Metadata
////            Metadata metadata = sources.getMetadataBuilder().build();
////
////            return metadata.getSessionFactoryBuilder().build();
////        }catch(Exception e){
////            e.printStackTrace();
////            return null;
////        }
////    }
//
//    @Bean
//    public DataSource dataSource() {
////        MysqlDataSource mysql = null;
////        try{
////            mysql = new MysqlDataSource();
////            mysql.setURL("jdbc:mysql://localhost/rapidml");
////            mysql.setUser("root");
////            mysql.setPassword("");
////        }catch (Exception e){
////            e.printStackTrace();
////        }
////        return mysql;
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost/rapidml");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//
//        return dataSource;
//    }
//
//    @Bean
//    private Properties hibProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
//        properties.put("hibernate.show_sql", true);
//        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        return properties;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean sessionFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//
//        em.setDataSource(dataSource);
//        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        em.setPackagesToScan("api.stenden.data.model");
//
//        em.setJpaProperties(hibProperties());
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(hibProperties());
//        return em;
//    }
//
////
////    @Bean
////    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
////        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
////        sfb.setDataSource(dataSource);
////        sfb.setPackagesToScan("api.stenden.data.model");
////        Properties props = new Properties();
////        props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
////        sfb.setHibernateProperties(props);
////        return sfb;
////    }
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory sessionFactory) {
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////        transactionManager.setSessionFactory(sessionFactory);
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(sessionFactory);
//        return transactionManager;
//    }
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
}
