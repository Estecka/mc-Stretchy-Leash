package tk.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.Leashable.LeashData;
import net.minecraft.sound.SoundEvents;
import tk.estecka.stretchyleash.StretchData;
import tk.estecka.stretchyleash.StretchyLeashMod;
import tk.estecka.stretchyleash.StretchData.LeashDataDuck;

import static tk.estecka.stretchyleash.StretchyLeashMod.CONFIG;

@Mixin(Leashable.class)
interface LeashableMixin
{
	@Mixin(Leashable.LeashData.class)
	static class LeashDataMixin
	implements LeashDataDuck {
		@Unique
		private final StretchData stretchData = new StretchData();
		
		@Override
		public StretchData stretchyLeash$GetData(){
			return this.stretchData;
		}
	}

	@Inject(method="detachLeash(Lnet/minecraft/entity/Entity;ZZ)V", at=@At("HEAD"))
	static private void PlaySound(Entity entity, boolean _1, boolean _2, CallbackInfo info){
		Leashable leashable = (Leashable)entity;
		if (!entity.getWorld().isClient() && leashable.getLeashHolder() != null)
			StretchyLeashMod.PlaySoundAtPlayer(leashable.getLeashHolder(), SoundEvents.ENTITY_LEASH_KNOT_BREAK);
	}


	@Inject( method="tickLeash", at=@At(value="INVOKE", target="net/minecraft/entity/Leashable.shouldTickLeash(Lnet/minecraft/entity/Entity;F)Z") )
	static private void UpdateGracePeriod(Entity leashed, CallbackInfo info, @Local(ordinal=1) Entity holder, @Local LeashData data, @Local float length){
		StretchData customData = StretchData.Of(data);

		if (holder == null)
			customData.graceTicks = CONFIG.graceTicks;
		else {
			boolean isStretching = Leashable.field_52314 < length;

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
	static private double ApplyGracePeriod(double original, @Local LeashData data){
		if (StretchData.Of(data).graceTicks < 0)
			return 0; // Leash should resist
		else
			return Double.POSITIVE_INFINITY; // Leash should break
	}

	@ModifyConstant( method="applyLeashElasticity(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;F)V", require=3, constant=@Constant(doubleValue=0.4) )
	static private double PullStrength(double original){
		return original * CONFIG.pullStrength;
	}
}
