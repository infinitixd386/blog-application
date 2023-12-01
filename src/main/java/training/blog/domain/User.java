package training.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private Credentials credentials;
    private Profile profile;
    private List<Post> posts;
}
