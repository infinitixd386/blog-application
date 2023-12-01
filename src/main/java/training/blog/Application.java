package training.blog;

import java.nio.file.Path;

import training.blog.domain.Credentials;
import training.blog.domain.User;
import training.blog.persistence.FileDataStore;
import training.blog.persistence.DataStore;
import com.epam.universities.blog.service.*;
import training.blog.view.BlogView;
import training.blog.view.ConsoleBlogView;
import training.blog.service.*;

public class Application {
    private static final String BASE_DIR_PATH = "input";
    private final UserService userService;
    private final BlogService blogService;

    public Application(UserService userService, BlogService blogService) {
        this.userService = userService;
        this.blogService = blogService;
    }

    public static void main(String[] args) {
        DataStore dataStore = new FileDataStore(Path.of(BASE_DIR_PATH));
        dataStore.init();

        UserService userService = new DefaultUserService(dataStore);
        BlogService blogService = new DefaultBlogService(dataStore);
        Application app = new Application(userService, blogService);
        app.run();
    }

    private void run() {
        BlogView blogView = new ConsoleBlogView();

        Credentials credentials = blogView.readCredentials();

        User user = new User();
        try {
            user = userService.authenticate(credentials);
        } catch (AuthenticationException ex) {
            blogView.printError("Login name and/or password not valid!");
            System.exit(1);
        }

        blogView.printWelcomeMessage(user);
        System.out.println("1. List my followers (Users who follow me)");
        System.out.println("2. List my followings (Users who I follow)");
        System.out.println("3. List blogposts from my follows");
        System.out.println("4. Search user");
        System.out.println("5. Publish new blog");
        System.out.println("6. Quit application");
        boolean finished = false;
        int chosenActivity;
        do {
            chosenActivity = blogView.selectActivity();
            switch (chosenActivity) {
                case 1 -> blogView.printAllFollowers(userService.getAllFollowers(user));
                case 2 -> blogView.printAllFollowed(userService.getAllFollowed(user));
                case 3 -> blogView.printBlogsFromMyFollowings(blogService.getBlogPostsFromFollowedUsers(user));
                case 4 -> {
                    User searchedUser = userService.searchUser(blogView.readSearchedUser()).get();
                    if (blogView.printUserProfile(searchedUser,
                            userService.getAllFollowed(user).size(),
                            userService.getAllFollowers(user).size())) {
                        userService.followUser(user, searchedUser);
                        blogView.printSuccessfulFollowMessage(searchedUser);
                    }
                }
                case 5 -> blogService.createBlogPost(user, blogView.readBlogPost());
                case 6 -> finished = blogView.quitApplication(user);
            }
        } while (!finished);
    }
}