package fr.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.Leashable.Wrench;
import net.minecraft.world.entity.Leashable.LeashData;
import net.minecraft.sounds.SoundEvents;
import fr.estecka.stretchyleash.StretchData;
import fr.estecka.stretchyleash.StretchyLeashMod;
import fr.estecka.stretchyleash.StretchData.LeashDataDuck;
import static fr.estecka.stretchyleash.StretchyLeashMod.CONFIG;


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


	@Inject(
		method = "tickLeash",
		require = 1,
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/world/entity/Leashable.whenLeashedTo(Lnet/minecraft/world/entity/Entity;)V"
		)
	)
	static private void UpdateGracePeriod(
		CallbackInfo info,
		@Local(argsOnly=true) Entity leashedEntity,
		@Local(ordinal=1) Entity leader,
		@Local LeashData leashData,
		@Local double leashLength
	){
		Leashable leash = (Leashable)leashedEntity;
		StretchData stretchData = StretchData.Of(leashData);

		if (leader == null)
			stretchData.remainingGraceTicks = CONFIG.graceTicks;
		else {
			boolean isStretching = leash.leashSnapDistance() < leashLength;

			if (isStretching) {
				--stretchData.remainingGraceTicks;
				if (!stretchData.wasStretching)
					StretchyLeashMod.PlaySoundAtLeader(leader, SoundEvents.CROSSBOW_LOADING_MIDDLE.value());
			}
			else
				stretchData.remainingGraceTicks = CONFIG.graceTicks;

			stretchData.wasStretching = isStretching;
		}
	}

	/**
	 * Intended to override the `if` condition that leads into `snapLongLeash`
	 * Prevents or forces breakage by overriding the leash's snapping distance.
	 */
	@ModifyExpressionValue(
		method = "tickLeash",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/world/entity/Leashable.leashSnapDistance()D"
		)
	)
	static private double ApplyGracePeriod(double original, @Local LeashData data){
		if (StretchData.Of(data).remainingGraceTicks < 0)
			return 0; // Leash should break
		else
			return Double.POSITIVE_INFINITY; // Leash should resist
	}

	@ModifyExpressionValue(
		method = "checkElasticInteractions",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/world/entity/Leashable$Wrench.scale(D)Lnet/minecraft/world/entity/Leashable$Wrench;"
		)
	)
	private Wrench PullStrength(Wrench original){
		return original.scale(CONFIG.pullStrength);
	}

	@Overwrite
	default public double leashSnapDistance(){
		return CONFIG.snapDistance;
	}

	@Overwrite
	default public double leashElasticDistance(){
		return CONFIG.pullDistance;
	}
}
