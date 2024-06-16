package com.example.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.db.model.UsersModel;
import com.example.db.model.User;
import com.example.db.repository.UsersRepository;
import com.example.db.repository.UserRepository;

@Service
public class UsersService {

   
    private UsersRepository usersRepository;
    private UserRepository userRepository;

    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser(String login, String password, String email){
        if(login != null && password != null){

            if(usersRepository.findFirstByLogin(login).isPresent()){
                System.out.println("Duplicate login");
                return null;
            }

            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            usersModel.setEmail(email);

            User nww = new User();
            nww.setAge(18);
            nww.setGender('F');
            nww.setOccupation(1);
            nww.setZipCode("06810");

            String curr = nww.getId();
            usersModel.setUserId(curr);

            userRepository.save(nww);
            return usersRepository.save(usersModel);
        } else{
            return null;
        }
    }
    public UsersModel authenticate(String login, String password){
        return usersRepository.findByLoginAndPassword(login, password).orElse(null);
    }
}
