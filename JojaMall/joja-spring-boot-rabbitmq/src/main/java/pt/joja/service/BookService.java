package pt.joja.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import pt.joja.bean.Book;

@Service
public class BookService {
    @RabbitListener(queues = {"joja.news", "market.news"})
    public void receive(Book book) {
        System.out.println("收到了书：" + book);
    }

    @RabbitListener(queues = {"joja"})
    public void receiveMessage(Message message) {
        System.out.println("收到了消息：");
        System.out.println(message.getMessageProperties());
        System.out.println(message.getBody());
    }
}
