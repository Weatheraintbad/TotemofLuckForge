package totemofluck.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import totemofluck.item.ModItems;

import static net.minecraft.world.item.ItemDisplayContext.GROUND;

public class TotemAnimationRenderer {

    public static void renderTotemAnimation(LivingEntity entity, PoseStack stack,
                                            MultiBufferSource buffers, int light) {
        if (entity.deathTime <= 0 && entity.getHealth() > 0) return;
        for (ItemStack hand : entity.getHandSlots()) {
            if (hand.is(ModItems.TOTEM_OF_LUCK.get())) {
                doRender(entity, stack, buffers, light, hand);
                break;
            }
        }
    }

    private static void doRender(LivingEntity entity, PoseStack ms,
                                 MultiBufferSource buffers, int light, ItemStack totem) {
        ms.pushPose();
        float tick = Minecraft.getInstance().getPartialTick();
        float age = (entity.tickCount + tick) % 20 / 20f;
        float scale = 1f + age * 0.5f;

        ms.translate(0, entity.getBbHeight() + 0.5, 0);
        ms.mulPose(Axis.YP.rotationDegrees(age * 360));
        ms.scale(scale, scale, scale);

        Minecraft.getInstance().getItemRenderer().renderStatic(totem,
                GROUND, light,
                OverlayTexture.NO_OVERLAY, ms, buffers,
                entity.level(), (int) entity.getId());

        ms.popPose();
    }
}