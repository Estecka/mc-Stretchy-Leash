package tk.estecka.stretchyleash.config;

import java.util.HashMap;
import java.util.Map;
import tk.estecka.stretchyleash.config.ConfigIO.Property;


public class Config
extends ConfigIO.AFixedCoded
{
	public int   graceTicks   = 35;
	public float lengthMax    = 10f;
	public float pullStrength = 2f;
	public float stepHeight   = 1f;

	@Override
	public Map<String, Property<?>> GetProperties(){
		return new HashMap<>(){{
			put("graceTicks",   Property.Integer(()->graceTicks,   v->graceTicks=v  ));
			put("lengthMax",    Property.Float  (()->lengthMax,    v->lengthMax=v   ));
			put("pullStrength", Property.Float  (()->pullStrength, v->pullStrength=v));
			put("stepHeight",   Property.Float  (()->stepHeight,   v->stepHeight=v  ));
		}};
	}
}
