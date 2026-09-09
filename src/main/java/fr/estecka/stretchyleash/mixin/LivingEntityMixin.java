package fr.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import static fr.estecka.stretchyleash.StretchyLeashMod.CONFIG;


@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@ModifyReturnValue(method="getStepHeight", at=@At("RETURN"))
	private float LeashedStepHeight(float original){
		if (this instanceof Leashable leashable && leashable.isLeashed())
			return Math.max(original, CONFIG.stepHeight);
		else
			return original;
	}
}
