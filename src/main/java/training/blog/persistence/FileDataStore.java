package training.blog.persistence;

import training.blog.domain.Credentials;
import training.blog.domain.Following;
import training.blog.domain.Profile;
import training.blog.domain.User;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileDataStore implements DataStore {
    private static final String USERS_CSV_NAME = "users.csv";
    private static final String FOLLOWINGS_CSV_NAME = "followings.csv";
    private static final String BLOGS_TXT_NAME = "blogs.txt";
    private final Path basePath;
    private static final String[] USER_COLUMNS = {"id", "loginname", "password", "name",
            "dateOfBirth", "location", "quote", "joinedAt"};
    private static final String[] FOLLOWING_COLUMNS = {"follower", "followed", "followedAt"};
    private List<User> users;
    private List<Following> followings;

    public FileDataStore(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public void init() {
        readUsersCSV();
        readFollowingsCSV();
    }

    private void readUsersCSV() {
        users = new ArrayList<>();
        List<String> usersStringList = new ArrayList<>();
        try (Scanner scanner = new Scanner(basePath.resolve(USERS_CSV_NAME).toFile())) {
            while (scanner.hasNextLine()) {
                usersStringList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
        for (String userAsString : usersStringList) {
            String[] userFields = userAsString.split(",");
            users.add(createUser(userFields));
        }
    }

    private void readFollowingsCSV() {
        followings = new ArrayList<>();
        List<String> followingsStringList = new ArrayList<>();
        try (Scanner scanner = new Scanner(basePath.resolve(FOLLOWINGS_CSV_NAME).toFile())) {
            while (scanner.hasNextLine()) {
                followingsStringList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found");
        }
        for (String followingAsString : followingsStringList) {
            String[] followingFields = followingAsString.split(",");
            followings.add(createFollowing(followingFields));
        }
    }

    private User createUser(String[] userFields) {
        User user = createPlainUser(userFields);
        return user;
    }

    private User createPlainUser(String[] userFields) {
        User user = new User();
        user.setId(Long.parseLong(getUserDataByColumnName(userFields, "id")));
        user.setCredentials(createCredentials(userFields));
        user.setProfile(createProfile(userFields));
        return user;
    }

    private Credentials createCredentials(String[] credentialFields) {
        return new Credentials(getUserDataByColumnName(credentialFields, "loginname"),
                getUserDataByColumnName(credentialFields, "password"));
    }

    private Profile createProfile(String[] profileFields) {
        return new Profile(getUserDataByColumnName(profileFields, "name"),
                LocalDate.parse(getUserDataByColumnName(profileFields, "dateOfBirth")),
                getUserDataByColumnName(profileFields, "location"),
                getUserDataByColumnName(profileFields, "quote"),
                LocalDate.parse(getUserDataByColumnName(profileFields, "joinDate")));
    }

    private Following createFollowing(String[] followingFields) {
        Following following = new Following();
        following.setFollower(users.stream()
                .filter(user -> user.getId() == Long.parseLong(getFollowingDataByColumnName(followingFields, "follower")))
                .findAny()
                .get());
        following.setFollowed(users.stream()
                .filter(user -> user.getId() == Long.parseLong(getFollowingDataByColumnName(followingFields, "followed")))
                .findAny()
                .get());
        following.setFollowedAt(LocalDate.parse(getFollowingDataByColumnName(followingFields, "followDate")));
        return following;
    }

    private String getUserDataByColumnName(String[] data, String fieldName) {
        for (int i = 0; i < USER_COLUMNS.length; i++) {
            if (fieldName.equals(USER_COLUMNS[i])) {
                return data[i];
            }
        }
        throw new RuntimeException("Error during user data reading: Column with the name" + fieldName + "is not specified");
    }

    private String getFollowingDataByColumnName(String[] data, String fieldName) {
        for (int i = 0; i < FOLLOWING_COLUMNS.length; i++) {
            if (fieldName.equals(FOLLOWING_COLUMNS[i])) {
                return data[i];
            }
        }
        throw new RuntimeException("Error during following data reading: Column with the name" + fieldName + "is not specified");
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public List<Following> getAllFollowings() {
        return followings;
    }
}
