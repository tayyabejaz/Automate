package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.User;
import com.innovidio.androidbootstrap.repository.TripRepository;
import com.innovidio.androidbootstrap.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {
    @Inject
    UserRepository userRepository;
    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        this.userRepository.addUser(user);
    }

    public void deleteUser(User user) {
        this.userRepository.deleteUser(user);
    }

    public void updateUser(User user) {
        this.userRepository.updateUser(user);
    }

    public LiveData<User> getUserById(int id) {
        return this.userRepository.getUserById(id);
    }
}