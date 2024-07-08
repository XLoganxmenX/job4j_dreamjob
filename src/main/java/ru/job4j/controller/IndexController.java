package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.model.User;
import ru.job4j.utils.AuthUtils;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String getIndex(Model model, HttpSession session) {
        AuthUtils.checkUserSession(model, session);
        return "index";
    }
}
