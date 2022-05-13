package org.met.labweb.challenges.service;

import org.met.labweb.challenges.model.*;
import lombok.AllArgsConstructor;


import java.util.List;

@AllArgsConstructor
public class RoundService {

    DiceService diceService;
    RuleService ruleService;
    PlayerService playerService;

    public void playRoundOne(List<Player> players){
        System.out.println("****************************************************");
        System.out.println("***************** ROUND 1. FIGHT!! *****************");
        System.out.println("****************************************************");
        players.forEach(this::playRoundOne);
    }
    public void playRoundOne(Player player){
        System.out.println("\n*** Es el turno de "+player.getName()+" ***");

        List<Dice> dices = diceService.getDices();
        diceService.rollDices(dices);

        if (ruleService.allDicesAreEqual(dices)){
            System.out.println("---> Los 3 son iguales! sumas 6 puntos ;)");
            playerService.addPointsAndPrintScore(player,6);

        } else if (ruleService.allDiceAreDifferent(dices)){
            System.out.println("---> Los 3 son diferentes, no sumas puntos :(");
            playerService.printCurrentScore(player);

        } else {
            System.out.println("---> Uno de los dados es diferente, volvemos a lanzarlo");
            diceService.rollDice(dices.stream().filter(d->d.getId().equals(diceService.getDifferentDiceId(dices))).findAny().get());

            if (ruleService.allDicesAreEqual(dices)){
                System.out.println("---> Los 3 son iguales! sumas 6 puntos ;)");
                playerService.addPointsAndPrintScore(player,6);

            } else {
                System.out.println("---> Solo 2 dados son iguales, sumas 3 puntos");
                playerService.addPointsAndPrintScore(player,3);
            }
        }
    }
    public void playRoundTwo(List<Player> players){
        System.out.println("****************************************************");
        System.out.println("************** ROUND 2. FINISH THEM!! **************");
        System.out.println("****************************************************");
        players.forEach(this::playRoundTwo);
    }
    public void playRoundTwo(Player player){
        System.out.println("\n*** Es el turno de "+player.getName()+" ***\n---> Tu apuesta es " + player.getBet());

        List<Dice> dices = diceService.getDices();
        diceService.rollDices(dices);

        if (ruleService.totalOfDicesParityIsEqualToBet(dices, player.getBet())){
            System.out.println("---> Acertaste la apuesta!");
            playerService.addPointsAndPrintScore(player,diceService.getMaxDiceValue(dices));

            if(ruleService.allDicesParityIsEqualToBet(dices,player.getBet())){
                System.out.println("---> BONUS! tu apuesta coincide con cada dado, duplicas tu puntaje ;)");
                playerService.multiplyPointsAndPrintScore(player,2);
            }
        }else{
            System.out.println("---> No acertaste la apuesta :(");
            playerService.substractPointsAndPrintScore(player,diceService.getMinDiceValue(dices));
        }
    }

}
