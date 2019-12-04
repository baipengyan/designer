package com.gao.designer;

import com.gao.designer.dao.UserDao;
import com.gao.designer.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesignerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesignerApplication.class, args);
    }

}
