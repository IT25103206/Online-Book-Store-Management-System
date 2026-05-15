package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Integer id);
    UserDTO login(UserDTO loginDTO);
}
