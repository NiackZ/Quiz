package nekotaku.anime;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnimeSpecification {
    public static Specification<Anime> search(String title) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null) {
                System.out.println("Title: " + title);
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

