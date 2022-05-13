package org.met.labweb.challenges.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Dice {
    private Integer id;
    private Integer currentValue;

    @Override
    public String toString() {
        if (currentValue!=null && currentValue!=0){
            return "Dice" + id + ": " + currentValue;
        }else{
            return "El dado no ha sido lanzado";
        }
    }
}
