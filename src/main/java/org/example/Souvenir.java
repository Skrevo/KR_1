package org.example;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Souvenir {

    private String name;

    private Manufacture manufacture;

    private String dateOfProduce;
    private double price;

    @Override
    public String toString() {
        return "Souvenir{" +
                "name='" + name + '\'' +
                ", manufacture=" + manufacture +
                ", dateOfProduce='" + dateOfProduce + '\'' +
                ", price=" + price +
                '}';
    }
}
