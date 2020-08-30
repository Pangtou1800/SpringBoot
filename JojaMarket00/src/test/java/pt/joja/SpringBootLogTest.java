package pt.joja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootLogTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testLog() {
        // 日志的级别由低到高：
        // trace -> debug -> info -> warn -> error
        // 可以调整需要输出的日志级别，只打印指定以上级别的信息
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        // SpringBoot默认指定info级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");
    }

}
