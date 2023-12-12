package quiz.anime.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import quiz.anime.Anime;
import quiz.anime.AnimeCreateDTO;
import quiz.anime.AnimeGetDTO;
import quiz.anime.repository.AnimeRepository;
import quiz.genres.GenreRepository;
import quiz.links.LinkService;
import quiz.marks.MarkRepository;
import quiz.status.StatusRepository;
import quiz.studios.StudioRepository;
import quiz.types.repository.TypeRepository;
import quiz.utils.Utils;
import quiz.utils.model.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final TypeRepository typeRepository;
    private final GenreRepository genreRepository;
    private final StatusRepository statusRepository;
    private final MarkRepository markRepository;
    private final StudioRepository studioRepository;
    private final LinkService linkService;

    public Long create(AnimeCreateDTO animeCreateDTO) throws IOException {
        Anime anime = new Anime();

        anime.setRuName(animeCreateDTO.getRusName());
        anime.setPosterURL(null);
        anime.setType(this.typeRepository.findById(animeCreateDTO.getTypeId()).orElse(null));
        anime.setGenres(this.genreRepository.findAllById(animeCreateDTO.getGenreIds()));
        anime.setStatus(this.statusRepository.findById(animeCreateDTO.getStatusId()).orElse(null));
        anime.setEpisodeCount(animeCreateDTO.getEpisodeCount());
        anime.setEpisodeDuration(animeCreateDTO.getEpisodeDuration());
        anime.setLinks(animeCreateDTO.getLinkList().stream().map(this.linkService::createNewLink).toList());
        anime.setMarks(this.markRepository.findAllById(animeCreateDTO.getMarkIds()));
        anime.setDescription(animeCreateDTO.getDescription());
        List<LocalDate> period = animeCreateDTO.getPeriod();
        if (period.isEmpty()) {
            anime.setStartDate(null);
            anime.setEndDate(null);
        }
        else {
            anime.setStartDate(period.get(0));
            anime.setEndDate(period.size() > 1 ? period.get(1) : null);
        }

        anime.setRomajiName(animeCreateDTO.getRomName());
        anime.setStudios(this.studioRepository.findAllById(animeCreateDTO.getStudioIds()));
        Long animeId = this.animeRepository.save(anime).getId();

        Image posterImage = animeCreateDTO.getPoster();
        String directoryPath = Paths.get("").toAbsolutePath() + "/frontend/src/public/poster/anime/" + animeId + "/";
        String formatName = Utils.getFileExtension(posterImage.getFileName());
        String fileName = "main-" + Utils.generateRandomString() + "." + formatName;
        Files.createDirectories(Paths.get(directoryPath));

        byte[] fileBytes = java.util.Base64.getDecoder().decode(posterImage.getBase64Image());
        String fullPath = directoryPath + fileName;
        String posterURL = Utils.convertToRelativePath(fullPath);
        Files.write(Paths.get(fullPath), Utils.resizeImage(fileBytes, formatName, 700));

        this.animeRepository.updatePoster(posterURL, animeId);
        return animeId;
    }

    public List<AnimeGetDTO> getAll() {
        return this.animeRepository.findAll().stream()
                .map(AnimeGetDTO::new)
                .toList();
    }

    public Anime getById(Long id) {
        return this.animeRepository.findById(id).orElse(null);
    }
}
