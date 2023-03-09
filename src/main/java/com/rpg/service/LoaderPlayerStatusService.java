package com.rpg.service;

import com.rpg.domain.PlayerStatus;
import com.rpg.repository.impl.PlayerStatusImpl;
import com.rpg.repository.interfaces.PlayerStatusRepository;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;
public class LoaderPlayerStatusService {
    public static final PlayerStatusRepository playerStatusRepository = new PlayerStatusImpl();

    private static final String SEPARATOR = "-------------------";
    public static void createScoreBoardAutoUpdate(Player player){

        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        if(!PlayerCheckinService.getPlayerCheckin(player)){
            player.setScoreboard(scoreboard);
            return;
        }

        PlayerStatus playerStatus = playerStatusRepository.getPlayerStatus(player);
        Objective objective = scoreboard.registerNewObjective("dummy","title", ChatColorUtil.boldText("    "+playerStatus.getPlayerName()+"     \n",ChatColor.GOLD));
        int percent = (int) ((playerStatus.getCurrentXp()*100)/playerStatus.getMaxXp());

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore(ChatColor.GREEN+SEPARATOR).setScore(41);

        //TODO Fazer aplicação das Classes
        /*objective.getScore(ChatColorUtil.boldText(" "+TranslateUtil.getKey("CLASS")+": "+ ChatColorUtil.textColor(TranslateUtil.getKey("NO_CLASS"),ChatColor.WHITE),ChatColor.GREEN)).setScore(50);
        objective.getScore(ChatColor.GREEN+"-------------------").setScore(41);*/

        objective.getScore(ChatColorUtil.boldText(" "+TranslateUtil.getKey("LEVEL")+":          "+ChatColor.AQUA+playerStatus.getLevel().toString(),ChatColor.GREEN)).setScore(40);
        objective.getScore(ChatColor.GREEN+SEPARATOR).setScore(31);
        objective.getScore(ChatColorUtil.boldText(" XP:"+ percentualXPbarProgress(percent) ,ChatColor.GREEN)).setScore(30);

        objective.getScore("    "+ChatColor.GRAY+ (int)((double)playerStatus.getCurrentXp())+" / "+ ChatColor.YELLOW+ (int)((double)playerStatus.getMaxXp()) + ChatColor.AQUA+"  ("+percent+ "%)").setScore(20);

        if(playerStatus.getCore() > 0){
            objective.getScore(ChatColor.YELLOW+SEPARATOR).setScore(12);
            objective.getScore(ChatColorUtil.boldText(" Core: "+playerStatus.getCore(), ChatColor.YELLOW)).setScore(11);
        }

        objective.getScore(ChatColor.GOLD+SEPARATOR).setScore(1);
        objective.getScore(ChatColorUtil.boldText(" "+TranslateUtil.getKey("GOLD")+":  "+ChatColor.GRAY+playerStatus.getBalance(),ChatColor.GOLD)).setScore(0);
        player.setScoreboard(scoreboard);
    }

    private static String percentualXPbarProgress(int percent){
        StringBuilder percentString = new StringBuilder();

        for (int i = 0; i < (9L * percent/ 100); i++) {
            percentString.append("█");
        }

      return percentString.toString();
    }

}
