package totemofluck.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import totemofluck.TotemOfLuck;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TotemOfLuck.MOD_ID);

    public static final RegistryObject<Item> TOTEM_OF_LUCK =
            ITEMS.register("totem_of_luck",
                    () -> new Item(new Item.Properties()
                            .stacksTo(1)
                            .rarity(Rarity.EPIC)));
}