package quiz.anime.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import quiz.anime.Anime;
import quiz.anime.AnimeCreateDTO;
import quiz.anime.AnimeGetDTO;
import quiz.anime.AnimeGetShortDTO;
import quiz.anime.repository.AnimeRepository;
import quiz.genres.Genre;
import quiz.genres.GenreRepository;
import quiz.links.Link;
import quiz.links.LinkService;
import quiz.marks.MarkRepository;
import quiz.status.Status;
import quiz.status.StatusRepository;
import quiz.studios.Studio;
import quiz.studios.StudioRepository;
import quiz.types.Type;
import quiz.types.TypeRepository;
import quiz.utils.Utils;
import quiz.utils.model.Image;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
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

    private static final Logger log = LoggerFactory.getLogger(AnimeService.class);

    public Long createAnime(AnimeCreateDTO animeCreateDTO) throws IOException {
        try {
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
            setPoster(animeCreateDTO.getPoster(), animeId, null);
            return animeId;
        }
        catch (Exception e) {
            log.error("Ошибка при сохранении Аниме: {}", e.getMessage());
            return null;
        }
    }

    public List<AnimeGetDTO> getAllAnimes() {
        return this.animeRepository.findAll().stream()
                .map(AnimeGetDTO::new)
                .toList();
    }

    public List<AnimeGetShortDTO> getAllAnimesShort() {
        return this.animeRepository.findAll().stream()
                .map(AnimeGetShortDTO::new)
                .toList();
    }

    public Anime getAnimeById(Long id) {
        return this.animeRepository.findById(id).orElse(null);
    }

    public void updateAnime(Long id, AnimeCreateDTO anime) throws IOException {
        Anime currentAnime = this.animeRepository.findById(id).orElse(null);
        if (currentAnime != null) {
            currentAnime.setRuName(anime.getRusName());
            currentAnime.setRomajiName(anime.getRomName());
            currentAnime.setEpisodeCount(anime.getEpisodeCount());
            currentAnime.setEpisodeDuration(anime.getEpisodeDuration());
            List<LocalDate> period = anime.getPeriod();
            if (!period.isEmpty()) {
                currentAnime.setStartDate(period.get(0));
                if (period.size() > 1) {
                    currentAnime.setEndDate(period.get(1));
                }
            }
            currentAnime.setDescription(anime.getDescription());

            // Обновление Типа
            if (anime.getTypeId() != null) {
                Type type = this.typeRepository.findById(anime.getTypeId())
                        .orElseThrow(() -> new EntityNotFoundException("Тип с ИД = " + anime.getTypeId() + " не найден."));
                currentAnime.setType(type);
            }

            // Обновление Статуса
            if (anime.getStatusId() != null) {
                Status status = this.statusRepository.findById(anime.getStatusId())
                        .orElseThrow(() -> new EntityNotFoundException("Статус с ИД = " + anime.getTypeId() + " не найден."));
                currentAnime.setStatus(status);
            }

            // Обновление жанров через массив ID
            if (anime.getGenreIds() != null && !anime.getGenreIds().isEmpty()) {
                List<Genre> genres = genreRepository.findAllById(anime.getGenreIds());
                if (genres.size() != anime.getGenreIds().size()) {
                    throw new EntityNotFoundException("Количество жанров не совпадает.");
                }
                currentAnime.setGenres(genres);
            } else {
                currentAnime.setGenres(Collections.emptyList());
            }

            // Обновление студий через массив ID
            if (anime.getStudioIds() != null && !anime.getStudioIds().isEmpty()) {
                List<Studio> studios = studioRepository.findAllById(anime.getStudioIds());
                if (studios.size() != anime.getStudioIds().size()) {
                    throw new EntityNotFoundException("Количество студий не совпадает.");
                }
                currentAnime.setStudios(studios);
            } else {
                currentAnime.setStudios(Collections.emptyList());
            }

            // Обработка коллекции ссылок (linkList)
            List<Link> currentLinks = currentAnime.getLinks();
            List<Link> newLinks = anime.getLinkList();

            // Удаляем лишние связи из промежуточной таблицы
            List<Link> linksToRemove = currentLinks.stream()
                    .filter(existingLink -> newLinks.stream().noneMatch(updatedLink ->
                            updatedLink.getId() != null && updatedLink.getId().equals(existingLink.getId())))
                    .toList();

            currentAnime.getLinks().removeAll(linksToRemove);

            // Обновляем существующие ссылки или добавляем новые
            for (Link updatedLink : newLinks) {
                if (updatedLink.getId() != null) {
                    // Обновляем существующую ссылку
                    currentLinks.stream()
                            .filter(existingLink -> updatedLink.getId().equals(existingLink.getId()))
                            .findFirst()
                            .ifPresent(existingLink -> {
                                existingLink.setName(updatedLink.getName());
                                existingLink.setUrl(updatedLink.getUrl());
                            });
                } else {
                    currentAnime.getLinks().add(this.linkService.createNewLink(updatedLink));
                }
            }

            // Сохраняем обновленный объект Anime
            animeRepository.save(currentAnime);
            setPoster(anime.getPoster(), currentAnime.getId(), currentAnime.getPosterURL());

            // Удаляем ссылки из базы данных, которые больше не используются
            this.linkService.deleteAll(linksToRemove);
        }
    }

    public void deleteAnime(Long id) {
    }

    private void setPoster(Image poster, Long animeId, String previousPosterUrl) throws IOException {
        try {
            if (poster != null) {
                Path absolutePath = Paths.get("").toAbsolutePath();
                if (previousPosterUrl != null) {
                    Files.deleteIfExists(Paths.get(absolutePath + "/frontend/" + previousPosterUrl));
                }
                String directoryPath = absolutePath + "/frontend/src/public/images/poster/anime/" + animeId + "/";
                String formatName = Utils.getFileExtension(poster.getFileName());
                String fileName = "poster_" + Utils.generateRandomString() + "." + formatName;
                Files.createDirectories(Paths.get(directoryPath));

                byte[] fileBytes = java.util.Base64.getDecoder().decode(poster.getBase64Image());
                String fullPath = directoryPath + fileName;
                String posterURL = Utils.convertToRelativePath(fullPath);
                Files.write(Paths.get(fullPath), Utils.resizeImage(fileBytes, formatName, 700));

                this.animeRepository.updatePoster(posterURL, animeId);
            }
        }
        catch (Exception e) {
            log.info("Ошибка при установке постера: {}", e.getMessage());
        }

    }
}
