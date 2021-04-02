package com.movies;

import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lx Zhang.
 * @date 2021/4/2 8:43 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFileApplication {
    @Autowired
    private OSSClient client;
    @Test
    public void delete(){
        client.deleteObject("movies-bucket","bf7cffa2a9ba479bbbccc39684b0be40.jpg");
    }
}
