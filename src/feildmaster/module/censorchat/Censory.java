package feildmaster.module.censorchat;

import com.feildmaster.channelchat.configuration.ModuleConfiguration;
import java.util.*;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Censory extends JavaPlugin {
    private static Censory plugin;

    private ModuleConfiguration config;
    private List<Filter> filters;

    public boolean All;
    public boolean Global;
    public boolean Local;
    public boolean World;
    public boolean Private;
    public List<String> Channels;

    public void onDisable() {
        getServer().getLogger().info(String.format("[%1$s] Disabled!", getDescription().getName()));
    }

    public void onEnable() {
        plugin = this;

        setupConfig();

        reloadConfig();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_CHAT, new Moderator(), Event.Priority.Normal, this);

        getServer().getLogger().info(String.format("[%1$s] v%2$s Enabled!", getDescription().getName(), getDescription().getVersion()));
    }

    private void setupConfig() {
        config = new ModuleConfiguration(this);
        if(!config.exists())
            config.saveDefaults();
    }

    private void reloadSettings() {
        All = config.getBoolean("protect.all");
        Global = config.getBoolean("protect.global");
        Local = config.getBoolean("protect.local");
        World = config.getBoolean("protect.world");
        Private = config.getBoolean("protect.private");
    }

    private void createFilters() {
        filters = new LinkedList<Filter>();
        for(Object word : config.getList("patterns")) {
            filters.add(new Filter(word.toString()));
        }
    }

    private void compileChannels() {
        Channels = new ArrayList<String>();
        for(Object o : config.getList("protect.channels"))
            Channels.add(o.toString());
    }

    public void reloadConfig() {
        if(!config.load())
            System.out.println(format("Error occurred when loading configuration."));
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

    public List<Filter> getFilters() {
        return new LinkedList<Filter>(filters);
    }

    public static Censory getPlugin() {
        return plugin;
    }

    public String format(String message) {
        return String.format("[%1$s] %2$s", getDescription().getName(), message);
    }
}
