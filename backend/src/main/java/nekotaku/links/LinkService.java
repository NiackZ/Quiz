package nekotaku.links;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    public Link createNewLink(Link link) {
        return this.linkRepository.save(link);
    }

    public void deleteAll(List<Link> links) {
        this.linkRepository.deleteAll(links);
    }

}
