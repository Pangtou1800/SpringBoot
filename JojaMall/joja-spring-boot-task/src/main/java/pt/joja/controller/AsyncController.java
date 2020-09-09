package pt.joja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.joja.service.AsyncService;

@RestController
public class AsyncController {

    AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @GetMapping("/hello")
    public String hello() {
        asyncService.hello();
        return "success";
    }

    @GetMapping("/hello2")
    public String hello2() {
        int num = asyncService.getNumber();
        return "success: " + num;
    }

}
