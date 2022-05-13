package org.met.labweb.challenges.unit;


import org.junit.jupiter.api.Test;
import org.met.labweb.challenges.Common;
import org.met.labweb.challenges.model.Dice;
import org.met.labweb.challenges.service.DiceService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DiceServiceUnitTest extends Common {
    DiceService diceService=new DiceService();

    @Test
    public void rollDices_test(){
        List<Dice> dices=getUnrolledDices(3);

        diceService.rollDices(dices);

        dices.forEach(d-> assertNotEquals(0,d,"rollDices_test dice number "+d.getId()));
    }
    @Test
    public void getDifferentDiceId_test(){
        List<Dice> dices=getRolledDices_oneIsDifferent(3);

        int result= diceService.getDifferentDiceId(dices);

        assertEquals(1,result,"getDifferentDiceId_test result");
    }
    @Test
    public void getMaxDiceValue_test(){
        List<Dice> dices=getRolledDices_oneIsDifferent(3);

        int result= diceService.getMaxDiceValue(dices);

        assertEquals(2,result,"getMaxDiceValue_test result");
    }
    @Test
    public void getMinDiceValue_test(){
        List<Dice> dices=getRolledDices_oneIsDifferent(3);

        int result= diceService.getMinDiceValue(dices);

        assertEquals(1,result,"getMinDiceValue_test result");
    }
}
