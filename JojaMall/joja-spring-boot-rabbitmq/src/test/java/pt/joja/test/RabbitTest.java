package pt.joja.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.joja.bean.Book;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void testCreateExchange() {
//        amqpAdmin.declareExchange(new DirectExchange("amqp.exchange.direct"));
//        amqpAdmin.declareQueue(new Queue("ampq.queue01"));
        amqpAdmin.declareBinding(new Binding("ampq.queue01", Binding.DestinationType.QUEUE, "amqp.exchange.direct","queue01", null));
        System.out.println("创建完成");

    }

    @Test
    public void testDirect(){
        //Message message = new Message();
        //rabbitTemplate.send("", "", message);
        Map<String, Object> msgs = new HashMap<>();
        msgs.put("msg", "Welcome to Joja Market");
        msgs.put("data", Arrays.asList("Hello world", 123, true));

        //rabbitTemplate.convertAndSend("exchange.direct" ,"joja.news", msgs);
        rabbitTemplate.convertAndSend("exchange.topic", "joja.news", new Book("红楼梦", "曹雪芹"));

    }

    @Test
    public void testReceive() {
        Object o = rabbitTemplate.receiveAndConvert("joja.news");
        System.out.println(o);
    }

}
