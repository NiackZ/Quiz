package nekotaku.links;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public List<Link> findAll() {
        return IteratorUtils.toList(this.linkRepository.findAll().iterator());
    }

    public Link createNewLink(Link link) {
        return this.linkRepository.save(link);
    }

    public void deleteAll(List<Link> links) {
        this.linkRepository.deleteAll(links);
    }

}
