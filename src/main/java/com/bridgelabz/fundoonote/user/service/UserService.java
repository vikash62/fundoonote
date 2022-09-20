package com.bridgelabz.fundoonote.user.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.user.dto.LoginDto;
import com.bridgelabz.fundoonote.user.dto.RegisterDto;
import com.bridgelabz.fundoonote.user.dto.UserDto;
import com.bridgelabz.fundoonote.user.exception.UserException;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
import com.bridgelabz.fundoonote.user.utility.EmailSender;
import com.bridgelabz.fundoonote.user.utility.TokenManager;
import com.bridgelabz.fundoonote.user.utility.UserResponse;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	EmailSender emailSender;

	@Override
	public UserResponse registration(RegisterDto registerDto) throws UserException {
		Optional<User> find = userRepository.findByEmail(registerDto.getEmail());
		if (find.isPresent()) {
			throw new UserException(String.format(
					"!!! The Email entered has already present in our Database...Kindly Login with your Credentials !!!"));
		}
		User user = modelMapper.map(registerDto, User.class);
		User save = userRepository.save(user);
		String url = "http://localhost:8080/home/verify?token=" + tokenManager.encodeToken(save.getId());
		emailSender.sendSimpleEmail(save.getEmail(),
				"Hi, Am Spring, Sending the mail to your notice, confirm your account, please click here : " + url,
				"Mail from Spring Boot");
		UserResponse response = new UserResponse(
				"The response message : User Information are added Sucessfully to the DataBase", 200, save);
		return response;
	}

	@Override
	public UserResponse login(LoginDto loginDto) throws UserException {
		String token;
		Optional<User> isUser = userRepository.findByEmail(loginDto.getEmail());
		if (isUser.isPresent()) {
			String password = isUser.get().getPassword();
			if (password.equals(loginDto.getPassword())) {
				token = tokenManager.encodeToken(isUser.get().getId());
				return new UserResponse("..User found at our Database..", 200, token);
			} else
				throw new UserException("Password Entered is Wrong..Kindly Check your password");
		} else {
			throw new UserException("Entered Email Id or Password is Wrong... Kindly check and Try again ");
		}
	}

	@Override
	public UserResponse updateUser(int id, RegisterDto registerDto) {
		User change = modelMapper.map(registerDto, User.class);
		change.setId(id);
		User update = userRepository.save(change);
		UserResponse response = new UserResponse(
				"The response message : User Information Sucessfully updated to the DataBase", 200, update);
		return response;
	}

	@Override
	public UserResponse deleteUser(int id) throws UserException {
		Optional<User> find = userRepository.findById(id);
		if (!find.isPresent()) {
			throw new UserException(String.format("!!! User Details not found with id " + id
					+ ". Kindly check the entered Information for Deleting !!!"));
		}
		userRepository.deleteById(id);
		UserResponse response = new UserResponse(
				"The response message : User Information Sucessfully deleted from the DataBase", 200, id);
		return response;
	}

	@Override
	public UserResponse userVerification(String token) throws UserException {
		int id = tokenManager.decodeToken(token);
		User isUser = userRepository.findById(id).orElseThrow(() -> new UserException("Invalid User ID"));
		isUser.setIsVerified(true);
		userRepository.save(isUser);
		return new UserResponse("User verified Sucessfully", 200, id);

	}

	@Override
	public UserResponse getById(int id) throws UserException {
		Optional<User> find = userRepository.findById(id);
		if (!find.isPresent()) {
			throw new UserException(
					String.format("!!! User not found with id " + id + ". Kindly check the entered Information !!!"));
		}
		UserDto dto = modelMapper.map(find, UserDto.class);
		UserResponse response = new UserResponse(
				"The response message : User Information Sucessfully retrieved from the DataBase", 200, dto);

		return response;
	}

	@Override
	public List<UserDto> findAll() {
		List<User> user = userRepository.findAll();
		Type userType = new TypeToken<List<UserDto>>() {

		}.getType();
		List<UserDto> userDto = modelMapper.map(user, userType);
		return userDto;
	}

	@Override
	public User forgotPassword(String email, String password) throws UserException {
		Optional<User> isUser = userRepository.findByEmail(email);
		if (isUser.isPresent()) {
			isUser.get().setPassword(password);
			return userRepository.save(isUser.get());
		} else {
			throw new UserException("Invalid Email");
		}
	}
}