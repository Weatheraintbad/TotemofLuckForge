package totemofluck.api;

import totemofluck.config.ModConfig;
import totemofluck.util.TotemHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;

public class LuckyTotemAPI {
    public static void tryApplyBuff(Player player) {
        if (!TotemHelper.hasLuckyTotem(player)) return;
        if (player.getRandom().nextInt(100) < ModConfig.BUFF_CHANCE_PERCENT) {
            var effect = ModConfig.BUFF_POOL.get(player.getRandom().nextInt(ModConfig.BUFF_POOL.size()));
            player.addEffect(new MobEffectInstance(effect,
                    20 * ModConfig.BUFF_SECONDS, 0));
        }
    }
}