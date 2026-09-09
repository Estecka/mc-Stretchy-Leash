package fr.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.passive.HappyGhastEntity;
import static fr.estecka.stretchyleash.StretchyLeashMod.CONFIG;


@Mixin({
	GhastEntity.class,
	HappyGhastEntity.class,
})
public abstract class GhastEntityMixin
implements Leashable
{
	@Override
	public double getLeashSnappingDistance(){
		return CONFIG.snapDistanceLarge;
	}

	@Override
	public double getElasticLeashDistance(){
		return CONFIG.pullDistanceLarge;
	}
}
