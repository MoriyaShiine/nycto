package moriyashiine.nycto.mixin.hunter.client;

import moriyashiine.nycto.client.renderer.entity.layers.WolfHunterArmorLayer;
import net.minecraft.client.model.animal.wolf.WolfModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.world.entity.animal.wolf.Wolf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfRenderer.class)
public abstract class WolfRendererMixin extends MobRenderer<Wolf, WolfRenderState, WolfModel> {
	public WolfRendererMixin(EntityRendererProvider.Context context, WolfModel model, float shadow) {
		super(context, model, shadow);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void nycto$hunter(EntityRendererProvider.Context context, CallbackInfo ci) {
		addLayer(new WolfHunterArmorLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
	}
}
