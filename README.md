# Stretchy Leash
Makes leash breakage less likely when they can be avoided, and easier to detect when they do happen.

## Changes
With the default configuration:
- Leashes can stretch past their maximum length, **they will break only if they remain over-stretched for too long.** This small grace period gives mobs a chance to be pulled back within range.
- **Audio cues will play at the leader's position** when a leash starts to over-stretch, when it breaks, or is manually removed.
- **Leashed mobs have their step height increased to 1**, making them less likely to get stuck when climbing slopes.
- Leashed mobs are pulled in more strongly, so they can get back within range faster.


## Config
The config file is located at `.minecraft/config/stretchy-leash.properties`.
It can be edited in-game using Mod-Menu + Cloth-Config.

#### `gracePeriod` (Integer)
Default: 35 ticks (1.75s)  
Vanilla: 0  

Controls how long a leash can remain over-stretched without breaking.

#### `maxLength` (Double)
Default: 10 blocks  
Vanilla: 10 blocks  

Controls how far a leash can reach before starting to over-stretch.

This does not affect the distance at wich mobs start being pulled in (6 blocks).

#### `pullStrength` (Double)
Default: 2  
Vanilla: 1  

Controls how fast leashed mobs are pulled toward the leader. This helps them keep-up when being pulled at high speeds.

#### `stepHeight` (Float)
Default: 1  
Vanilla: 0  

Increases the step-height of leashed mob. This reduces the risks of mobs falling behind while climbing slopes.


## Caveats
In order to be fully server-side, the sound `item.crossbow.load_middle`  is reused as the over-stretching cue. This sound event lacks a subtitle in vanilla, and would show an erroneous subtitle if it had one.
