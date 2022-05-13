package org.met.labweb.challenges.service;

import org.met.labweb.challenges.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class RuleService {
    public boolean allDicesAreEqual(List<Dice> dices){
        return dices.stream().allMatch(dice->dice.getCurrentValue().equals(dices.get(0).getCurrentValue()));
    }
    public boolean allDiceAreDifferent(List<Dice> dices){
        List<Integer> currentValues=dices.stream().map(Dice::getCurrentValue).distinct().collect(Collectors.toList());
        return currentValues.size() == dices.size();
    }
    public boolean totalOfDicesParityIsEqualToBet(List<Dice> dices, String bet){
        boolean isEven = dices.stream().map(Dice::getCurrentValue).reduce(Integer::sum).get() % 2 == 0;
        return bet.equalsIgnoreCase("PAR") == isEven;
    }
    public boolean allDicesParityIsEqualToBet(List<Dice> dices, String bet){

        if(bet.equalsIgnoreCase("PAR")){
            return dices.stream().allMatch(d->d.getCurrentValue()%2==0);
        }else{
            return dices.stream().allMatch(d->d.getCurrentValue()%2!=0);
        }
    }

}
