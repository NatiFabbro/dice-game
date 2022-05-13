package org.met.labweb.challenges.service;

import org.met.labweb.challenges.model.*;
import lombok.AllArgsConstructor;


import java.util.List;

@AllArgsConstructor
public class RoundService {

    DiceService diceService;
    RuleService ruleService;
    PlayerService playerService;

    public String playRoundAndGetMsg(Round round, List<Player> players){
        String[] message = {round.getMessage()};
        switch (round){
            case ONE:
                players.forEach(x-> message[0] = message[0].concat(playRoundOne(x)));
                break;
            case TWO:
                players.forEach(x-> message[0] = message[0].concat(playRoundTwo(x)));
                break;
        }

        return message[0];
    }

    public String playRoundOne(Player player){
        String message="\n*** Es el turno de "+player.getName()+" ***";

        List<Dice> dices = diceService.getDices();
        diceService.rollDices(dices);
        message=message.concat(diceService.getDicesValues(dices));

        if (ruleService.allDicesAreEqual(dices)){
            message=enrichMessageAndApplyScore(message,Rule.allDicesAreEqual.getMessage(), player,Operation.ADD,Rule.allDicesAreEqual.getValue());
        } else if (ruleService.allDiceAreDifferent(dices)){
            message=enrichMessageAndApplyScore(message,Rule.allDicesAreDifferent.getMessage(), player,Operation.ADD,Rule.allDicesAreDifferent.getValue());
        } else {
            message=message.concat("\n---> Uno de los dados es diferente, volvemos a lanzarlo");
            diceService.rollDice(dices.stream().filter(d->d.getId().equals(diceService.getDifferentDiceId(dices))).findAny().get());
            message=message.concat(diceService.getDicesValues(dices));

            if (ruleService.allDicesAreEqual(dices)){
                message=enrichMessageAndApplyScore(message,Rule.allDicesAreEqual.getMessage(), player,Operation.ADD,Rule.allDicesAreEqual.getValue());
            } else {
                message=enrichMessageAndApplyScore(message,Rule.oneDiceIsDifferent.getMessage(), player,Operation.ADD,Rule.oneDiceIsDifferent.getValue());
            }
        }
        return message;
    }

    public String playRoundTwo(Player player){
        String message="\n*** Es el turno de "+player.getName()+" ***\n---> Tu apuesta es " + player.getBet();

        List<Dice> dices = diceService.getDices();
        diceService.rollDices(dices);
        message=message.concat(diceService.getDicesValues(dices));

        if (ruleService.totalOfDicesParityIsEqualToBet(dices, player.getBet())){
            message=enrichMessageAndApplyScore(message,Rule.totalOfDicesParityIsEqualToBet.getMessage(),
                    player,Operation.ADD,diceService.getMaxDiceValue(dices));

            if(ruleService.allDicesParityIsEqualToBet(dices,player.getBet())){
                message=enrichMessageAndApplyScore(message,Rule.allDicesParityIsEqualToBet.getMessage(),
                        player,Operation.MULTIPLY,Rule.allDicesParityIsEqualToBet.getValue());
            }
        }else{
            message=enrichMessageAndApplyScore(message,Rule.totalOfDicesParityIsDifferentToBet.getMessage(),
                    player,Operation.SUBSTRACT,diceService.getMinDiceValue(dices));
        }
        return message;
    }

    private String enrichMessageAndApplyScore(String currentMessage, String newInfo,
                                              Player player, Operation operation, int value){
        switch (operation){
            case ADD:
                playerService.addPoints(player,value);
                break;
            case MULTIPLY:
                playerService.multiplyPoints(player,value);
                break;
            case SUBSTRACT:
                playerService.substractPoints(player,value);
                break;
        }
        return currentMessage.concat(newInfo).concat(playerService.getCurrentScore(player));

    }
}
