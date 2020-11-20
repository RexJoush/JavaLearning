package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * 该类是一个配置类，作用和 bean.xml 一样
 * spring 中的新注解
 * Configuration 注解
 *     作用：指定当前类是一个配置类
 *     细节：当配置类作为 AnnotationConfigApplicationContext 对象创建的参数时，该注解可以不写
 * ComponentScan 注解
 *     作用：指定 Spring 在初始化时要扫描的包
 *         <context:component-scan base-package="com.joush"></context:component-scan>
 *     属性：value： 和 beanPackage 作用一样，用于指定创建容器需要扫描的包，等同于上一句的配置
 *
 * Bean 注解
 *     作用：用于把当前方法的返回值作为 bean 对象存入 spring 容器中
 *     属性: name 用于指定 bean 的 id，默认是当前的方法名
 *     细节：当我们使用注解方法配置时，如果方法有参数，那么 spring 会到 ioc 容器中查询是否有对应的 bean 对象，查找方式和 Autowired 一样
 * PropertiesSource 注解
 *     作用：用于指定 properties 文件的位置
 *     属性：value：指定文件的名称和路径
 *          关键字 classpath 表示类路径下
 */

@Configuration
@Import(JdbcConfig.class)
@ComponentScan({"com.joush","config"})
@PropertySource("classpath:jdbcConfig.properties")
public class SpringConfiguration {


}
