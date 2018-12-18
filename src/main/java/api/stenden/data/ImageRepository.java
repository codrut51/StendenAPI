package api.stenden.data;

import java.util.List;

public interface ImageRepository {
    Image getById(Long id);
    List<Image> getImages();
}
