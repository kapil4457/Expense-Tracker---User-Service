package com.example.userservice.services;

import com.example.userservice.entities.UserInfo;
import com.example.userservice.entities.UserInfoDto;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto){
        Function<UserInfo,UserInfo> updatingUser = user->{
            if(!userInfoDto.getEmail().isEmpty()){
                user.setEmail(userInfoDto.getEmail());
            }
            if(!userInfoDto.getFirstName().isEmpty()){
                user.setFirstName(userInfoDto.getFirstName());
            }
            if(!userInfoDto.getLastName().isEmpty()){
                user.setLastName(userInfoDto.getLastName());
            }
            if(userInfoDto.getPhoneNumber() != 0){
                user.setPhoneNumber(userInfoDto.getPhoneNumber());
            }
            if(!userInfoDto.getProfilePic().isEmpty()){
                user.setProfilePic(userInfoDto.getProfilePic());
            }
            return userRepository.save(userInfoDto.transformToUserInfo());
        };
        Supplier<UserInfo> createUser = ()->{
            return userRepository.save(userInfoDto.transformToUserInfo());
        };
        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId()).map(updatingUser).orElseGet(createUser);
        return new UserInfoDto(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }

    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception{
        Optional<UserInfo> userInfoOptional = userRepository.findByUserId(userInfoDto.getUserId());
        if(userInfoOptional.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo = userInfoOptional.get();
        return new UserInfoDto(
            userInfo.getUserId(),
            userInfo.getFirstName(),
            userInfo.getLastName(),
            userInfo.getPhoneNumber(),
            userInfo.getEmail(),
            userInfo.getProfilePic()
        );
    }
}
