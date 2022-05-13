package org.met.labweb.challenges.unit;

import org.junit.jupiter.api.Test;
import org.met.labweb.challenges.Common;
import org.met.labweb.challenges.model.Dice;
import org.met.labweb.challenges.model.Player;
import org.met.labweb.challenges.model.Rule;
import org.met.labweb.challenges.service.DiceService;
import org.met.labweb.challenges.service.PlayerService;
import org.met.labweb.challenges.service.RoundService;
import org.met.labweb.challenges.service.RuleService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        when(diceService.getDicesValues(anyList())).thenReturn("1,3,3");

        when(ruleService.totalOfDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(false);

        doNothing().when(playerService).substractPoints(any(Player.class),anyInt());
        when(playerService.getCurrentScore(any(Player.class))).thenReturn("2");


        //call method
        String result=roundService.playRoundTwo(player);

        //assert

        assertTrue(result.contains(Rule.totalOfDicesParityIsDifferentToBet.getMessage()));

        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
        verify(diceService,atLeast(1)).getMinDiceValue(anyList());
        verify(diceService,atLeast(1)).getDicesValues(anyList());

        verify(ruleService,atLeast(1)).totalOfDicesParityIsEqualToBet(anyList(),anyString());

        verify(playerService,atLeast(1)).substractPoints(any(Player.class),anyInt());
        verify(playerService,atLeast(1)).getCurrentScore(any(Player.class));

    }

    @Test
    public void roundTwo_correctBet_noBonus(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(2,1,3);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        when(diceService.getMaxDiceValue(anyList())).thenReturn(dices.get(2).getCurrentValue());
        when(diceService.getDicesValues(anyList())).thenReturn("2,1,3");


        when(ruleService.totalOfDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(true);
        when(ruleService.allDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(false);

        doNothing().when(playerService).addPoints(any(Player.class),anyInt());
        when(playerService.getCurrentScore(any(Player.class))).thenReturn("6");


        //call method
        String result=roundService.playRoundTwo(player);

        //assert

        assertTrue(result.contains(Rule.totalOfDicesParityIsEqualToBet.getMessage()));

        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
        verify(diceService,atLeast(1)).getMaxDiceValue(anyList());
        verify(diceService,atLeast(1)).getDicesValues(anyList());

        verify(ruleService,atLeast(1)).totalOfDicesParityIsEqualToBet(anyList(),anyString());
        verify(ruleService,atLeast(1)).allDicesParityIsEqualToBet(anyList(),anyString());

        verify(playerService,atLeast(1)).addPoints(any(Player.class),anyInt());
        verify(playerService,atLeast(1)).getCurrentScore(any(Player.class));

    }

    @Test
    public void roundTwo_correctBet_Bonus(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(2,2,2);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        when(diceService.getMaxDiceValue(anyList())).thenReturn(dices.get(0).getCurrentValue());
        when(diceService.getDicesValues(anyList())).thenReturn("2,2,2");

        when(ruleService.totalOfDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(true);
        when(ruleService.allDicesParityIsEqualToBet(anyList(),anyString())).thenReturn(true);

        doNothing().when(playerService).addPoints(any(Player.class),anyInt());
        doNothing().when(playerService).multiplyPoints(any(Player.class),anyInt());
        when(playerService.getCurrentScore(any(Player.class))).thenReturn("10");


        //call method
        String result=roundService.playRoundTwo(player);

        //assert
        assertTrue(result.contains(Rule.totalOfDicesParityIsEqualToBet.getMessage()));
        assertTrue(result.contains(Rule.allDicesParityIsEqualToBet.getMessage()));


        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
        verify(diceService,atLeast(1)).getMaxDiceValue(anyList());
        verify(diceService,atLeast(1)).getDicesValues(anyList());


        verify(ruleService,atLeast(1)).totalOfDicesParityIsEqualToBet(anyList(),anyString());
        verify(ruleService,atLeast(1)).allDicesParityIsEqualToBet(anyList(),anyString());

        verify(playerService,atLeast(1)).addPoints(any(Player.class),anyInt());
        verify(playerService,atLeast(1)).multiplyPoints(any(Player.class),anyInt());
        verify(playerService,atLeast(1)).getCurrentScore(any(Player.class));


    }
}
