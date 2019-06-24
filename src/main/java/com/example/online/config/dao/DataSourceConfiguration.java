package com.example.online.config.dao;

import com.example.online.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
//配置mybatis mapper的扫描路径
@MapperScan("com.example.online.dao")
public class DataSourceConfiguration {
    //通过@Value标签从properties中读出数据库配置变量
    @Value("${jdbc.driver}")
    private  String jdbcDriver;
    @Value("${jdbc.url}")
    private  String jdbcUrl;
    @Value("${jdbc.username}")
    private  String jdbcUsername;
    @Value("${jdbc.password}")
    private  String jdbcPassword;

    /**
     * 生成与spring-dao.xml对应的bean dataSource
     */
    @Bean(name="dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        //生成datasouce实例
        ComboPooledDataSource dataSource =new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(10);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setCheckoutTimeout(3000);
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }
}
