package quiz.anime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import quiz.genres.Genre;
import quiz.links.Link;
import quiz.marks.Mark;
import quiz.studios.Studio;
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
    private List<Studio> studios;
    @ManyToMany
    @JoinTable(
            name = "anime_genres",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
    @OneToMany
    @JoinTable(
            name = "anime_links",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private List<Link> links;
    @OneToMany
    @JoinTable(
            name = "anime_marks",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private List<Mark> marks;
}
