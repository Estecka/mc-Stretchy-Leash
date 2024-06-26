package tk.estecka.stretchyleash.config;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.Leashable;
import tk.estecka.stretchyleash.config.ConfigIO.Property;


public class Config
extends ConfigIO.AFixedCoded
{
	public int    graceTicks   = 35;
	public double lengthMax    = Leashable.field_52314;
	public double pullStrength = 2.0;
	public float  stepHeight   = 1.0f;

	@Override
	public Map<String, Property<?>> GetProperties(){
		return new HashMap<>(){{
			put("graceTicks",   Property.Integer(()->graceTicks,   v->graceTicks=v  ));
			put("lengthMax",    Property.Double (()->lengthMax,    v->lengthMax=v   ));
			put("pullStrength", Property.Double (()->pullStrength, v->pullStrength=v));
			put("stepHeight",   Property.Float  (()->stepHeight,   v->stepHeight=v  ));
		}};
	}
}
