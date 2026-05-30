package space.retri.minecraft.nofarmxp;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

public class NoFarmXP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        switch (event.getSpawnReason()) {
            case SPAWNER:
                event.getEntity().setMetadata("noxp-spawner", new FixedMetadataValue(this, true));
                break;
            case POTION_EFFECT:
                event.getEntity().setMetadata("noxp-potion", new FixedMetadataValue(this, true));
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasMetadata("noxp-spawner") 
            || (entity.hasMetadata("noxp-potion") 
                && (event.getEntity().getKiller().hasPotionEffect(PotionEffectType.BAD_OMEN)
                 || event.getEntity().getKiller().hasPotionEffect(PotionEffectType.TRIAL_OMEN))
            )
        ) {
            event.setDroppedExp(0);
        }
    }
}
