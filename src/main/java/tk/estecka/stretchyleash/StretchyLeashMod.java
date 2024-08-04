package tk.estecka.stretchyleash;

import java.io.IOException;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.estecka.stretchyleash.config.Command;
import tk.estecka.stretchyleash.config.Config;
import tk.estecka.stretchyleash.config.ConfigIO;

public class StretchyLeashMod
implements ModInitializer
{
	static public final String MODID = "stretchy-leash";

	static public final Logger LOGGER = LoggerFactory.getLogger(MODID);
	static public final ConfigIO IO = new ConfigIO(MODID+".properties");
	static public final Config CONFIG = new Config();

	@Override
	public void onInitialize(){
		Command.Register();

		try {
			IO.GetIfExists(CONFIG);
		}
		catch(IOException e){
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * In practice, the leader is not always a player, it may be a wandering 
	 * trader or a fence post.
	 */
	static public void PlaySoundAtPlayer(Entity entity, SoundEvent event){
		entity.playSound(event, 1f, 1f);
		if (entity instanceof PlayerEntity player)
			player.playSoundToPlayer(event, SoundCategory.NEUTRAL, 1.5f, 1f);
	}
}
