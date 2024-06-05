package tk.estecka.stretchyleash.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import tk.estecka.stretchyleash.StretchyLeashMod;
import static tk.estecka.stretchyleash.StretchyLeashMod.CONFIG;


@Unique
@Mixin(PathAwareEntity.class)
public class PathAwareEntityMixin
extends MobEntity
{
	private int graceTicks = CONFIG.graceTicks;
	private boolean wasStretching = false;

	private PathAwareEntityMixin(){ super(null, null); }


	@Inject( method="updateLeash", at=@At("HEAD") )
	private void UpdateGracePeriod(CallbackInfo info){
		Entity holder = this.getHoldingEntity();

		if (holder == null)
			this.graceTicks = CONFIG.graceTicks;
		else {
			boolean isStretching = CONFIG.lengthMax*CONFIG.lengthMax < this.squaredDistanceTo(holder);

			if (isStretching) {
				--this.graceTicks;
				if (!this.wasStretching)
					StretchyLeashMod.PlaySoundAtPlayer(holder, SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE);
			}
			else
				this.graceTicks = CONFIG.graceTicks;

			this.wasStretching = isStretching;
		}
	}

	/**
	 * Intended to override the `if` condition that leads into `detachLeash`
	 */
	@ModifyConstant( method="updateLeash", expect=2, constant=@Constant(floatValue=10.0f) )
	private float ApplyGracePeriod(float original){
		if (this.graceTicks < 0)
			return 0;
		else
			return Float.POSITIVE_INFINITY;
	}

	@ModifyArgs( method="updateLeash", expect=1, at=@At(value="INVOKE", target="net/minecraft/util/math/Vec3d.add(DDD)Lnet/minecraft/util/math/Vec3d;") )
	private void PullStrength(Args args){
		args.set(0, (double)args.get(0) * CONFIG.pullStrength);
		args.set(1, (double)args.get(1) * CONFIG.pullStrength);
		args.set(2, (double)args.get(2) * CONFIG.pullStrength);
	}
}
