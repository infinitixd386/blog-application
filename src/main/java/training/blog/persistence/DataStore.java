package training.blog.persistence;

import training.blog.domain.Following;
import training.blog.domain.User;

import java.util.List;

public interface DataStore {
    void init();
    List<User> getAllUsers();
    List<Following> getAllFollowings();
}
