package training.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private String name;
    private LocalDate dateOfBirth;
    private String location;
    private String quote;
    private LocalDate joinedAt;
}
