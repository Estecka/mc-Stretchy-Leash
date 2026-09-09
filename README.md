# Stretchy Leash
Makes leash breakage less likely when they can be avoided, and easier to detect when they do happen.

## Changes
- Leashes can stretch past their maximum length, **they will break only if they remain over-stretched for too long.** This small grace period gives mobs a chance to be pulled back within range.
- An audio cue will play at the leader's position when a leash threatens to break.
- Leashed mobs are pulled in more strongly, so they can get back within range faster.
- **Leashed mobs have their step height increased to 1**, making them less likely to get stuck when climbing slopes.


## Config
The config for clients can be edited using Mod-Menu + Cloth-Config. On servers, use the `/stretchy-leash config` command (admin-only).

#### `gracePeriod` (Integer)
Default: 35 ticks (1.75s)  
Vanilla: 0  

Controls how long a leash can remain over-stretched without breaking.

#### `stepHeight` (Float)
Default: 1  
Vanilla: 0  

Increases the step-height of leashed mob. This reduces the risks of mobs falling behind while climbing slopes.

#### `snapDistance`, `snapDistanceLarge` (Double)
Default: 12 blocks  
Vanilla: 12 blocks  
Default (Large): 16 blocks  
Vanilla (Large): 16 blocks  

Controls how far a leash can reach before starting to over-stretch.

The "Large" version applies to Ghasts and Happy-Ghasts.


#### `pullDistance`, `pullDistanceLarge` (Double)
Default: 6 blocks  
Vanilla: 6 blocks  
Default (Large): 10 blocks  
Vanilla (Large): 10 blocks  

Controls how far a leash can reach without pulling mobs.

The "Large" version applies to Ghasts and Happy-Ghasts.

#### `pullStrength` (Double)
Default: 2  
Vanilla: 1  

Controls how fast leashed mobs are pulled in. This helps them keep-up when being pulled at high speeds.

**Increasing this value too much may result in mobs being flinged away at physics-defying speed.** Even 5 is already risky.

## Caveats
In order to be fully server-side, the sound `item.crossbow.load_middle`  is reused as the over-stretching cue. This sound event lacks a subtitle in vanilla, and would show an erroneous subtitle if it had one.
