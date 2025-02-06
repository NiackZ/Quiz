package nekotaku.utils.updateEntity;

public interface INameDeletedEntity {
    String getName();
    Boolean isDeleted();
    void setName(String name);
    void setDeleted(Boolean deleted);
}
