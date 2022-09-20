package com.bridgelabz.fundoonote.user.service;

import java.util.List;

import com.bridgelabz.fundoonote.user.dto.LoginDto;
import com.bridgelabz.fundoonote.user.dto.RegisterDto;
import com.bridgelabz.fundoonote.user.dto.UserDto;
import com.bridgelabz.fundoonote.user.exception.UserException;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.utility.UserResponse;

public interface IUserService {

	UserResponse registration(RegisterDto registerDto) throws UserException;

	UserResponse login(LoginDto loginDto) throws UserException;

	UserResponse updateUser(int id, RegisterDto registerDto);

	UserResponse deleteUser(int id) throws UserException;

	UserResponse getById(int id) throws UserException;

	UserResponse userVerification(String token);

	List<UserDto> findAll();

	User forgotPassword(String email, String password) throws UserException;

}