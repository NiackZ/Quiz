package quiz.anime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import quiz.genres.Genre;
import quiz.title.model.Title;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "animes")
public class Anime extends Title {
    private String romajiName;
    private String kanjiName;
    @ManyToMany
    @JoinTable(
            name = "anime_studios",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    private List<Genre> studios;
    private String season;
}
