package com.feildmaster.module.censorchat;

import com.feildmaster.channelchat.Module;
import java.util.*;

public class Censory extends Module {
    private static Censory plugin;
    private List<Filter> filters;

    public boolean All;
    public boolean Global;
    public boolean Local;
    public boolean World;
    public boolean Private;
    public List<String> Channels;

    public void onDisable() {}

    public void onEnable() {
        plugin = this;
        reloadConfig();
        getServer().getPluginManager().registerEvents(new Moderator(), plugin);
    }

    private void reloadSettings() {
        All = getConfig().getBoolean("protect.all");
        Global = getConfig().getBoolean("protect.global");
        Local = getConfig().getBoolean("protect.local");
        World = getConfig().getBoolean("protect.world");
        Private = getConfig().getBoolean("protect.private");
    }

    private void createFilters() {
        filters = new LinkedList<Filter>();
        for(Object word : getConfig().getList("patterns")) {
            filters.add(new Filter(word.toString()));
        }
    }

    private void compileChannels() {
        Channels = new ArrayList<String>();
        for(Object o : getConfig().getList("protect.channels")) {
            Channels.add(o.toString());
        }
    }

    public void reloadConfig() {
        super.reloadConfig();
        if(getConfig().needsUpdate()) {
            saveDefaultConfig();
        }
        reloadSettings();
        compileChannels();
        createFilters();
    }

    public String applyFilters(String message) {
        for(Filter f : filters) {
            message = f.replaceAll(message);
        }

        return message;
    }

    public static Censory getPlugin() {
        return plugin;
    }
}
