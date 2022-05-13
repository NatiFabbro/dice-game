package org.met.labweb.challenges.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.met.labweb.challenges.Common;
import org.met.labweb.challenges.model.Dice;
import org.met.labweb.challenges.model.Player;
import org.met.labweb.challenges.service.DiceService;
import org.met.labweb.challenges.service.PlayerService;
import org.met.labweb.challenges.service.RoundService;
import org.met.labweb.challenges.service.RuleService;

import java.util.List;

import static org.mockito.Mockito.*;


public class RoundServiceUnitTest extends Common {

    DiceService diceService=mock(DiceService.class);
    RuleService ruleService=mock(RuleService.class);
    PlayerService playerService=mock(PlayerService.class);

    RoundService roundService= new RoundService(diceService,ruleService,playerService);

    @Test
    public void roundTwo_incorrectBet(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(1,3,3);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        when(diceService.getMinDiceValue(anyList())).thenReturn(dices.get(0).getCurrentValue());

        when(ruleService.totalOfDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(false);

        doNothing().when(playerService).substractPointsAndPrintScore(any(Player.class),anyInt());

        //call method
        roundService.playRoundTwo(player);

        //assert
        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
        verify(diceService,atLeast(1)).getMinDiceValue(anyList());

        verify(ruleService,atLeast(1)).totalOfDicesParityIsEqualToBet(anyList(),anyString());

        verify(playerService,atLeast(1)).substractPointsAndPrintScore(any(Player.class),anyInt());

    }

    @Test
    public void roundTwo_correctBet_noBonus(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(2,1,3);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        when(diceService.getMaxDiceValue(anyList())).thenReturn(dices.get(2).getCurrentValue());

        when(ruleService.totalOfDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(true);
        when(ruleService.allDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(false);

        doNothing().when(playerService).addPointsAndPrintScore(any(Player.class),anyInt());

        //call method
        roundService.playRoundTwo(player);

        //assert
        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
        verify(diceService,atLeast(1)).getMaxDiceValue(anyList());

        verify(ruleService,atLeast(1)).totalOfDicesParityIsEqualToBet(anyList(),anyString());
        verify(ruleService,atLeast(1)).allDicesParityIsEqualToBet(anyList(),anyString());

        verify(playerService,atLeast(1)).addPointsAndPrintScore(any(Player.class),anyInt());

    }

    @Test
    public void roundTwo_correctBet_Bonus(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(2,2,2);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        when(diceService.getMaxDiceValue(anyList())).thenReturn(dices.get(0).getCurrentValue());

        when(ruleService.totalOfDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(true);
        when(ruleService.allDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(true);

        doNothing().when(playerService).addPointsAndPrintScore(any(Player.class),anyInt());
        doNothing().when(playerService).multiplyPointsAndPrintScore(any(Player.class),anyInt());


        //call method
        roundService.playRoundTwo(player);

        //assert
        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
        verify(diceService,atLeast(1)).getMaxDiceValue(anyList());

        verify(ruleService,atLeast(1)).totalOfDicesParityIsEqualToBet(anyList(),anyString());
        verify(ruleService,atLeast(1)).allDicesParityIsEqualToBet(anyList(),anyString());

        verify(playerService,atLeast(1)).addPointsAndPrintScore(any(Player.class),anyInt());
        verify(playerService,atLeast(1)).multiplyPointsAndPrintScore(any(Player.class),anyInt());

    }
}
