package com.rpg;

import com.rpg.commands.BasicRPGCommand;
import com.rpg.enums.CommandsEnum;
import com.rpg.hud.ClassMenu;
import com.rpg.hud.SquireMenu;
import com.rpg.hud.StatusMenu;
import com.rpg.listeners.ActionsEventListeners;
import com.rpg.listeners.DamageEventListeners;
import com.rpg.listeners.JoinEventListeners;
import com.rpg.packages.DamageIndicator;
import com.rpg.packages.HealthBossBar;
import com.rpg.repository.impl.PlayerStatusImpl;
import com.rpg.service.LoaderPlayerStatusService;
import com.rpg.util.colorutil.ChatColorUtil;
import com.rpg.util.config.ConfigRPG;
import com.rpg.util.config.ConfigLeveling;
import com.rpg.util.config.ConfigTranslateFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class RPGCore extends JavaPlugin {

    private static ConfigLeveling configLeveling;
    private static ConfigRPG configRPG;
    private static ConfigTranslateFiles configTranslateFiles;
    private final PluginManager pluginManager = this.getServer().getPluginManager();

    @Override
    public void onLoad() {
        super.onLoad();

        loadConfigurations();

        boadcast(ChatColorUtil.boldText("======================", ChatColor.GOLD));
        boadcast(ChatColorUtil.boldText("[RPGCore] ", ChatColor.GREEN));
        boadcast(ChatColorUtil.textColor(" Loaded Version: "+getPlugin().getDescription().getVersion(), ChatColor.GREEN));
        boadcast(ChatColorUtil.textColor(" Author: "+getPlugin().getDescription().getAuthors(), ChatColor.GREEN));
        boadcast(ChatColorUtil.textColor(" Description: \n "+ChatColor.GRAY+getPlugin().getDescription().getDescription(), ChatColor.GREEN));
        boadcast(ChatColorUtil.boldText("======================", ChatColor.GOLD));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        pluginManager.registerEvents(new JoinEventListeners(),this);
        pluginManager.registerEvents(new ActionsEventListeners(),this);
        pluginManager.registerEvents(new DamageEventListeners(), this);

        pluginManager.registerEvents(new ClassMenu(),this);
        pluginManager.registerEvents(new SquireMenu(),this);
        pluginManager.registerEvents(new StatusMenu(),this);


        getCommand(CommandsEnum.RPG.name()).setExecutor(new BasicRPGCommand());
        getCommand(CommandsEnum.CLASS.name()).setExecutor(new BasicRPGCommand());
        getCommand(CommandsEnum.LEVELTOP.name()).setExecutor(new BasicRPGCommand());
        getCommand(CommandsEnum.STATUS.name()).setExecutor(new BasicRPGCommand());

    }

    @Override
    public void onDisable() {
        super.onDisable();

        if(DamageIndicator.damageIndicatorsEntities.size() > 0)
            DamageIndicator.damageIndicatorsEntities.forEach((key,entity)->{
                DamageIndicator.damageIndicatorsEntities.remove(key);
                entity.remove();
            });
        HealthBossBar.clearAllBossBar();
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll();
    }

    public static Plugin getPlugin(){
        return getPlugin(RPGCore.class);
    }

    private void loadConfigurations(){
        configLeveling = new ConfigLeveling();
        configTranslateFiles = new ConfigTranslateFiles();
        configRPG = new ConfigRPG();

        for (Player onlinePlayer : getServer().getOnlinePlayers()) {
            LoaderPlayerStatusService.createScoreBoardAutoUpdate(onlinePlayer);
            PlayerStatusImpl.calculateCurrentHealth(onlinePlayer);
        }
    }

    public static void logInfo(String msg){
        RPGCore.getPlugin().getServer().getLogger().info(msg);
    }

    public static void boadcast(String msg){
        RPGCore.getPlugin().getServer().broadcastMessage(msg);
    }

    public static ConfigLeveling getConfigLeveling(){return configLeveling; }

    public  static  ConfigTranslateFiles getConfigTranslateFiles(){return configTranslateFiles;}

    public static ConfigRPG getConfigRPG() {
        return configRPG;
    }
}
