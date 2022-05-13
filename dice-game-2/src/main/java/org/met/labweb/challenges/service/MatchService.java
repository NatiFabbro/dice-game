package org.met.labweb.challenges.service;



import org.met.labweb.challenges.model.Player;

import java.util.List;
import java.util.stream.Collectors;

public class MatchService {
    public void welcome(){
        System.out.println("****************************************************");
        System.out.println("***                                              ***");
        System.out.println("***Te damos la bienvenida al JUEGO DE DOS O TRES!***");
        System.out.println("***                                              ***");
        System.out.println("****************************************************");
    }

    public void printRules(){
        System.out.println("Reglas del juego:\n" +
                "\n" +
                "---Reglas de la Primera Ronda---\n" +
                "1. El primer jugador lanza los 3 dados. \n" +
                "    a) Si los tres dados fueran iguales suma 6 puntos.\n" +
                "    b) Si dos dados son iguales y uno es distinto, entonces " +
                "vuelve a tirar, pero únicamente el dado distinto. Si al volver " +
                "a lanzar ese dado logra que los tres dados sean iguales, entonces sumará " +
                "los 6 puntos en esa ronda. Si el dado relanzado sigue siendo distinto a los " +
                "dos que eran iguales, sumará 3 puntos en esa ronda. \n" +
                "    c) Si los tres dados fueran todos distintos, entonces obtiene 0 puntos " +
                "y no puede volver a tirar ningún dado en esa ronda.\n" +
                "2. Se repite el paso 1 para las demás personas\n" +
                "\n" +
                "---Reglas de la Segunda Ronda---\n" +
                "1. El primer jugador vuelve a lanzar los 3 dados y se considera que apuesta el " +
                "puntaje de la ronda anterior a par/impar (el programa debe pedir que el jugador " +
                "elija si apuesta por par o impar). \n" +
                "    a) Si la suma de los tres dados en esta segunda jugada es de la paridad elegida, " +
                "entonces suma el dado de mayor valor a su puntaje de la ronda anterior;\n" +
                "en caso contrario, resta el dado de menor valor a su puntaje anterior.\n" +
                "    b) Si efectivamente la suma en la segunda jugada es de la paridad elegida, " +
                "entonces el programa debe controlar si además los tres dados fueron de la paridad elegida, " +
                "y en ese caso, se duplica el puntaje total.\n" +
                "2. Se repite el paso 1 para las demás personas\n" +
                "\n" +
                "---Final del Juego---\n" +
                "    Gana quien más puntaje haya obtenido.\n");

        System.out.println("----------------------------------------------\n");
    }

    public void printWinnerAndScores(List<Player> players){
        System.out.println("\n********************FIN DEL JUEGO*******************\n");
        System.out.println("\n****************************************************");

        int maxScore= players.stream().map(Player::getScore).max(Integer::compareTo).get();
        List<Player> winners=players.stream().filter(x->x.getScore().equals(maxScore)).collect(Collectors.toList());

        if(winners.size()==1){
            System.out.println("******"+winners.get(0).getName()+" GANASTE!!! ******");
        }else if (winners.size()>1){
            System.out.println("\nTenemos un empate entre: ");
            winners.forEach(x-> System.out.print(x.getName()+" - "));
        }else{
            System.out.println("\nHay algun problema :( no pudimos detectar quién ganó");
        }

        System.out.println("\n****************************************************\n");
        players.forEach(System.out::println);
    }

    public int validateInputAndGetTotalPlayers(String input){
        try{
            int totalPlayers=Integer.parseInt(input);
            if(totalPlayers<2){
                System.out.println("*** Al menos debe haber 2 personas jugando ***");
                System.out.println("***     Vamos a armar la partida para 2    ***");
                return 2;
            }else{
                return totalPlayers;
            }
        }catch (Exception e){
            System.out.println("***     No ingresaste una cantidad válida    ***");
            System.out.println("*** Vamos a armar la partida para 2 personas ***");
            return 2;
        }
    }
}
