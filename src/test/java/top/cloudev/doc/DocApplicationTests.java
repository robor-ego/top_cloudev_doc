package top.cloudev.doc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocApplicationTests {

    //TODO 执行测试时建议注释掉启动项目的@EnableCaching注解，防止因缓存而导致的问题。

    @Test
    public void contextLoads() {

    }

    /**
     * 将对象转换成JSON字符串
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String Obj2Json(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
