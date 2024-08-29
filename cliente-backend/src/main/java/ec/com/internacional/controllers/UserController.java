package ec.com.internacional.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.internacional.models.Usuario;
import ec.com.internacional.services.impl.UsuarioService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsuarioService userService;

    @PostMapping("/register")
    public Usuario registerUser(@RequestBody Usuario user) {
        return userService.saveUser(user);
    }
}
