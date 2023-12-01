package training.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private long id;
    private User author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
