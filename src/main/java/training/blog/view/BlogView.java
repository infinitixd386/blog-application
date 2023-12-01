package training.blog.view;

import training.blog.domain.Post;
import training.blog.domain.Credentials;
import training.blog.domain.User;

import java.util.List;
import java.util.Map;

public interface BlogView {
    Credentials readCredentials();
    void printWelcomeMessage(User user);
    int selectActivity();
    void printAllFollowers(List<User> followers);
    void printAllFollowed(List<User> followed);
    String readSearchedUser();
    boolean printUserProfile(User user, int numberOfFollowings, int numberOfFollowers);
    void printSuccessfulFollowMessage(User followed);
    void printBlogsFromMyFollowings(Map<User, List<Post>> blogpostsFromFollowings);
    Post readBlogPost();
    boolean quitApplication(User user);
    void printError(String message);
}
