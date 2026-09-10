package fr.estecka.stretchyleash.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.monster.Ghast;
import static fr.estecka.stretchyleash.StretchyLeashMod.CONFIG;


@Mixin({
	Ghast.class,
	HappyGhast.class,
})
public abstract class GhastEntityMixin
implements Leashable
{
	@Override
	public double leashSnapDistance(){
		return CONFIG.snapDistanceLarge;
	}

	@Override
	public double leashElasticDistance(){
		return CONFIG.pullDistanceLarge;
	}
}
