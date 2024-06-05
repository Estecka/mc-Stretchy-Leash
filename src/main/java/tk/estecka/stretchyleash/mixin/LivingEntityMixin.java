package tk.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import static tk.estecka.stretchyleash.StretchyLeashMod.CONFIG;


@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@ModifyReturnValue(method="getStepHeight", at=@At("RETURN"))
	private float LeashedStepHeight(float original){
		if ((Object)this instanceof MobEntity mob && mob.getHoldingEntity() != null)
			return Math.max(original, CONFIG.stepHeight);
		else
			return original;
	}
}
