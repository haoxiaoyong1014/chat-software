package cn.haoxiaoyong.netty;

import cn.haoxiaoyong.netty.util.IdWorker;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.servlet.MultipartConfigElement;


/**
 * Created by haoxy on 2019/1/5.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootApplication
@MapperScan({"cn.haoxiaoyong.netty.mapper"})
@Import(FdfsClientConfig.class)
public class NettyApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("5120KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("5120KB");
        return factory.createMultipartConfig();
    }
}
