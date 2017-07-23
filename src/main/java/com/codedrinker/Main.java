
package com.codedrinker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@ComponentScan
@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
public class Main {
    @RequestMapping("/")
    String index() {
        return "index";
    }
}
