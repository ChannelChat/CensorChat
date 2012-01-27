package feildmaster.module.censorchat;

import org.bukkit.event.*;
import com.feildmaster.channelchat.event.channel.ReloadEvent;
import com.feildmaster.channelchat.channel.Channel;
import com.feildmaster.channelchat.event.player.ChannelPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import static com.feildmaster.channelchat.channel.ChannelManager.getManager;

public class Moderator implements Listener {
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if(event.isCancelled()) return;

        if(Censory.getPlugin().All) {
            event.setMessage(Censory.getPlugin().applyFilters(event.getMessage()));
            return;
        }

        Channel channel = event instanceof ChannelPlayerChatEvent ? ((ChannelPlayerChatEvent)event).getChannel() : getManager().getActiveChannel(event.getPlayer());

        if(channel == null) return; // Do nothing, shouldn't happen though...

        if(Censory.getPlugin().Global && channel.getType() == Channel.Type.Global
                || Censory.getPlugin().World && channel.getType() == Channel.Type.World
                || Censory.getPlugin().Local && channel.getType() == Channel.Type.Local
                || Censory.getPlugin().Private && channel.getType() == Channel.Type.Private
                || Censory.getPlugin().Channels.contains(channel.getName()))
            event.setMessage(Censory.getPlugin().applyFilters(event.getMessage()));
    }

    @EventHandler
    public void onReload(ReloadEvent event) {
        Censory.getPlugin().reloadConfig();
    }
}
