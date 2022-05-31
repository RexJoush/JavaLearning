import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Rex Joush
 * @time 2022.05.02
 */
@ComponentScan("com.joush.service.impl")
public class MainApplication {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("provider.xml");
        ioc.start();
        System.in.read(); // 按任意键退出
    }
}
