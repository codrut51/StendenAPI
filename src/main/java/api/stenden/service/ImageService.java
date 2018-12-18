package api.stenden.service;

import api.stenden.data.Image;
import api.stenden.data.ImageRepository;
import api.stenden.data.jpa.ImageJpa;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class ImageService implements ImageRepository {

    private ImageJpa imageJpa;
//    @Autowired
    public ImageService(ImageJpa imageJpa)
    {
        this.imageJpa = imageJpa;
    }
    @Override
    @Transactional
    public Image getById(Long id) {
        return imageJpa.getById(id);
    }

    @Override
    @Transactional
    public List<Image> getImages() {
        return imageJpa.getImages();
    }
}
