package org.met.labweb.challenges.service;


import org.met.labweb.challenges.model.Player;

import java.util.List;

public class MatchService {
    PlayerService playerService= new PlayerService();

    public String getWelcomeMsg(){
        return "****************************************************\n" +
                "***Te damos la bienvenida al JUEGO DE DOS O TRES!***\n" +
                "****************************************************";
    }

    public String getRulesMsg(){
        return "Reglas del juego:\n" +
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
                "    Gana quien más puntaje haya obtenido.\n" +
                "----------------------------------------------\n";
    }

    public String getWinnerAndScoresMessage(List<Player> players){
        String message="\n********************FIN DEL JUEGO*******************";

        List<Player> winners=playerService.getWinners(players);

        message= winners.size()==1
                    ? message.concat("\n************ "+winners.get(0).getName()+" GANASTE!!! ************")
                    : winners.size()>1
                        ? message.concat(getTiedPlayersNames(winners))
                        : message.concat("\nHay algun problema :( no pudimos detectar quién ganó");

        return message.concat("\n****************************************************\n")
                .concat(getPlayersData(players));
    }

    public int validateInputAndGetTotalPlayers(String input){
        try{
            int totalPlayers=Integer.parseInt(input);
            if(totalPlayers<2){
                return 2;
            }else{
                return totalPlayers;
            }
        }catch (Exception e){
            return  2;
        }
    }

    private String getTiedPlayersNames(List<Player> players){
        String[] tiedPlayersNames ={""};
        players.forEach(x-> tiedPlayersNames[0]=tiedPlayersNames[0].concat(x.getName()+" - "));
        return "\nTenemos un empate entre: ".concat(tiedPlayersNames[0]);
    }

    private String getPlayersData(List<Player> players){
        String[] playersData ={""};
        players.forEach(x->playersData[0]=playersData[0].concat(x.toString()+"\n"));
        return playersData[0];
    }
}
