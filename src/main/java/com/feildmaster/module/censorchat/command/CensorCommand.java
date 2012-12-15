package com.feildmaster.module.censorchat.command;

import com.feildmaster.module.censorchat.Censory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CensorCommand implements CommandExecutor {
    private Censory plugin;
    public CensorCommand(Censory plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("CensorChat.admin") || args.length == 0) return false;
        try{
            switch(SubCommands.valueOf(args[0].toUpperCase())){

                case DEL:
                case DELETE:
                case RM:
                case REMOVE:
                    if(args.length < 2) return false;
                    if(!plugin.Patterns.contains(args[1]) || !plugin.Patterns.remove(args[1])){
                        sender.sendMessage(ChatColor.RED + "[CensorChat] Failed to remove pattern.");
                        return true;
                    }
                    plugin.getConfig().set("patterns", plugin.Patterns);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "[CensorChat] Successfully removed pattern.");
                    break;

                case ADD:
                    if(args.length < 2) return false;
                    if(plugin.Patterns.contains(args[1]) || !plugin.Patterns.add(args[1])){
                        sender.sendMessage(ChatColor.RED + "[CensorChat] Failed to add pattern.");
                        return true;
                    }
                    plugin.getConfig().set("patterns", plugin.Patterns);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "[CensorChat] Successfully added pattern.");
                    break;

                case LIST:
                    StringBuilder string = new StringBuilder();
                    boolean color = true;
                    for(Object word : plugin.Patterns) {
                        string.append(string.length() != 0 ? " " : "").append((color = !color) ? ChatColor.GRAY : ChatColor.WHITE).append(word.toString());
                    }
                    sender.sendMessage("Patterns: "+string.toString());
                    break;
            }
        } catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }

    private enum SubCommands{
        ADD,
        REMOVE,
        DEL,
        DELETE,
        RM,
        LIST
    }
}
