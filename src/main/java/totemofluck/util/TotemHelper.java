package totemofluck.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import totemofluck.item.ModItems;

public class TotemHelper {

    public static ItemStack findLuckyTotem(Player player) {
        ItemStack off = player.getItemInHand(InteractionHand.OFF_HAND);
        if (off.is(ModItems.TOTEM_OF_LUCK.get())) return off;

        ItemStack main = player.getItemInHand(InteractionHand.MAIN_HAND);
        return main.is(ModItems.TOTEM_OF_LUCK.get()) ? main : null;
    }

    public static boolean hasLuckyTotem(Player player) {
        return player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.TOTEM_OF_LUCK.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.TOTEM_OF_LUCK.get());
    }
}