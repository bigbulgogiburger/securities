package com.template.securities.controller;


import com.template.securities.domain.Users;
import com.template.securities.dto.TestDto;
import com.template.securities.dto.UserDto;
import com.template.securities.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Users> signup(@Valid @RequestBody UserDto userDto)
    {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Users> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> getUserInfo(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }

    @PostMapping("/user")
    public ResponseEntity<Users> getUserInfo(@RequestBody TestDto testDto){
        return ResponseEntity.ok(userService.getUserWithAuthorities(testDto.getUserName()));
    }

}
