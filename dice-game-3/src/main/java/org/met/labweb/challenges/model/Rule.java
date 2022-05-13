package org.met.labweb.challenges.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rule {
    allDicesAreEqual("\n---> Los 3 son iguales! sumas 6 puntos ;)",6),
    allDicesAreDifferent("\n---> Los 3 son diferentes, no sumas puntos :(",0),
    oneDiceIsDifferent("\n---> Solo 2 dados son iguales, sumas 3 puntos",3),
    totalOfDicesParityIsEqualToBet("\n---> Acertaste la apuesta! sumas el valor del dado más alto",null),
    allDicesParityIsEqualToBet("\n---> BONUS! tu apuesta coincide con cada dado, duplicas tu puntaje ;)",2),
    totalOfDicesParityIsDifferentToBet("\n---> No acertaste la apuesta :( perdés puntos",null);

    public String message;
    public Integer value;
}
