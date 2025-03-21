package web_hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import web_hibernate.entity.User;
import web_hibernate.service.UserService;

@Controller
@RequestMapping(value = {"/","/users"})
public class UsersController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = {"", "/", "/all"}, method = RequestMethod.GET)
    public String getUsersList(
            @RequestParam(name = "update_user_id", required = false) Long beingUpdateUserId,
            ModelMap modelMap) {
        modelMap.addAttribute("usersList", userService.getUsersList());
        Optional<User> beingUpdateUser = (beingUpdateUserId != null) ?
                userService.getUserById(beingUpdateUserId) : Optional.of(new User());
        modelMap.addAttribute("beingUpdateUser", beingUpdateUser.orElse(new User()));
        return "users";
    }

    @RequestMapping(path = {"/delete"}, method = RequestMethod.POST)
    public String deleteUserById(
            @RequestParam(name = "id") Long id,
            ModelMap modelMap) {
        userService.deleteUserById(id);
        return "redirect:/users/";
    }

    @RequestMapping(path = {"/read_for_update"}, method = RequestMethod.POST)
    public String prepareForUpdate(@RequestParam(name = "update_user_id") Long beingUpdateUserId,
                                   ModelMap modelMap) {
        return "redirect:/users?update_user_id=" + beingUpdateUserId;
    }

    @RequestMapping(path = {"/add"}, method = RequestMethod.POST)
    public String addUser(User user, ModelMap modelMap) {
        userService.addUser(user);
        return "redirect:/users/";
    }

    @RequestMapping(path = {"/update"}, method = RequestMethod.POST)
    public String updateUser(User user, ModelMap modelMap) {
        userService.updateUser(user);
        return "redirect:/users/";
    }
}
