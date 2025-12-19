package totemofluck.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.GameType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.util.FakePlayer;
import totemofluck.config.ModConfig;
import totemofluck.item.ModItems;
import totemofluck.util.TotemHelper;

public class DeathEventHandler {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent e) {
        if (!(e.getEntity() instanceof Player player) || !ModConfig.ENABLE_TOTEM_RESURRECTION)
            return;

        if (tryResurrect(player, e.getSource()))
            e.setCanceled(true);   // 取消死亡
    }

    private boolean tryResurrect(Player player, DamageSource src) {
        // 忽略假玩家
        if (player instanceof FakePlayer) return false;

        if (!ModConfig.WORK_IN_HARD_DIFFICULTY &&
                player.level().getDifficulty().getId() >= 3) return false;

        if (ModConfig.CHECK_UNBLOCKABLE_DAMAGE && isUnblockable(src)) return false;

        ItemStack totem = TotemHelper.findLuckyTotem(player);
        if (totem == null) return false;

        // 创造模式且不消耗直接复活
        if (player.isCreative() && !ModConfig.CONSUME_TOTEM_IN_CREATIVE) {
            applyEffects(player, null);
            return true;
        }

        applyEffects(player, totem);
        return true;
    }

    private boolean isUnblockable(DamageSource src) {
        String name = src.getMsgId();
        return "out_of_world".equals(name) || "generic_kill".equals(name);
    }

    private void applyEffects(Player player, ItemStack totem) {
        player.setHealth(ModConfig.FULL_HEALTH_RESURRECTION ? player.getMaxHealth() : 1.0F);
        player.removeAllEffects();
        player.clearFire();

        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,
                ModConfig.RESURRECTION_REGENERATION_DURATION,
                ModConfig.RESURRECTION_REGENERATION_AMPLIFIER));

        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,
                ModConfig.RESURRECTION_ABSORPTION_DURATION,
                ModConfig.RESURRECTION_ABSORPTION_AMPLIFIER));

        if (ModConfig.RESURRECTION_FIRE_RESISTANCE_DURATION > 0)
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,
                    ModConfig.RESURRECTION_FIRE_RESISTANCE_DURATION, 0));

        if (ModConfig.RESURRECTION_RESISTANCE_DURATION > 0)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                    ModConfig.RESURRECTION_RESISTANCE_DURATION,
                    ModConfig.RESURRECTION_RESISTANCE_AMPLIFIER));

        if (ModConfig.ADD_NIGHT_VISION && ModConfig.NIGHT_VISION_DURATION > 0)
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,
                    ModConfig.NIGHT_VISION_DURATION, 0));

        if (ModConfig.RESTORE_HUNGER) {
            player.getFoodData().setFoodLevel(20);
            player.getFoodData().setSaturation(5.0F);
        }

        if (ModConfig.PLAY_RESURRECTION_SOUND) {
            player.level().playSound(null, player.blockPosition(),
                    SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS,
                    ModConfig.RESURRECTION_SOUND_VOLUME,
                    ModConfig.RESURRECTION_SOUND_PITCH);
        }

        player.level().broadcastEntityEvent(player, (byte) 35);   // 图腾动画

        if (totem != null) totem.shrink(1);
    }
}