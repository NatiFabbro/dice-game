package org.met.labweb.challenges.service;


import org.met.labweb.challenges.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayerService {
    public void setBet(Player player, String bet){
        player.setBet(bet.equalsIgnoreCase("IMPAR") ? bet : "PAR");
    }

    public String getCurrentScore(Player player){
        return "\n "+player.getName()+" Tu puntaje actual es: "+player.getScore()+"\n";
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



    public List<Player> getWinners(List<Player> players){
        int maxScore= players.stream().map(Player::getScore).max(Integer::compareTo).get();
        return players.stream().filter(x->x.getScore().equals(maxScore)).collect(Collectors.toList());
    }

    public List<Player> createPlayersList(int totalPlayers){
        List<Player> players=new ArrayList<>();
        IntStream.rangeClosed(1,totalPlayers).forEach(x-> players.add(Player.builder().id(x).score(0).build()));
        return players;
    }

    public void setPlayerName(Player player, String name){
        player.setName(name.toUpperCase());
    }
}
