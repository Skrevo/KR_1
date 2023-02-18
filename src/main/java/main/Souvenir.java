package main;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Souvenir {

    private String name;

    private Manufacture manufacture;

    private String dateOfProduce;
    private double price;
}
