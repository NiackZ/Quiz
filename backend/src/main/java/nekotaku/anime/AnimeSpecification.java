package nekotaku.anime;

import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnimeSpecification {

    private static final Logger log = LoggerFactory.getLogger(AnimeSpecification.class);

    public static Specification<Anime> search(String title) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null) {
                log.info("Поиск по названию: {}", title);
                Predicate titleEn = cb.like(cb.lower(root.get("enName")), "%" + title + "%");
                Predicate titleRu = cb.like(cb.lower(root.get("ruName")), "%" + title + "%");
                Predicate titleKanji = cb.like(cb.lower(root.get("kanjiName")), "%" + title + "%");
                Predicate titleRomaji = cb.like(cb.lower(root.get("romajiName")), "%" + title + "%");

                predicates.add(cb.or(titleEn, titleKanji, titleRu, titleRomaji));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

