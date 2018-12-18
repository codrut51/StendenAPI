package api.stenden.res;

import api.stenden.data.Image;
import api.stenden.data.jpa.ImageJpa;
import api.stenden.lib.DatabaseConfig;
import api.stenden.service.ImageService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// We're telling Spring MVC that this is a REST controller
// This treats methods with @RequestMapping as if it had the @ResponseBody annotation
// which tells Spring that the return value should be transformed into a response
@RestController
@RequestMapping("/images")
public class LaptopController {

    private ImageService imageService;
//
////    @Autowired
//    public LaptopController(ImageService imageService)
//    {
//        this.imageService = imageService;
//    }

    @GetMapping
    public Image getLaptops() {
        DatabaseConfig db = new DatabaseConfig();
//        System.out.println(db.dataSource());
//        System.out.println(db.sessionFactory(db.dataSource()).getObject());
        imageService = new ImageService(new ImageJpa());
//         return laptopService.getJpaRepository();
//        return laptops;
        return imageService.getById(1L);
    }
}
