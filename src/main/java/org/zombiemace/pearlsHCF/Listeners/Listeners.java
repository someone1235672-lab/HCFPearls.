package org.zombiemace.pearlsHCF.Listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.zombiemace.pearlsHCF.HCFPearls;

public class Listeners implements Listener {

    private final HCFPearls plugin;

    public Listeners(HCFPearls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPearlThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl)) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player) event.getEntity().getShooter();
        long now = System.currentTimeMillis();

        if (plugin.pearlCooldowns.containsKey(player.getUniqueId())) {
            long last = plugin.pearlCooldowns.get(player.getUniqueId());
            long remaining = (plugin.cooldown * 1000) - (now - last);

            if (remaining > 0) {
                event.setCancelled(true);
                int secondsLeft = (int) Math.ceil(remaining / 1000.0);
                player.sendMessage(ChatColor.DARK_RED + "You cannot pearl for "
                        + ChatColor.RED + secondsLeft + "s");
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.RED + "Pearl Cooldown: " + secondsLeft + "s"));
                return;
            }
        }

        plugin.pearlCooldowns.put(player.getUniqueId(), now);

        new BukkitRunnable() {
            @Override
            public void run() {
                long left = ((plugin.cooldown * 1000) - (System.currentTimeMillis() - now)) / 1000;

                if (left <= 0 || !player.isOnline()) {
                    cancel();
                    return;
                }

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.RED + "Pearl Cooldown: " + left + "s"));
            }
        }.runTaskTimer(plugin, 20, 20);
    }

    @EventHandler
    public void onPearlTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;

        Player player = event.getPlayer();
        Material block = event.getTo().getBlock().getType();

        if (isGlitchBlock(block)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED + "Pearl blocked: unsafe teleport.");
        }
    }

    private boolean isGlitchBlock(Material m) {
        return m.name().contains("SLAB")
                || m.name().contains("STAIR")
                || m.name().contains("FENCE")
                || m.name().contains("GATE")
                || m.name().contains("DOOR")
                || m.name().contains("TRAPDOOR")
                || m == Material.COBBLESTONE_WALL
                || m == Material.NETHER_BRICK_FENCE;
    }
}
//hello
