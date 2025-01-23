package nekotaku.anime.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import nekotaku.anime.Anime;
import nekotaku.anime.AnimeCreateDTO;
import nekotaku.anime.AnimeGetDTO;
import nekotaku.anime.AnimeGetShortDTO;
import nekotaku.anime.repository.AnimeRepository;
import nekotaku.genres.Genre;
import nekotaku.genres.GenreRepository;
import nekotaku.links.Link;
import nekotaku.links.LinkService;
import nekotaku.marks.MarkRepository;
import nekotaku.status.Status;
import nekotaku.status.StatusRepository;
import nekotaku.studios.Studio;
import nekotaku.studios.StudioRepository;
import nekotaku.types.Type;
import nekotaku.types.TypeRepository;
import nekotaku.utils.Utils;
import nekotaku.utils.model.UpdateImage;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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
            UpdateImage poster = Utils.setPoster(animeCreateDTO.getPoster(), animeId, null, "/images/poster/anime/");
            this.animeRepository.updatePoster(poster.getURL(), poster.getId());
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
            UpdateImage poster = Utils.setPoster(anime.getPoster(), currentAnime.getId(), currentAnime.getPosterURL(), "/images/poster/anime/");
            this.animeRepository.updatePoster(poster.getURL(), poster.getId());

            // Удаляем ссылки из базы данных, которые больше не используются
            this.linkService.deleteAll(linksToRemove);
        }
    }

    public void deleteAnime(Long id) {
    }

}
