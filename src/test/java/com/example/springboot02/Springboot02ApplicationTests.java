package com.example.springboot02;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02ApplicationTests {

    @Test
    public void contextLoads() {
        JSON.toJSONString("");
    }

}
