package org.met.labweb.challenges.service;


import org.met.labweb.challenges.model.Dice;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DiceService {

    public List<Dice> getDices(){
        return Arrays.asList(Dice.builder().id(1).build(), Dice.builder().id(2).build(), Dice.builder().id(3).build());
    }

    public int getDiceNewValue(){
        return (int)Math.floor(Math.random() * 6) + 1;
    }
    public void rollDices(List<Dice> dices){
        dices.forEach(this::rollDice);
    }
    public void rollDice(Dice dice){
        dice.setCurrentValue(getDiceNewValue());
        System.out.println(dice);
    }

    public int getDifferentDiceId(List<Dice> dices){
        int differentDiceId[]={0};
        dices.forEach(dice -> {
            List<Dice> filteredDices= dices.stream()
                    .filter(d->!d.getCurrentValue().equals(dice.getCurrentValue()))
                    .collect(Collectors.toList());

            if (filteredDices.size()==dices.size()-1){
                differentDiceId[0]=dice.getId();
            }
        });
        return differentDiceId[0];
    }
    public int getMaxDiceValue(List<Dice> dices){
        return dices.stream().map(Dice::getCurrentValue).max(Comparator.naturalOrder()).get();
    }
    public int getMinDiceValue(List<Dice> dices){
        return dices.stream().map(Dice::getCurrentValue).min(Comparator.naturalOrder()).get();
    }
}
