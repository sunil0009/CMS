package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.dto.UserRequest;
import com.example.cms.dto.UserResponse;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.model.User;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private ResponseStructure<UserResponse> responseStructure;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, ResponseStructure<UserResponse> responseStructure,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.responseStructure = responseStructure;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Failed to user register");
		
		User user = userRepository.save(mapToUserEntity(userRequest , new User()));
		
		return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
				.setMessage("User Registered Successfully")
				.setData(mapToUserResponse(user)));
	}
	
	private UserResponse mapToUserResponse(User user) {
		UserResponse urp = new UserResponse();
		urp.setUserId(user.getUserId());
		urp.setUserName(user.getUserName());
		urp.setEmail(user.getEmail());
		return urp;
	}
	
	private User mapToUserEntity(UserRequest userRequest , User user) {
		user.setEmail(userRequest.getEmail());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUserName(userRequest.getUserName());
		return user;
	}
}
