package com.rpg.util.config;

import com.rpg.RPGCore;
import com.rpg.util.TranslateUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConfigTranslateFiles {

    private final Plugin plugin = RPGCore.getPlugin();
    private final File translateFile = new File(plugin.getDataFolder(), "Translate/config.yml");
    private final File messageFile = new File(plugin.getDataFolder(), "Translate/messages.yml");
    private FileConfiguration translateConfiguration = new YamlConfiguration();
    private FileConfiguration messageConfiguration = new YamlConfiguration();
    public ConfigTranslateFiles() {
        CreateConnectionConfig();
    }

    private void CreateConnectionConfig() {
        RPGCore.logInfo("[RPGCore] Translate Files . . .");
        try {
            if (!translateFile.exists()) {
                RPGCore.logInfo("[RPGCore] Translate Config File . . .");

                List<String> headers = new ArrayList<>();
                headers.add(" Set your local language with available options ['en','pt-br','es'].");

                translateConfiguration.options().setHeader(headers);
                translateConfiguration.save(translateFile);

                RPGCore.logInfo("[RPGCore] Translate Config File: 100%");
            }

            if (!messageFile.exists()) {
                RPGCore.logInfo("[RPGCore] Message File . . .");
                messageConfiguration.save(messageFile);
                RPGCore.logInfo("[RPGCore] Message File: 100%");
            }

            createTranslateSection();
            createMessageSection();

            RPGCore.logInfo("[RPGCore] Files: 100%");
        } catch (IOException e) {
            RPGCore.logInfo("[RPGCore] Translate File Configuration Error:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public File getFile() {
        return translateFile;
    }

    public FileConfiguration getConfig() {
        return translateConfiguration;
    }

    public String getMessage(String section, String key) {
        return messageConfiguration.getConfigurationSection(section).getString(key);
    }

    private void createTranslateSection() {
        try{
            translateConfiguration = YamlConfiguration.loadConfiguration(translateFile);

            if (!translateConfiguration.contains("local-language"))
                translateConfiguration.set("local-language", "en");

            translateConfiguration.save(translateFile);
        }catch (IOException ioException){
            Bukkit.getLogger().info("Error: "+ioException);
        }catch (Throwable t){
            Bukkit.getLogger().info("Error: "+t);
        }
    }

    private void createMessageSection(){
        try {
            messageConfiguration = YamlConfiguration.loadConfiguration(messageFile);
            if (messageConfiguration == null)
                RPGCore.logInfo("[RPGCore] Creating Message Configuration . . .");
                messageENSection();
                messagePTBRSection();
                messageESSection();
                messageConfiguration.save(messageFile);
        }catch (IOException ioException){
            Bukkit.getLogger().info("Error: "+ioException);
        }catch (Throwable t){
            Bukkit.getLogger().info("Error: "+t);
        }
    }

    private void messageENSection() {
        ConfigurationSection enSection = messageConfiguration.getConfigurationSection("EN");

        if (enSection == null){
            RPGCore.logInfo("[RPGCore] Setup En Messages . . .");
            enSection = messageConfiguration.createSection("EN");
        }
        translateByLanguage(enSection, "enUS");
    }

    private void messagePTBRSection(){
        ConfigurationSection ptbrSection = messageConfiguration.getConfigurationSection("PT-BR");
        if (ptbrSection == null){
            RPGCore.logInfo("[RPGCore] Setup Pt-Br Messages . . .");
            ptbrSection = messageConfiguration.createSection("PT-BR");
        }
       translateByLanguage(ptbrSection, "ptBR");
    }

    private void messageESSection(){
        ConfigurationSection esSection = messageConfiguration.getConfigurationSection("ES");
        if (esSection == null){
            RPGCore.logInfo("[RPGCore] Setup Es Messages . . .");
            esSection = messageConfiguration.createSection("ES");
        }
       translateByLanguage(esSection, "esES");
    }

    /**
     *
     * @param section //Here you'll set your ConfigurationSection
     * @param brandLanguage //You must set same name as resource/translateConfig/[brand].properties
     */
    public void translateByLanguage(ConfigurationSection section, String brandLanguage){
        Properties properties = new Properties();
        try {
            InputStream in = plugin.getResource("translateConfig/"+brandLanguage+".properties");
            assert in != null;
            InputStreamReader ir = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(ir);

            if (in != null){
                properties.load(reader);
            }

            Enumeration keys = properties.keys();
            while(keys.hasMoreElements()){
                String key = (String)keys.nextElement();
                section.set(key, properties.get(key));
            }
        }catch (IOException ioException){
            Bukkit.getLogger().info("Error with inputStream-> "+ioException);
        }catch (Throwable t){
            Bukkit.getLogger().info("Error with inputStream-> "+t);
        }
    }
}
