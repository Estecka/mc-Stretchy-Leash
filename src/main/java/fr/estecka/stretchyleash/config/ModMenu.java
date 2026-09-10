package fr.estecka.stretchyleash.config;

import java.io.IOException;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.network.chat.Component;
import fr.estecka.stretchyleash.StretchyLeashMod;
import static fr.estecka.stretchyleash.StretchyLeashMod.CONFIG;


public class ModMenu
implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory(){
		Config defaultConfig = new Config();

		return parent -> {
			final var builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.literal("Stretchy Leash"));
			final var entries = builder.entryBuilder();
			final var category = builder.getOrCreateCategory(Component.literal("Leash"));

			var gracePeriod = entries
				.startIntField(Component.translatable("gamerule.stretchy-leash.gracePeriod"), CONFIG.graceTicks)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.graceTicks = v)
				.setDefaultValue(defaultConfig.graceTicks)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.gracePeriod.description"))
				.build()
				;

			var stepHeight = entries.startFloatField(Component.translatable("gamerule.stretchy-leash.stepHeight"), CONFIG.stepHeight)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.stepHeight = v)
				.setDefaultValue(defaultConfig.stepHeight)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.stepHeight.description"))
				.build()
				;

			var pullStrength = entries
				.startDoubleField(Component.translatable("gamerule.stretchy-leash.pullStrength"), CONFIG.pullStrength)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.pullStrength = v)
				.setDefaultValue(defaultConfig.pullStrength)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.pullStrength.description"))
				.build()
				;

			var snapDistance = entries
				.startDoubleField(Component.translatable("gamerule.stretchy-leash.snapDistance"), CONFIG.snapDistance)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.snapDistance = v)
				.setDefaultValue(defaultConfig.snapDistance)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.snapDistance.description"))
				.build()
				;

			var snapDistanceLarge = entries
				.startDoubleField(Component.translatable("gamerule.stretchy-leash.snapDistanceLarge"), CONFIG.snapDistanceLarge)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.snapDistanceLarge = v)
				.setDefaultValue(defaultConfig.snapDistanceLarge)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.snapDistanceLarge.description"))
				.build()
				;

			var pullDistance = entries
				.startDoubleField(Component.translatable("gamerule.stretchy-leash.pullDistance"), CONFIG.pullDistance)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.pullDistance = v)
				.setDefaultValue(defaultConfig.pullDistance)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.pullDistance.description"))
				.build()
				;

			var pullDistanceLarge = entries
				.startDoubleField(Component.translatable("gamerule.stretchy-leash.pullDistanceLarge"), CONFIG.pullDistanceLarge)
				.setMin(0)
				.setSaveConsumer(v -> CONFIG.pullDistanceLarge = v)
				.setDefaultValue(defaultConfig.pullDistanceLarge)
				.setTooltip(Component.translatable("gamerule.stretchy-leash.pullDistanceLarge.description"))
				.build()
				;

			category.addEntry(gracePeriod);
			category.addEntry(stepHeight);
			category.addEntry(pullStrength);
			category.addEntry(snapDistance);
			category.addEntry(snapDistanceLarge);
			category.addEntry(pullDistance);
			category.addEntry(pullDistanceLarge);

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
