package org.met.labweb.challenges.service;


import org.met.labweb.challenges.model.Player;

public class PlayerService {
    public void setBet(Player player, String bet){
        player.setBet(bet.equalsIgnoreCase("IMPAR") ? bet : "PAR");
    }

    public void printCurrentScore(Player player){
        System.out.println("\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n");
    }
    public void addPoints(Player player, int points){
        player.setScore(player.getScore()+points);
    }
    public void substractPoints(Player player, int points){
        player.setScore(Math.max(player.getScore()-points,0));
    }
    public void multiplyPoints(Player player, int multiplayer){
        player.setScore(player.getScore()*multiplayer);
    }
    public void addPointsAndPrintScore(Player player, int points){
        addPoints(player,points);
        printCurrentScore(player);
    }
    public void substractPointsAndPrintScore(Player player, int points){
        substractPoints(player,points);
        printCurrentScore(player);
    }
    public void multiplyPointsAndPrintScore(Player player, int multiplayer){
        multiplyPoints(player,multiplayer);
        printCurrentScore(player);
    }
}
