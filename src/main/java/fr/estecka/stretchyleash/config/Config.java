package fr.estecka.stretchyleash.config;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.Leashable;
import fr.estecka.stretchyleash.config.ConfigIO.Property;


public class Config
extends ConfigIO.AFixedCoded
{
	public int    graceTicks   = 35;
	public float  stepHeight   = 1.0f;
	public double pullStrength = 2.0;
	public double pullDistance      = Leashable.LEASH_ELASTIC_DIST;
	public double pullDistanceLarge = 10.0;
	public double snapDistance      = Leashable.LEASH_TOO_FAR_DIST;
	public double snapDistanceLarge = 16.0;

	@Override
	public Map<String, Property<?>> GetProperties(){
		return new HashMap<>(){{
			put("graceTicks",   Property.Integer(()->graceTicks,   v->graceTicks=v  ));
			put("stepHeight",   Property.Float  (()->stepHeight,   v->stepHeight=v  ));
			put("pullStrength", Property.Double (()->pullStrength, v->pullStrength=v));
			put("snapDistance",      Property.Double (()->snapDistance, v->snapDistance=v));
			put("snapDistanceLarge", Property.Double (()->snapDistanceLarge, v->snapDistanceLarge=v));
			put("pullDistance",      Property.Double (()->pullDistance, v->pullDistance=v));
			put("pullDistanceLarge", Property.Double (()->pullDistanceLarge, v->pullDistanceLarge=v));
		}};
	}
}
