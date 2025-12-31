package com.raiyanserver.rlfrs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RLFRS extends JavaPlugin implements Listener {

    private final String prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + "RLFRS v2.0" + ChatColor.GRAY + "] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        // Branding for Raiyan Server Company Limited
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "========================================");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "   RLFRS Version 2.0 Plugin");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "   Made by: Raiyan Server Company Limited");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "   System: OPTIMIZED FOR 1.21.1");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "========================================");

        Bukkit.getPluginManager().registerEvents(this, this);

        // RL (Raiyan Lag Fix): Clears dropped items every 10 minutes to maintain TPS
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            int removed = 0;
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity instanceof Item) {
                        entity.remove();
                        removed++;
                    }
                }
            }
            if (removed > 0) {
                Bukkit.broadcastMessage(prefix + ChatColor.YELLOW + "Raiyan Lag Fix: " + ChatColor.WHITE + removed + " items cleared.");
            }
        }, 12000L, 12000L); 
    }

    @EventHandler
    public void onSecurityCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();
        // RS (Raiyan Secure): Blocks unauthorized admin commands
        if ((msg.startsWith("/pl") || msg.startsWith("/stop") || msg.startsWith("/op")) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(prefix + ChatColor.RED + "Blocked by Raiyan Secure: Unauthorized Access.");
        }
    }
}
