package training.blog.service;

import training.blog.domain.Post;
import training.blog.domain.Following;
import training.blog.domain.User;
import training.blog.persistence.DataStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBlogService implements BlogService {

    private final DataStore dataStore;

    public DefaultBlogService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Map<User, List<Post>> getBlogPostsFromFollowedUsers(User user) {
        Map<User, List<Post>> blogpostsFromFollowings = new HashMap<>();
        dataStore.getAllFollowings()
                .stream()
                .filter(following -> following.getFollower().equals(user))
                .map(Following::getFollowed)
                .toList()
                .forEach(following -> blogpostsFromFollowings.put(following, following.getPosts()));
        return blogpostsFromFollowings;
    }

    @Override
    public void createBlogPost(User user, Post post) {
        post.setAuthor(user);
        user.getPosts().add(post);
    }
}
