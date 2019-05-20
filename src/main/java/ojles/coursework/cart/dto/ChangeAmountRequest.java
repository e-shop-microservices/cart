package ojles.coursework.cart.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ChangeAmountRequest {
    @PositiveOrZero
    private int newAmount;
}
