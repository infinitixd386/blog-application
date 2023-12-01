package training.blog.service;

import training.blog.domain.Credentials;
import training.blog.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User authenticate(Credentials credentials) throws AuthenticationException;

    Optional<User> searchUser(String fullName);

    List<User> getAllFollowers(User user);

    List<User> getAllFollowed(User user);

    void followUser(User follower, User followed);
}
