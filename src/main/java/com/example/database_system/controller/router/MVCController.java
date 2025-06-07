package com.example.database_system.controller.router;

import com.example.database_system.pojo.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping
public class MVCController {


    @GetMapping({"/login",""})
    public String ShowLoginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String ShowHomePage() {
        return "home";
    }

    @GetMapping("/create")
    public String ShowCreatePage() {
        return "create";
    }

    @GetMapping("/vote/{voteId}")
    public String ShowVotePage() {
        return "vote_detail";
    }

    @GetMapping("/user-detail")
    public String ShowUserPage() {;
        return "user_detail";
    }

    @GetMapping("vote/search/{keyword}")
    public String ShowSearchPage() {
        return "search";
    }

    @GetMapping("my-vote")
    public String ShowMyVotePage() {
        return "my_vote";
    }


}
