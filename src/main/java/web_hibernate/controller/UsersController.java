package web_hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import web_hibernate.entity.User;
import web_hibernate.service.UserService;

@Controller
@RequestMapping(path = {"/","/users"})
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"", "/", "/all"})
    public String getUsersListWithFormForAddUser(ModelMap modelMap) {
        modelMap.addAttribute("usersList", userService.getUsersList());
        modelMap.addAttribute("beingUpdateUser", new User());
        return "users";
    }

    @PostMapping(path = {"/delete"})
    public String deleteUserById(
            @RequestParam(name = "id") Long id,
            ModelMap modelMap) {
        userService.deleteUserById(id);
        return "redirect:..";
    }

    @GetMapping(path = {"/update"})
    public String getUsersListWithFormForUpdateUser(
            @RequestParam(name = "update_user_id") Long beingUpdateUserId,
            ModelMap modelMap) {
        Optional<User> beingUpdateUser = userService.getUserById(beingUpdateUserId);
        if (beingUpdateUser.isEmpty()) {
            throw new IllegalArgumentException("No user found with requested ID");
        }
        modelMap.addAttribute("beingUpdateUser", beingUpdateUser.get());
        modelMap.addAttribute("usersList", userService.getUsersList());
        return "users";
    }

    @PostMapping(path = {"/add"})
    public String addUser(User user, ModelMap modelMap) {
        userService.addUser(user);
        return "redirect:..";
    }

    @PostMapping(path = {"/update"})
    public String updateUser(User user, ModelMap modelMap) {
        userService.updateUser(user);
        return "redirect:..";
    }
}
