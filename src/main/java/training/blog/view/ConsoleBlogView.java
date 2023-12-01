package training.blog.view;

import training.blog.domain.Post;
import training.blog.domain.Credentials;
import training.blog.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleBlogView implements BlogView {

    private final static Scanner reader = new Scanner(System.in);

    @Override
    public Credentials readCredentials() {
        printLineSeparatorSymbols();
        System.out.print("Login name: ");
        String loginName = reader.nextLine();
        System.out.print("Password: ");
        String password = reader.nextLine();
        return new Credentials(loginName, password);
    }

    @Override
    public void printWelcomeMessage(User user) {
        printLineSeparatorSymbols();
        System.out.println("Welcome to the Blogger Application " + user.getProfile().getName() + "!");
    }

    @Override
    public int selectActivity() {
        printLineSeparatorSymbols();
        System.out.println("1. List my followers (Users who follow me)");
        System.out.println("2. List my followings (Users who I follow)");
        System.out.println("3. List blogposts from my follows");
        System.out.println("4. Search user");
        System.out.println("5. Publish new blog");
        System.out.println("6. Quit application");
        System.out.print("Select Activity(1-6): ");
        return reader.nextInt();
    }

    @Override
    public void printAllFollowers(List<User> followers) {
        printLineSeparatorSymbols();
        System.out.println("My followers:");
        followers.forEach(follower -> System.out.println(follower.getProfile().getName()));
    }

    @Override
    public void printAllFollowed(List<User> followings) {
        printLineSeparatorSymbols();
        System.out.println("My followings:");
        followings.forEach(following -> System.out.println(following.getProfile().getName()));
    }

    @Override
    public String readSearchedUser() {
        printLineSeparatorSymbols();
        System.out.print("Search user by full name: ");
        return reader.nextLine();
    }

    @Override
    public boolean printUserProfile(User user, int numberOfFollowings, int numberOfFollowers) {
        System.out.println("Username: " + user.getCredentials().getLoginname() + " | Fullname: " + user.getProfile().getName());
        System.out.println("Quote: " + user.getProfile().getQuote());
        System.out.println("Location: " + user.getProfile().getLocation() + " | Birth date: " + user.getProfile().getDateOfBirth());
        System.out.println("Join date: " + user.getProfile().getJoinedAt());
        System.out.println("Following: " + numberOfFollowings + " | Followers: " + numberOfFollowers);
        printBlogSeparatorSymbols();
        user.getPosts().forEach(blog -> {
            System.out.println(blog.getTitle());
            System.out.println(blog.getContent() + " | " + blog.getCreatedAt());
            printBlogSeparatorSymbols();
        });
        while (true) {
            System.out.print("Press F to follow or M to go to menu...");
            String follow = reader.nextLine().toUpperCase();
            if (follow.equals("F")) {
                return true;
            }
            if (follow.equals("M")) {
                return false;
            }
        }
    }

    @Override
    public void printSuccessfulFollowMessage(User followed) {
        System.out.println(followed.getProfile().getName() + " has been followed.");
    }

    @Override
    public void printBlogsFromMyFollowings(Map<User, List<Post>> blogpostsFromFollowings) {
        printLineSeparatorSymbols();
        System.out.println("Blogposts from my followings:");
        blogpostsFromFollowings.forEach((key, value) -> value.forEach(blogpost -> {
            System.out.println(blogpost.getTitle());
            System.out.println(blogpost.getContent());
            System.out.println(key.getProfile().getName() + " | " + blogpost.getCreatedAt());
            printBlogSeparatorSymbols();
        }));
    }

    @Override
    public Post readBlogPost() {
        printLineSeparatorSymbols();
        System.out.println("Create new blogpost.");
        printBlogSeparatorSymbols();
        System.out.print("Title: ");
        String title = reader.nextLine();
        System.out.print("Content: ");
        String content = reader.nextLine();
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        return new Post();
    }

    @Override
    public boolean quitApplication(User user) {
        printLineSeparatorSymbols();
        System.out.println("Quitting Blogger Application...");
        System.out.println("Goodbye " + user.getProfile().getName() + ".");
        printLineSeparatorSymbols();
        return true;
    }

    @Override
    public void printError(String message) {
        System.out.println(message);
    }


    private void printLineSeparatorSymbols() {
        System.out.println("===");
    }

    private void printBlogSeparatorSymbols() {
        System.out.println("---");
    }
}
