package tk.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.sound.SoundEvents;
import tk.estecka.stretchyleash.StretchyLeashMod;

import static tk.estecka.stretchyleash.StretchyLeashMod.CONFIG;

@Mixin(Leashable.class)
public interface LeashableMixin
extends Leashable
{
	@Mixin(LeashData.class)
	static public class LeashDataMixin
	{
		public int graceTicks = CONFIG.graceTicks;
		public boolean wasStretching = false;
	}

	@Inject(method="detachLeash", at=@At("HEAD"))
	static private void PlaySound(Entity entity, boolean _1, boolean _2, CallbackInfo info){
		Leashable leashable = (Leashable)entity;
		if (!entity.getWorld().isClient() && leashable.getLeashHolder() != null)
			StretchyLeashMod.PlaySoundAtPlayer(leashable.getLeashHolder(), SoundEvents.ENTITY_LEASH_KNOT_BREAK);
	}


	@Inject( method="tickLeash", at=@At(value="INVOKE", target="net/minecraft/entity/Leashable.shouldTickLeash(Lnet/minecraft/entity/Entity;F)Z") )
	static private void UpdateGracePeriod(Entity leashed, CallbackInfo info, @Local Entity holder, @Local LeashData data, @Local float length){
		LeashDataMixin customData = (LeashDataMixin)(Object)data;

		if (holder == null)
			customData.graceTicks = CONFIG.graceTicks;
		else {
			boolean isStretching = CONFIG.lengthMax < length;

			if (isStretching) {
				--customData.graceTicks;
				if (!customData.wasStretching)
					StretchyLeashMod.PlaySoundAtPlayer(holder, SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE.value());
			}
			else
				customData.graceTicks = CONFIG.graceTicks;

			customData.wasStretching = isStretching;
		}
	}

	/**
	 * Intended to override the `if` condition that leads into `detachLeash`
	 */
	@ModifyConstant( method="tickLeash", constant=@Constant(doubleValue=10.0) )
	static private float ApplyGracePeriod(float original, @Local LeashData data){
		LeashDataMixin customData = (LeashDataMixin)(Object)data;
		if (customData.graceTicks < 0)
			return 0; // Should resist
		else
			return Float.POSITIVE_INFINITY; // Should break
	}

	@ModifyConstant( method="applyLeashElasticity", require=3, constant=@Constant(doubleValue=0.4) )
	static private double PullStrength(double original){
		return original * CONFIG.pullStrength;
	}
}
