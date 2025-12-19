package totemofluck;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import totemofluck.item.ModItems;
import totemofluck.event.DeathEventHandler;
import totemofluck.event.PlayerEventHandler;

@Mod(TotemOfLuck.MOD_ID)
public class TotemOfLuck {
    public static final String MOD_ID = "totemofluck";

    public TotemOfLuck() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModItems.ITEMS.register(modBus);

        modBus.addListener(this::addCreative);

        forgeBus.register(new DeathEventHandler());
        forgeBus.register(new PlayerEventHandler());

        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientProxy.init(modBus);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent e) {
        if (e.getTabKey() == CreativeModeTabs.COMBAT) {
            e.accept(ModItems.TOTEM_OF_LUCK);
        }
    }
}