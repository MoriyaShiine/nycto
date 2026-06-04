# runClient 阻塞修复记录 - 2026-06-03

## 修复范围

- 修复 `vampire_spawns.json` 的 NeoForge biome modifier 格式：`biomes` 改为单个自定义生物群系标签，`spawners` 使用合法生成列表。
- 新增 `data/nycto/tags/worldgen/biome/vampire_spawn_biomes.json`，合并森林和针叶林标签。
- 收窄 `build.gradle` 资源排除规则，确保新的吸血鬼生成标签会进入最终 jar，同时继续排除旧世界生成数据。
- 将 `blood_fountain_blood.json` 和 `wooden_stake.json` 改为 1.21.1 可加载的标准 block model，移除 Blockbench 三轴 rotation。
- 将 `garlic.json` 和 `garlic_wreath.json` blockstate 改为无属性 variants，匹配当前实际方块。
- 将 `pale_oak_coffin` 仍保留 Nycto 自带材质，但粒子和头部占位模型改用 1.21.1 存在的橡木资源。
- 将 `pack.mcmeta` 调整为 1.21.1 资源包格式 `pack_format: 34`。

## 本地验证

- `.\gradlew.bat clean build --offline --no-daemon` 通过。
- 最终 jar `D:\mcmodding\nycto\build\libs\nycto-1.21.1-neoforge-b0.jar` 已包含：
  - `data/nycto/neoforge/biome_modifier/vampire_spawns.json`
  - `data/nycto/tags/worldgen/biome/vampire_spawn_biomes.json`
  - 修正后的大蒜、大蒜花环、血泉血液、木桩、苍白橡木棺材资源
- 包内扫描未发现 `pale_oak_planks`、`pale_oak_sign`、`"down=`、`Missing axis`。

## 仍需实机复验

- 需要再次运行 `runClient`，点击单人游戏并进入或创建世界，确认世界注册表加载已不再崩溃。
- 进入世界后再验收吸血鬼转化、吸血、祭坛、棺材、血泉、匕首、14 个能力、猎人路线、HUD、实体渲染、声音和粒子表现。
