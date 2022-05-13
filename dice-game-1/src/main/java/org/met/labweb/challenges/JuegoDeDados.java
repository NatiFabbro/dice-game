package org.met.labweb.challenges;


import lombok.AllArgsConstructor;
import org.met.labweb.challenges.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class JuegoDeDados {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Player> players=new ArrayList<>();

        System.out.println("****************************************************");
        System.out.println("***                                              ***");
        System.out.println("***Te damos la bienvenida al JUEGO DE DOS O TRES!***");
        System.out.println("***                                              ***");
        System.out.println("****************************************************");


        System.out.println("Querés ver las reglas del juego? (Y|N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
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

        System.out.println("Cuántas personas van a jugar? (si no indicas algo vàlido, o ingresas un número menor a 2 supondremos que son 2 personas):");
        int totalPlayers;
        try{
            totalPlayers=Integer.parseInt(scanner.nextLine());
            if(totalPlayers<2){
                totalPlayers=2;
            }
        }catch (Exception e){
            totalPlayers=2;
        }
        System.out.println("*** Vamos a armar la partida para "+totalPlayers+" personas ***");

        IntStream.rangeClosed(1,totalPlayers).forEach(x-> players.add(Player.builder().id(x).score(0).build()));

        players.forEach(p->{
            System.out.println("Ingresá el nombre de player "+p.getId()+": ");
            p.setName(scanner.nextLine().toUpperCase());
        });

        System.out.println("****************************************************");
        System.out.println("***************** ROUND 1. FIGHT!! *****************");
        System.out.println("****************************************************");
        players.forEach(player->{
            System.out.println("\n*** Es el turno de "+player.getName()+" ***");

            List<Dice> dices = Arrays.asList(Dice.builder().id(1).build(), Dice.builder().id(2).build(), Dice.builder().id(3).build());
            dices.forEach(JuegoDeDados::rollDice);

            if (allDicesAreEqual(dices)){
                System.out.println("---> Los 3 son iguales! sumas 6 puntos ;)");
                addPoints(player,6);
                System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");

            } else if (allDiceAreDifferent(dices)){
                System.out.println("---> Los 3 son diferentes, no sumas puntos :(");
                System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");

            } else {
                System.out.println("---> Uno de los dados es diferente, volvemos a lanzarlo");
                rollDice(dices.stream().filter(d->d.getId().equals(getDifferentDiceId(dices))).findAny().get());

                if (allDicesAreEqual(dices)){
                    System.out.println("---> Los 3 son iguales! sumas 6 puntos ;)");
                    addPoints(player,6);
                    System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");

                } else {
                    System.out.println("---> Solo 2 dados son iguales, sumas 3 puntos");
                    addPoints(player,3);
                    System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");
                }
            }
        });

        players.forEach(player->{
            System.out.println(player.getName()+" ingresá la apuesta (PAR|IMPAR si ingresas otra cosa vamos a apostar PAR por vos): ");
            String bet=scanner.nextLine();
            player.setBet(bet.equalsIgnoreCase("IMPAR") ? bet : "PAR");
        });

        System.out.println("****************************************************");
        System.out.println("************** ROUND 2. FINISH THEM!! **************");
        System.out.println("****************************************************");
        players.forEach(player->{
            System.out.println("\n*** Es el turno de "+player.getName()+" ***\n---> Tu apuesta es " + player.getBet());

            List<Dice> dices = Arrays.asList(Dice.builder().id(1).build(), Dice.builder().id(2).build(), Dice.builder().id(3).build());
            dices.forEach(JuegoDeDados::rollDice);

            if (totalOfDicesParityIsEqualToBet(dices, player.getBet())){
                System.out.println("---> Acertaste la apuesta!");
                addPoints(player,getMaxDiceValue(dices));
                System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");

                if(allDicesParityIsEqualToBet(dices,player.getBet())){
                    System.out.println("---> BONUS! tu apuesta coincide con cada dado, duplicas tu puntaje ;)");
                    multiplyPoints(player,2);
                    System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");
                }
            }else{
                System.out.println("---> No acertaste la apuesta :(");
                substractPoints(player,getMinDiceValue(dices));
                System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");
            }
        });

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

    public static void addPoints(Player player, int points){
        player.setScore(player.getScore()+points);
    }
    public static void substractPoints(Player player, int points){
        player.setScore(Math.max(player.getScore()-points,0));
    }
    public static void multiplyPoints(Player player, int multiplayer){
        player.setScore(player.getScore()*multiplayer);
    }

    public static boolean allDicesAreEqual(List<Dice> dices){
        return dices.stream().allMatch(dice->dice.getCurrentValue().equals(dices.get(0).getCurrentValue()));
    }
    public static boolean allDiceAreDifferent(List<Dice> dices){
        List<Integer> currentValues=dices.stream().map(Dice::getCurrentValue).distinct().collect(Collectors.toList());
        return currentValues.size() == dices.size();
    }
    public static boolean totalOfDicesParityIsEqualToBet(List<Dice> dices, String bet){
        boolean isEven = dices.stream().map(Dice::getCurrentValue).reduce(Integer::sum).get() % 2 == 0;
        return bet.equalsIgnoreCase("PAR") == isEven;
    }
    public static boolean allDicesParityIsEqualToBet(List<Dice> dices, String bet){

        if(bet.equalsIgnoreCase("PAR")){
            return dices.stream().allMatch(d->d.getCurrentValue()%2==0);
        }else{
            return dices.stream().allMatch(d->d.getCurrentValue()%2!=0);
        }
    }

    public static void rollDice(Dice dice){
        dice.setCurrentValue((int)Math.floor(Math.random() * 6) + 1);
        System.out.println(dice);
    }

    public static int getDifferentDiceId(List<Dice> dices){
        int differentDiceId[]={0};
        dices.forEach(dice -> {
            List<Dice> filteredDices= dices.stream()
                    .filter(d->!d.getCurrentValue().equals(dice.getCurrentValue()))
                    .collect(Collectors.toList());

            if (filteredDices.size()==dices.size()-1){
                differentDiceId[0]=dice.getId();
            }
        });
        return differentDiceId[0];
    }
    public static int getMaxDiceValue(List<Dice> dices){
        return dices.stream().map(Dice::getCurrentValue).max(Comparator.naturalOrder()).get();
    }
    public static int getMinDiceValue(List<Dice> dices){
        return dices.stream().map(Dice::getCurrentValue).min(Comparator.naturalOrder()).get();
    }
}
