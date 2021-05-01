package ma.emsi.crud_API.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.crud_API.entities.UserEntity;
import ma.emsi.crud_API.repositories.UserRepository;
import ma.emsi.crud_API.services.UserService;
import ma.emsi.crud_API.shared.Utils;
import ma.emsi.crud_API.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	Utils util;

	@Override
	public UserDto createUser(UserDto user) {
		
		UserEntity user2 = userRepository.findByEmail(user.getEmail());
		
		if (user2 != null) throw new RuntimeException("user alrady exist");
		
		UserEntity userEntity = new UserEntity();
		
		BeanUtils.copyProperties(user, userEntity);
		
		userEntity.setEncryptedPassword("test password");
		userEntity.setUserId(util.generateUserId(32));
		
		UserEntity newUser = userRepository.save(userEntity);
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(newUser, userDto);
		
		return userDto;
	}

}
