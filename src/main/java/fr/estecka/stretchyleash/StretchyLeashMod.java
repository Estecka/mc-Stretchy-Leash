package fr.estecka.stretchyleash;

import java.io.IOException;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.estecka.stretchyleash.config.Command;
import fr.estecka.stretchyleash.config.Config;
import fr.estecka.stretchyleash.config.ConfigIO;


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

	static public void PlaySoundAtLeader(Entity leader, SoundEvent event){
		leader.playSound(event, 1f, 1f);
		if (leader instanceof PlayerEntity player)
			player.playSoundToPlayer(event, SoundCategory.NEUTRAL, 1.5f, 1f);
	}
}
