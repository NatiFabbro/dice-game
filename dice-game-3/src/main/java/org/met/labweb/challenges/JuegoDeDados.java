package org.met.labweb.challenges;


import lombok.AllArgsConstructor;
import org.met.labweb.challenges.model.*;
import org.met.labweb.challenges.service.*;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class JuegoDeDados {

    static MatchService matchService=new MatchService();
    static PlayerService playerService=new PlayerService();
    static DiceService diceService=new DiceService();
    static RuleService ruleService=new RuleService();
    static RoundService roundService=new RoundService(diceService,ruleService,playerService);
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        welcomeAndRules();

        List<Player> players= setUpAndGetPlayers();

        System.out.println(roundService.playRoundAndGetMsg(Round.ONE,players));

        setBets(players);

        System.out.println(roundService.playRoundAndGetMsg(Round.TWO,players));

        System.out.println(matchService.getWinnerAndScoresMessage(players));

    }

    public static void welcomeAndRules(){
        System.out.println(matchService.getWelcomeMsg());

        System.out.println("Querés ver las reglas del juego? (Y|N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            System.out.println(matchService.getRulesMsg());
        }
    }

    public static List<Player> setUpAndGetPlayers(){
        System.out.println("Cuántas personas van a jugar? (si no indicas nada vàlido, supondremos que son 2 personas):");
        int totalPlayers=matchService.validateInputAndGetTotalPlayers(scanner.nextLine());

        System.out.println("***     Vamos a armar la partida para "+totalPlayers+"    ***");
        List<Player> players=playerService.createPlayersList(totalPlayers);

        players.forEach(p->{
            System.out.println("Ingresá el nombre de player "+p.getId()+": ");
            playerService.setPlayerName(p,scanner.nextLine());
        });

        return players;
    }

    public static void setBets(List<Player> players){
        players.forEach(p->{
            System.out.println(p.getName()+" ingresá la apuesta (PAR|IMPAR si ingresas otra cosa vamos a apostar PAR por vos): ");
            playerService.setBet(p,scanner.nextLine());
        });
    }
}
