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

//TODO - над методами используй GetMapping и тд а не RequestMapping. его используй только над контроллером
//     - имя метода getUsersList() не отвечает тому что он делает. если по id пользователь не найден - выкидывай эксепшн
//     - prepareForUpdate() сделай ГЕТ
@Controller
@RequestMapping(value = {"/","/users"})
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"", "/", "/all"})
    public String getUsersList(
            @RequestParam(name = "update_user_id", required = false) Long beingUpdateUserId,
            ModelMap modelMap) {
        modelMap.addAttribute("usersList", userService.getUsersList());
        Optional<User> beingUpdateUser = (beingUpdateUserId != null) ?
                userService.getUserById(beingUpdateUserId) : Optional.of(new User());
        modelMap.addAttribute("beingUpdateUser", beingUpdateUser.orElse(new User()));
        return "users";
    }

    @PostMapping(path = {"/delete"})
    public String deleteUserById(
            @RequestParam(name = "id") Long id,
            ModelMap modelMap) {
        userService.deleteUserById(id);
        return "redirect:/users/";
    }

    @GetMapping(path = {"/read_for_update"})
    public String prepareForUpdate(@RequestParam(name = "update_user_id") Long beingUpdateUserId,
                                   ModelMap modelMap) {
        // По неизвестной причине не получает передавать RequestParam в GET-запросе
        return "redirect:/users?update_user_id=" + beingUpdateUserId;
    }

    @PostMapping(path = {"/add"})
    public String addUser(User user, ModelMap modelMap) {
        userService.addUser(user);
        return "redirect:/users/";
    }

    @PostMapping(path = {"/update"})
    public String updateUser(User user, ModelMap modelMap) {
        userService.updateUser(user);
        return "redirect:/users/";
    }
}
