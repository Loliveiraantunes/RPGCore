package com.rpg.commands;

import com.rpg.enums.CommandsEnum;
import com.rpg.hud.ClassMenu;
import com.rpg.hud.StatusMenu;
import com.rpg.service.LoaderPlayerStatusService;
import com.rpg.service.PlayerCheckinService;
import com.rpg.service.ReportService;
import com.rpg.util.TranslateUtil;
import com.rpg.util.colorutil.ChatColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class BasicRPGCommand implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player))
            return false;

        Player player = ((Player) sender).getPlayer();

        rpgCommands(player, command, s, args);
        statusCommand(player, command, s, args);
        classCommand(player, command, s, args);
        topPlayersLevel(player, command, s, args);
        return false;
    }

    private boolean rpgCommands(Player player, Command command, String s, String[] args){
        if(command.getName().equalsIgnoreCase(CommandsEnum.RPG.name())){
            if(args.length > 0 && args[0].equalsIgnoreCase(CommandsEnum.START.name())){
                if(PlayerCheckinService.getPlayerCheckin(player)){
                    player.sendMessage(ChatColorUtil.boldText("\n"+TranslateUtil.getKey("ALREADY_STARTED", player) , ChatColor.RED));
                    return false;
                }

                player.sendMessage(ChatColorUtil.boldText("=============================", ChatColor.WHITE));
                player.sendMessage(ChatColorUtil.boldText("\n"+TranslateUtil.getKey("WELCOME", player) , ChatColor.GREEN));
                player.sendMessage(ChatColorUtil.boldText("\n=============================", ChatColor.WHITE));

                PlayerCheckinService.setPlayerCheckin(player);
                LoaderPlayerStatusService.createScoreBoardAutoUpdate(player);
            }
        }
        return false;
    }


    private boolean statusCommand(Player player, Command command, String s, String[] args){
        if(command.getName().equalsIgnoreCase(CommandsEnum.STATUS.name())){
            player.openInventory(StatusMenu.getInventoryByPlayer(player));
        }
        return false;
    }


    private boolean classCommand(Player player, Command command, String s, String[] args){
        if(command.getName().equalsIgnoreCase(CommandsEnum.CLASS.name())){
            player.openInventory(ClassMenu.classMenu);
        }
        return false;
    }

    private boolean topPlayersLevel(Player player, Command command, String s, String[] args){
        if(command.getName().equalsIgnoreCase(CommandsEnum.LEVELTOP.name())){

            if(args.length > 0){
                try{
                    ReportService.showLevelPlayersList(Integer.parseInt(args[0]), player);
                }catch (Exception e){
                    player.sendMessage(ChatColorUtil.boldText(TranslateUtil.getKey("INVALID_VALUE"),ChatColor.RED));
                }
            }else{
                ReportService.showLevelPlayersList(1, player);
            }
        }
        return false;
    }


}
