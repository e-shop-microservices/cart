package ojles.coursework.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private long id;
    private String name;
    private String imagePath;
    private long price;
}
