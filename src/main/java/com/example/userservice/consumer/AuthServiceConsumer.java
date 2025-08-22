package com.example.userservice.consumer;

import com.example.userservice.entities.UserInfo;
import com.example.userservice.entities.UserInfoDto;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {

    private UserRepository userRepository;

    AuthServiceConsumer(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @KafkaListener(topics="${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData){
        try{
            UserInfo userInfo = eventData.transformToUserInfo();
            userRepository.save(userInfo);
            System.out.println("Hello");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
