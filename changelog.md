------------------------------------------------------
Version 26.1-b7
------------------------------------------------------
- beast forms no longer prevent holding armor items, only wearing them
- beast forms now have an effective cannibal level of 0 when anthropophagy is installed
- fix vampire charge jump not being disabled in the sun
- fix cases of fire animation not clearing when it should

------------------------------------------------------
Version 26.1-b6
------------------------------------------------------
- power order can now be changed in altars
- vampires exposed to the sun now get heal blocked, have their stat buffs disabled, and cannot use powers
- adjust vampire armor bonuses order
- vampire armor's sun resistance bonus now prevents burning in the sun (excluding if you have photophobia)
- add vampire_sun_exposure_mode gamerule (defaults to normal)
    - normal causes full vampire armor to prevent burning in the sun, but vampires get debuffed in the sun
    - burn_but_no_debuff causes full vampire armor to only halve time to burn in the sun, but vampires don't get debuffed in the sun
- vampiric daggers now automatically extract blood to glass bottles in offhand
- vampiric daggers can no longer be used to hurt yourself in offhand
- adjust vampire altar upgrade costs
- coffins now stack to 1
- garlic wreaths no longer affect entities if there are blocks in the way
- bat form now reduces detection range of mobs
- bat form can now cling to ceilings
- fix bat form not being able to swing their arm
- blood flechettes now deal more damage
- dark form now has less damage reduction and attack damage but heals faster
- dark form now properly stores the armor and tools you can't use without scattering your inventory
- tamed pets can no longer be hypnotized
- players with hypnotize can no longer be hypnotized
- update vile presence description

------------------------------------------------------
Version 26.1-b5
------------------------------------------------------
- add registerVampiricThrallRenderer client api method
- keen senses now shows the health of entities
- keen senses now disables instantly
- add banner patterns for vampire bat, wolf skull, and hunter's mark
  - hunters on horses now spawn with hunter's mark shields
- dark form can now crouch under 2 blocks
- vampiric thralls in wander or defend mode now patrol the position they were at when set to those modes
- vampiric thralls in defend mode no longer target entities outside the range of their home position
- vampiric thralls in defend mode no longer target entities they can't see
- vampiric thralls in defend mode no longer target creakings or creepers

------------------------------------------------------
Version 26.1-b4
------------------------------------------------------
- update to new strawberrylib
- halberd upgrade recipes now keep their components when crafting
- hunter masks can now be toggled between visible, reduced, and removed

------------------------------------------------------
Version 26.1-b3
------------------------------------------------------
- update anthropophagy compat

------------------------------------------------------
Version 26.1-b2
------------------------------------------------------
- fix broken bat form first person hand texture

------------------------------------------------------
Version 26.1-b1
------------------------------------------------------
- update to 26.1

------------------------------------------------------
Version 1.21.11-b7
------------------------------------------------------
- ambrosia bottles can no longer be automatically consumed with enchancement's assimilation enchantment

------------------------------------------------------
Version 1.21.11-b6
------------------------------------------------------
- fix https://github.com/MoriyaShiine/nycto/issues/11

------------------------------------------------------
Version 1.21.11-b5
------------------------------------------------------
- vampiric thralls in defend mode now target non-vampire players

------------------------------------------------------
Version 1.21.11-b4
------------------------------------------------------
- merge https://github.com/MoriyaShiine/nycto/pull/10

------------------------------------------------------
Version 1.21.11-b3
------------------------------------------------------
- add thralled horse netherite horns texture

------------------------------------------------------
Version 1.21.11-b2
------------------------------------------------------
- update to latest strawberrylib
  - if you were currently in bat form or dark form, your model will reset but otherwise will fix itself when you reactivate the power
- add clarification that werewolves do not exist yet to werewolf altar tooltip

------------------------------------------------------
Version 1.21.11-b1
------------------------------------------------------
- update to 1.21.11

------------------------------------------------------
Version 1.21.10-b8
------------------------------------------------------
- vampires can no longer be fire immune

------------------------------------------------------
Version 1.21.10-b7
------------------------------------------------------
- infection status effects (currently only Vampirism) now cause the entity that has it to shake
- fix piglin thralls not having a defend mode

------------------------------------------------------
Version 1.21.10-b6
------------------------------------------------------
- thralls now sleep in coffins
- clean up stake coffin logic
- fix typo in holy water advancement description
- fix horse thralls having a defend mode
- fix thrall mode cycles being set on client
- fix coffin and mist form rendering inconsistencies

------------------------------------------------------
Version 1.21.10-b5
------------------------------------------------------
- add advancements
- vampire armor is now fully remodeled and retextured
- add defend mode for vampiric thralls
- mod boots can now walk on powder snow
- mod armor is now repaired with iron instead of leather
- crossbow stakes no longer have a cooldown in creative mode
- remove power hotbar toggle from accessibility screen for vanilla consistency
- remove descriptions from vampiric dagger and garlic brew (they are in the advancement descriptions instead)
- update hunter boots item textures
- fix blood not draining if drained on the same attack that killed an entity
- fix clipping on hunter armor when sneaking
- fix enchantment glints not appearing on mod armor
- fix use power payload being sent by all clients instead of only the player that used the power

------------------------------------------------------
Version 1.21.10-b4
------------------------------------------------------
- mannequins now have quality blood
- use new tooltip wrapping for specific cases instead of depending on tooltipfix

------------------------------------------------------
Version 1.21.10-b3
------------------------------------------------------
- hypnotize now removes hypnosis when sneaking
- thralls no longer count as monsters for the purpose of sleeping
- thralls no longer count towards raids
- tamed mobs can now be thralled regardless of health
- hunter heat now only increases half the time when hurting or killing hostile mobs
- hunter heat is no longer increased when entities are alone
- hunters are no longer called when hurting raiders during a raid
- increase vampire hunter armor blood drain resistance chance
- vampiric dagger tooltip now clarifies inventory right click usage
- vampire spawning is now controlled with the doVampireSpawning gamerule instead of a config
- vampires no longer get burn time reduction from attribute modifiers
- fix https://github.com/MoriyaShiine/nycto/issues/7
- hunters can no longer be disarmed with enchancement

------------------------------------------------------
Version 1.21.10-b2
------------------------------------------------------
- thralls are now unthralled if their owner doesn't have the vampiric thrall power
- hypnotize now stuns players for longer
- hurting or killing hunters now gives negative villager reputation
- hurting or killing thralls no longer increases hunter heat
- hunter heat now has a limit on how many hunters can spawn in quick succession
- shears are now effective on garlic wreaths and aconite garlands
- animal blood is now somewhat sustainable
- vampire hunter armor blood drain immunity is now only blood drain resistance
- vampire hunter armor garlic aura no longer applies heal block
- add config
  - spawnVampires (common)
  - vampireChargeJump (client)
  - vampireStepHeight (client)
- fireballs no longer bypass shields on vampires
- fix transformation command not working in command blocks or datapacks
- fix garlic wreaths and aconite garlands not breaking when unsupported
- enchancement's chaos enchantment can no longer choose infection effects

------------------------------------------------------
Version 1.21.10-b1
------------------------------------------------------
- update to 1.21.10

------------------------------------------------------
Version 1.21.9-b1
------------------------------------------------------
- update to 1.21.9
  - copper golems have no blood
  - add thralled horse copper horns texture
- vampire mobs and thralls no longer have respawn leniency
- transformation set command now supports multiple players

------------------------------------------------------
Version 1.21.8-b4
------------------------------------------------------
- fix coffin stake code running with any item instead of just wooden stakes

------------------------------------------------------
Version 1.21.8-b3
------------------------------------------------------
- add Russian translation (thank you artmax9082 on Discord!)
- fix crash when spectating non-players
- properly adjust hunter horse speed with superb steeds installed

------------------------------------------------------
Version 1.21.8-b2
------------------------------------------------------
- streamline hunter spawning so they spawn more frequently when doing vampire stuff
  - maximum heat was reduced from 8 to 5
  - heat decay now happens every 5 minutes instead of 1 minute
  - heat now increases when damaging certain entity types in general instead of just when hurting them while draining blood
- horses that spawn on hunters now have maximum horse speed
- fix hunter contracts being consumed if no hunter could be spawned
- merge https://github.com/MoriyaShiine/nycto/pull/1
  - Adds Spanish translation. Thanks macuguita
- fix https://github.com/MoriyaShiine/nycto/issues/2

------------------------------------------------------
Version 1.21.8-b1
------------------------------------------------------
- initial release