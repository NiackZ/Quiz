package quiz.anime;

import lombok.Getter;
import lombok.Setter;
import quiz.genres.Genre;
import quiz.types.model.Type;

import java.util.List;

@Getter
@Setter
public class AnimeGetDTO {
    private Long id;
    private String rusName;
    private String romName;
    private String posterURL;
    private Type type;
    private List<Genre> genreList;

    public AnimeGetDTO (Anime anime) {
        this.id = anime.getId();
        this.rusName = anime.getRuName();
        this.romName = anime.getRomajiName();
        this.posterURL = anime.getPosterURL();
        this.type = anime.getType();
        this.genreList = anime.getGenres();
    }
}
