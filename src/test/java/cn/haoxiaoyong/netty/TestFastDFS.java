package cn.haoxiaoyong.netty;


import cn.haoxiaoyong.netty.util.FastDFSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by haoxy on 2019/1/14.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootTest(classes = NettyApplication.class)
@RunWith(SpringRunner.class)
public class TestFastDFS {

    @Autowired
    private FastDFSClient fastDFSClient;

    @Test
    public void testFastDFS() throws IOException {
        String path = fastDFSClient.uploadFile(new File("/Users/haoxiaoyong/mui/chat-view/unpackage/release/H51F4EF0F_0114164246.apk"));
        System.out.println(path);

    }
}
