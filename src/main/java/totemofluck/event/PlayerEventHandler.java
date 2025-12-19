package totemofluck.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import totemofluck.api.LuckyTotemAPI;

public class PlayerEventHandler {

    @SubscribeEvent
    public void onBreakBlock(PlayerEvent.BreakSpeed e) {
        LuckyTotemAPI.tryApplyBuff(e.getEntity());
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent e) {
        // ✅ 1.20.1 无 isDeadOrDying()，用 !isAlive()
        if (!e.getTarget().isAlive()) {
            LuckyTotemAPI.tryApplyBuff(e.getEntity());
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
        Player player = e.getEntity();
        if (!player.level().isClientSide &&
                player.isUsingItem() && player.getUseItemRemainingTicks() <= 0) {
            LuckyTotemAPI.tryApplyBuff(player);
        }
    }
}