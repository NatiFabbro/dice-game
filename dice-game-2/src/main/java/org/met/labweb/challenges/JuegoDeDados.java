package org.met.labweb.challenges;


import lombok.AllArgsConstructor;
import org.met.labweb.challenges.model.Player;
import org.met.labweb.challenges.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

@AllArgsConstructor
public class JuegoDeDados {

    static MatchService matchService=new MatchService();
    static PlayerService playerService=new PlayerService();
    static DiceService diceService=new DiceService();
    static RuleService ruleService=new RuleService();
    static RoundService roundService=new RoundService(diceService,ruleService,playerService);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Player> players=new ArrayList<>();

        matchService.welcome();

        System.out.println("Querés ver las reglas del juego? (Y|N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){ matchService.printRules(); }

        System.out.println("Cuántas personas van a jugar? (si no indicas nada vàlido, supondremos que son 2 personas):");
        int totalPlayers=matchService.validateInputAndGetTotalPlayers(scanner.nextLine());

        IntStream.rangeClosed(1,totalPlayers).forEach(x-> players.add(Player.builder().id(x).score(0).build()));

        players.forEach(p->{
            System.out.println("Ingresá el nombre de player "+p.getId()+": ");
            p.setName(scanner.nextLine().toUpperCase());
        });

        roundService.playRoundOne(players);

        players.forEach(p->{
            System.out.println(p.getName()+" ingresá la apuesta (PAR|IMPAR si ingresas otra cosa vamos a apostar PAR por vos): ");
            playerService.setBet(p,scanner.nextLine());
        });

        roundService.playRoundTwo(players);

        matchService.printWinnerAndScores(players);

    }
}
