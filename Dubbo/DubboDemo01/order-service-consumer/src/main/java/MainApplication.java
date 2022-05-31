import com.joush.service.OrderService;
import com.joush.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Rex Joush
 * @time 2022.05.02
 */
public class MainApplication {



    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("consumer.xml");
        System.out.println("调用开始。。。");
        OrderService service = ioc.getBean(OrderService.class);
        service.initOrder("1");
        System.out.println("调用完成。。。");
        System.in.read();
    }


}
