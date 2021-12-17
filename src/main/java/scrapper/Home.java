package scrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Home {
    private String url;
    private int price;
    private int bedCount;
    private double bathCount;
    private int garageCount;
}