package fr.estecka.stretchyleash;

import java.io.IOException;
import net.fabricmc.api.ModInitializer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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

	static public void PlaySoundAtLeader(Entity leader, SoundEvent sound){
		// StretchyLeashMod.LOGGER.warn("Playing sound: {} {}", leader.isSilent(), leader);
		leader.playSound(sound, 1f, 1f);
		if (leader instanceof Player player){
			player.level().playSound(
				null,
				player.getEyePosition().x,
				player.getEyePosition().y,
				player.getEyePosition().z,
				sound,
				SoundSource.NEUTRAL,
				1.5f, // Volume
				1f // Pitch
			);
		}
	}
}
