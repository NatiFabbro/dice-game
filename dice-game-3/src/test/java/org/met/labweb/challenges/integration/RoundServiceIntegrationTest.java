package org.met.labweb.challenges.integration;

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


public class RoundServiceIntegrationTest extends Common {

    DiceService diceService=spy(DiceService.class);
    RuleService ruleService=new RuleService();
    PlayerService playerService=new PlayerService();

    RoundService roundService= new RoundService(diceService,ruleService,playerService);

    @Test
    public void roundTwo_incorrectBet(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(1,3,3);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        doCallRealMethod().when(diceService).getMaxDiceValue(anyList());

        //call method
        String result=roundService.playRoundTwo(player);

        //assert
        assertEquals(2, player.getScore(),"roundTwo_incorrectBet - score");
        assertTrue(result.contains(Rule.totalOfDicesParityIsDifferentToBet.getMessage()));

        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
    }

    @Test
    public void roundTwo_correctBet_noBonus(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(2,1,3);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        doCallRealMethod().when(diceService).getMaxDiceValue(anyList());

        //call method
        String result=roundService.playRoundTwo(player);

        //assert

        assertEquals(6, player.getScore(),"roundTwo_correctBet_noBonus - score");
        assertTrue(result.contains(Rule.totalOfDicesParityIsEqualToBet.getMessage()));

        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
    }

    @Test
    public void roundTwo_correctBet_Bonus(){
        //prepare and mock
        Player player=getPlayer(3,"PAR");
        List<Dice> dices=getDices(2,2,2);

        when(diceService.getDices()).thenReturn(dices);
        doNothing().when(diceService).rollDices(anyList());
        doCallRealMethod().when(diceService).getMaxDiceValue(anyList());

        //call method
       String result= roundService.playRoundTwo(player);

        //assert

        assertEquals(10, player.getScore(),"roundTwo_correctBet_noBonus - score");
        assertTrue(result.contains(Rule.totalOfDicesParityIsEqualToBet.getMessage()));
        assertTrue(result.contains(Rule.allDicesParityIsEqualToBet.getMessage()));

        verify(diceService,atLeast(1)).getDices();
        verify(diceService,atLeast(1)).rollDices(anyList());
    }
}
