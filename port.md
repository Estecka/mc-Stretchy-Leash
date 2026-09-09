# Minecraft Code Breaking Changes
## 1.20.5
Initial release for v1

## 1.21.0
Everything changed. Everything.
Initial release for v2.

## 1.21.2
### Worked around:
- `Leashable::tickLeash` takes an extra parameter in the first position. Parameters are unused in the mixin, provide multiple `methods` to the inject.

## 1.21.6
- Vanilla now includes breaking sound for leashes.
- Introduction of `Leashable::getLeashSnappingDistance`
- Introduction of `Leashable::getElasticLeashDistance`
- Introduction of `Leashable$Elasticity`
