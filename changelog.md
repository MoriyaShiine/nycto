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