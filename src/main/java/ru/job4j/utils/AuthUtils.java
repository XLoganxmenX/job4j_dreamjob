package ru.job4j.utils;

import org.springframework.ui.Model;
import ru.job4j.model.User;

import javax.servlet.http.HttpSession;

public class AuthUtils {
    public static void checkUserSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
