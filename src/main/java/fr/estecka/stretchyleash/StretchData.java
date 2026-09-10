package fr.estecka.stretchyleash;

import net.minecraft.world.entity.Leashable.LeashData;
import static fr.estecka.stretchyleash.StretchyLeashMod.CONFIG;


/**
 * Informally a part of LeashableMixin/LeashDataMixin, but can't be declared 
 * directly in there due to mixin shenanigans.
 */
public class StretchData {
	public int remainingGraceTicks = CONFIG.graceTicks;
	public boolean wasStretching = false;


	static public StretchData Of(LeashData data){
		return ((LeashDataDuck)(Object)data).stretchyLeash$GetData();
	}

	static public interface LeashDataDuck {
		StretchData stretchyLeash$GetData();
	}
}

