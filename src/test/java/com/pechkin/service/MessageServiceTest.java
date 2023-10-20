package com.pechkin.service;

import com.pechkin.PechkinApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PechkinApplication.class)
@AutoConfigureMockMvc
public class MessageServiceTest {
    public void newMessageTest() {

    }
}
