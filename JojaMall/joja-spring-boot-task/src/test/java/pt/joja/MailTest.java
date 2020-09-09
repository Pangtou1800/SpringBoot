package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void mailTest() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("通知-今晚开会");
        simpleMailMessage.setText("Mary：你好！今晚7点开会");
        simpleMailMessage.setTo("porntou1404@163.com");
        simpleMailMessage.setFrom("pangtou1404@163.com");
        javaMailSender.send(simpleMailMessage);
    }

    @Test
    public void mailTest2() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setSubject("通知-好东西");
        messageHelper.setText("Mary：<br>你好！<br>给你看个好东西", true);

        //messageHelper.addAttachment("AppleJack.png", new File("C:\\workspace\\SpringBoot\\JojaMall\\joja-spring-boot-task\\target\\classes\\imgs\\AppleJack.png"));

        messageHelper.setTo("porntou1404@163.com");
        messageHelper.setFrom("pangtou1404@163.com");

        javaMailSender.send(mimeMessage);
    }

    @Test
    public void test01() throws IOException {
        System.out.println(new File("").getCanonicalPath());
    }

}
