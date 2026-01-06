package org.zombiemace.pearlsHCF;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.zombiemace.pearlsHCF.Listeners.Listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HCFPearls extends JavaPlugin {

    public final Map<UUID, Long> pearlCooldowns = new HashMap<>();
    public final long cooldown = 10;

    @Override
    public void onEnable() {
        Listeners listener = new Listeners(this);
        Bukkit.getPluginManager().registerEvents(listener, this);
        getLogger().info("HCFPearls enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("HCFPearls disabled.");
    }
}



//    @EventHandler
//    public void onPearlThrow(ProjectileLaunchEvent event) {
//        if (!(event.getEntity() instanceof EnderPearl)) return;
//        Player p = (Player) event.getEntity().getShooter();
//        if (p == null) return;
//
//        long now = System.currentTimeMillis();
//        if (pearlCooldowns.containsKey(p.getUniqueId())) {
//            long last = pearlCooldowns.get(p.getUniqueId());
//            long remaining = (cooldown * 1000) - (now - last);
////cooldown count in chat for pearls
//            if (remaining > 0) {
//                event.setCancelled(true);
//                p.sendMessage(ChatColor.DARK_RED + "You cannot pearl for "
//                        + ChatColor.RED + (remaining / 1000) + "s");
//                return;
//            }
////        }
//
//        pearlCooldowns.put(p.getUniqueId(), now);
//
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                long left = ((cooldown * 1000) - (System.currentTimeMillis() - now)) / 1000;
//                if (left <= 0 || !p.isOnline()) {
//                    cancel();
//                }
//            }
//        }.runTaskTimer(this, 20, 20);
//    }

//pearl failing due to unsafe teleport i.e. through a closed fence gate
//    @EventHandler
//    public void onPearlTeleport(PlayerTeleportEvent event) {
//        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
//
//        Player p = event.getPlayer();
//        Material block = event.getTo().getBlock().getType();
//
//        if (isGlitchBlock(block)) {
//            event.setCancelled(true);
//            p.sendMessage(ChatColor.DARK_RED + "Pearl blocked: unsafe teleport.");
//        }
//    }

//    private boolean isGlitchBlock(Material m) {
//        return m.name().contains("SLAB")
//                || m.name().contains("STAIR")
//                || m.name().contains("FENCE")
//                || m.name().contains("GATE")
//                || m.name().contains("DOOR")
//                || m.name().contains("TRAPDOOR")
//                || m == Material.COBBLESTONE_WALL
//                || m == Material.NETHER_BRICK_FENCE;
//    }
//}


