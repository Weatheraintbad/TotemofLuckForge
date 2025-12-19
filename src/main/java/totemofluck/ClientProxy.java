package totemofluck;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import totemofluck.client.TotemAnimationRenderer;
import totemofluck.item.ModItems;

public class ClientProxy {
    public static void init(IEventBus modBus) {
        modBus.addListener(ClientProxy::registerItemDecorator);
    }

    private static void registerItemDecorator(RegisterItemDecorationsEvent e) {
        // 如果需要物品栏特殊徽章，可在此注册
        // 目前留空，保留动画渲染钩子
    }
}