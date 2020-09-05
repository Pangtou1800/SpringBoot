package pt.joja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.joja.starter.HelloService;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name")String name) {
        return helloService.sayHello(name);
    }

}
