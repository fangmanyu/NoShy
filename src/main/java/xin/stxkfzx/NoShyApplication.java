package xin.stxkfzx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    // 加入事务
@MapperScan(value = "xin.stxkfzx.noshy.mapper")
public class NoShyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoShyApplication.class, args);
    }
}
