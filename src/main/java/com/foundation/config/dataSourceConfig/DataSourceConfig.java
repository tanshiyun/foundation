package com.foundation.config.dataSourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.foundation.component.TransactionManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @ClassName DataSourceConfig
 * @Description TODO
 * @Author tsy20
 * @Date 2020/1/17
 **/
@Configuration
public class DataSourceConfig {

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }


    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
                                               @Value("${mybatis.config-location}") String configLocation,
                                               @Value("${mybatis.type-aliases-package}") String typeAliasesPackage) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
        sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        return sessionFactoryBean.getObject();
    }


    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate (@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager (DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("foundationTransaction")
    public TransactionManager foundationTransaction(@Qualifier("transactionManager") PlatformTransactionManager transactionManager, @Value("${foundation.transaction.isolation}") String isolation, @Value("${foundation.transaction.propagation}")String propagation){
        TransactionManager foundationTransaction = new TransactionManager();
        foundationTransaction.setTransactionManager(transactionManager);
        foundationTransaction.setIsolation(isolation);
        foundationTransaction.setPropagation(propagation);
        return foundationTransaction;
    }
}
