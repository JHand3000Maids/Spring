package board.ex01board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")  // 기본 주소 요청이 오면 index.html로
    public String index() {
        return "index";
    }
}
