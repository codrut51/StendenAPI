package api.stenden.data.model;

import api.stenden.data.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePOJO implements Image {
    Long id;
    String path;
}
