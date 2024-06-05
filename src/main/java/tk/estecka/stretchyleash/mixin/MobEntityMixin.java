package tk.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvents;
import tk.estecka.stretchyleash.StretchyLeashMod;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin
extends LivingEntity
{
	@Shadow private Entity holdingEntity;

	private MobEntityMixin(){ super(null,null); }

	@Inject(method="detachLeash", at=@At("HEAD"))
	private void PlaySound(CallbackInfo info){
		if (holdingEntity != null && !this.getWorld().isClient())
			StretchyLeashMod.PlaySoundAtPlayer(holdingEntity, SoundEvents.ENTITY_LEASH_KNOT_BREAK);
	}
}
