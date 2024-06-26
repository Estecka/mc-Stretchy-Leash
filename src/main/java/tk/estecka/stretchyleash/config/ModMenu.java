package tk.estecka.stretchyleash.config;

import java.io.IOException;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.entity.Leashable;
import net.minecraft.text.Text;
import tk.estecka.stretchyleash.StretchyLeashMod;
import static tk.estecka.stretchyleash.StretchyLeashMod.CONFIG;

public class ModMenu
implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory(){
		Config defaultConfig = new Config();

		return parent -> {
			final var builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.literal("Stretchy Leash"));
			final var entries = builder.entryBuilder();
			final var category = builder.getOrCreateCategory(Text.literal("Leash"));

			category.addEntry(entries.startIntField(Text.translatable("gamerule.stretchy-leash.gracePeriod"), CONFIG.graceTicks)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.graceTicks = v)
				.setDefaultValue(defaultConfig.graceTicks)
				.setTooltip(Text.translatable("gamerule.stretchy-leash.gracePeriod.description"))
				.build()
			);

			category.addEntry(entries.startDoubleField(Text.translatable("gamerule.stretchy-leash.lengthMax"), Leashable.field_52314)
				.setMin(0)
				.setSaveConsumer(v -> Leashable.field_52314 = v)
				.setDefaultValue(10.0)
				.setTooltip(Text.translatable("gamerule.stretchy-leash.lengthMax.description"))
				.build()
			);

			category.addEntry(entries.startFloatField(Text.translatable("gamerule.stretchy-leash.pullStrength"), CONFIG.pullStrength)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.pullStrength = v)
				.setDefaultValue(defaultConfig.pullStrength)
				.setTooltip(Text.translatable("gamerule.stretchy-leash.pullStrength.description"))
				.build()
			);

			category.addEntry(entries.startFloatField(Text.translatable("gamerule.stretchy-leash.stepHeight"), CONFIG.stepHeight)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.stepHeight = v)
				.setDefaultValue(defaultConfig.stepHeight)
				.setTooltip(Text.translatable("gamerule.stretchy-leash.stepHeight.description"))
				.build()
			);

			builder.setSavingRunnable(()->{
				try {
					StretchyLeashMod.IO.Write(CONFIG);
				}
				catch (IOException e) {
					StretchyLeashMod.LOGGER.error("Unable to save config: {}", e);
				}
			});

			return builder.build();
		};
	}
}
