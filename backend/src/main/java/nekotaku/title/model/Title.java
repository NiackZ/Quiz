package nekotaku.title.model;

import lombok.Getter;
import lombok.Setter;
import nekotaku.status.Status;
import nekotaku.types.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    private Integer episodeCount;
    private Integer episodeDuration;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(length = 2000)
    private String description;
}
