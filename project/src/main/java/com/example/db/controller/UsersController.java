package com.example.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.db.model.UsersModel;
import com.example.db.service.UsersService;

@Controller
public class UsersController {

    //private final UsersService usersService;
    @Autowired
    private UsersService usersService;

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel){
        System.out.println("register request: " + usersModel);
        UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model){
        System.out.println("register request: " + usersModel);
        UsersModel autheticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if(autheticated != null){
            model.addAttribute("userLogin", autheticated.getLogin());
            return "personal_page";
        } else{
            return "error_page";
        }
        
    }
}
