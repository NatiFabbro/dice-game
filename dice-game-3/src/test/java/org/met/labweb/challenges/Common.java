package org.met.labweb.challenges;


import org.met.labweb.challenges.model.Dice;
import org.met.labweb.challenges.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Common {

    public List<Dice> getDices(int...values){
        List<Dice> dices=new ArrayList<>();
        Arrays.stream(values).forEach(x->{
            dices.add(Dice.builder().id(1).currentValue(x).build());
        });
        return dices;
    }

    public Player getPlayer(int currentPoints, String bet){
        return Player.builder().id(1).name("Player").score(currentPoints).bet(bet).build();
    }

    public List<Dice> getUnrolledDices(int amount){
        amount=amount>0 ? amount:3;
        List<Dice> dices=new ArrayList<>();

        IntStream.rangeClosed(1,amount).forEach(x-> dices.add(Dice.builder().id(x).currentValue(0).build()));
        return dices;
    }

    public List<Dice> getRolledDices_oneIsDifferent(int amount){
        amount=amount>1 ? amount:3;
        List<Dice> dices=new ArrayList<>();

        IntStream.rangeClosed(1,amount).forEach(x-> dices.add(Dice.builder().id(x).currentValue(1).build()));
        dices.get(0).setCurrentValue(2);
        return dices;
    }
}
