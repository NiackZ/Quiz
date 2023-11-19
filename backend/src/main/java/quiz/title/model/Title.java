package quiz.title.model;

import lombok.Getter;
import lombok.Setter;
import quiz.genres.Genre;
import quiz.links.model.Link;
import quiz.mark.model.Mark;
import quiz.status.Status;
import quiz.types.model.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String ruName;
    private String enName;
    private String posterURL;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @ManyToMany
    @JoinTable(
            name = "title_genres",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    private String duration;
    @OneToMany
    @JoinTable(
            name = "title_links",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private List<Link> links;
    @OneToMany
    @JoinTable(
            name = "title_marks",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private List<Mark> marks;
    private String description;
}
