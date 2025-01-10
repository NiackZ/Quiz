package quiz.anime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnimeGetShortDTO {
    private Long id;
    private String posterURL;

    public AnimeGetShortDTO (Anime anime) {
        this.id = anime.getId();
        this.posterURL = anime.getPosterURL();
    }
}
