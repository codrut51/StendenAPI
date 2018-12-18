package api.stenden.data.model;

import api.stenden.data.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="images")
public class ImageAnnotated implements Image {

    @Id
    @Column(name="IID")
    Long id;
    @Column(name="Path")
    String path;
}
