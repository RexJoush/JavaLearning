package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

/**
 * spring 和数据库相关的配置
 */

public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    /**
     * 创建一个 QueryRunner 对象
     * @param dataSource
     * @return
     */
    @Bean(name = "runner")
    @Scope("prototype")
                                        // 此处，如果存在多个 数据源，则配置此 Qualifier 注解可以限定访问的数据源
    public QueryRunner createQueryRunner(@Qualifier("ds1") DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    /**
     * 创建数据源对象
     * @return
     */
    @Bean(name = "ds1")
    public DataSource createDataSource(){
        ComboPooledDataSource ds = new ComboPooledDataSource();

        try {
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    @Bean(name = "ds2")
    public DataSource createDataSource2(){
        ComboPooledDataSource ds = new ComboPooledDataSource();

        try {
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
