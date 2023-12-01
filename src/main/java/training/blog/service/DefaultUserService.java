package training.blog.service;

import training.blog.domain.Credentials;
import training.blog.domain.Following;
import training.blog.domain.User;
import training.blog.persistence.DataStore;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DefaultUserService implements UserService{

    private final DataStore dataStore;

    public DefaultUserService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public User authenticate(Credentials credentials) throws AuthenticationException {
        return dataStore.getAllUsers()
                .stream()
                .filter(user -> user.getCredentials().equals(credentials))
                .findAny()
                .orElseThrow(() -> new AuthenticationException("Wrong credentials!"));
    }

    @Override
    public Optional<User> searchUser(String fullName) {
        return dataStore.getAllUsers()
                .stream()
                .filter(user -> user.getProfile().getName().equals(fullName))
                .findAny();
    }


    @Override
    public List<User> getAllFollowers(User user) {
        return dataStore.getAllFollowings()
                .stream()
                .filter(following -> following.getFollowed().equals(user))
                .map(Following::getFollower)
                .toList();
    }

    @Override
    public List<User> getAllFollowed(User user) {
        return dataStore.getAllFollowings()
                .stream()
                .filter(following -> following.getFollower().equals(user))
                .map(Following::getFollowed)
                .toList();
    }

    @Override
    public void followUser(User follower, User followed) {
        dataStore.getAllFollowings().add(new Following(follower, followed, LocalDate.now()));
    }
}
