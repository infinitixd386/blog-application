package training.blog.service;

import training.blog.domain.Post;
import training.blog.domain.User;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Map<User, List<Post>> getBlogPostsFromFollowedUsers(User user);

    void createBlogPost(User user, Post post);
}
