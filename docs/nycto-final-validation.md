# Nycto NeoForge 1.21.1 Final Validation

日期: 2026-06-03

## 最终结论

本轮后续增强已完成并通过干净重建。最终 jar:

- `D:\mcmodding\nycto\build\libs\nycto-1.21.1-neoforge-b0.jar`
- 大小: 1,238,091 bytes
- 时间: 2026-06-03 20:41:12

## 已补强内容

- 客户端按键: `R` 使用当前能力，`Shift + R` 切换当前能力。
- 网络同步: 新增 NeoForge payload，同步吸血鬼状态、血液、最大血液、能力列表、冷却和当前能力索引。
- HUD: 显示真实血液、当前能力、冷却和能力图标，不再常态显示等待同步。
- 实体渲染: `nycto:vampire` 和 `nycto:hunter` 已注册基础 renderer；吸血鬼有发光眼层。
- 数据包: 配方、掉落、自然生成和主要进度进入 jar；旧狼人/乌头/ambrosia 等无关数据未进入最终 jar。
- 语言: `en_us` 与 `zh_cn` 已覆盖当前注册内容，弱点说明标明不会由重写版强制赋予。
- 设计约束: 新实现没有阳光惩罚逻辑，也不会给玩家添加旧版能力组合副作用。

## 构建配置

- Java 编译范围只包含:
  - `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`
  - `src/main/java/moriyashiine/nycto/neoforge/**`
- 资源范围包含:
  - `src/main/resources`
  - `src/main/generated`
- `src/main/generated` 中旧无关数据通过 `build.gradle` 排除，包括 `data/c`、`data/enchancement`、旧 damage type、timeline、anthropophagy、旧 worldgen/custom registry tags 等。
- `processResources.includeEmptyDirs = false`，避免空目录进入 jar。

## 验证结果

- `.\gradlew.bat clean build --offline --no-daemon`: 通过。
- 源数据 JSON 校验: 通过。
- jar 内 JSON 数量: 378。
- jar 内 UTF-8 BOM JSON 数量: 0。
- jar 内未发现旧危险数据路径:
  - `data/c/**`
  - `data/enchancement/**`
  - `data/minecraft/tags/damage_type/**`
  - `data/minecraft/tags/timeline/**`
  - `data/nycto/anthropophagy/**`
  - `data/nycto/damage_type/**`
  - `data/nycto/worldgen/**`

## Jar 内容确认

已确认存在:

- `moriyashiine/nycto/neoforge/network/NyctoPayloads.class`
- `moriyashiine/nycto/neoforge/network/SyncPlayerDataPayload.class`
- `moriyashiine/nycto/neoforge/network/UseActivePowerPayload.class`
- `moriyashiine/nycto/neoforge/network/SetActivePowerPayload.class`
- `moriyashiine/nycto/neoforge/client/NyctoClientInputEvents.class`
- `moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.class`
- `moriyashiine/nycto/neoforge/client/renderer/VampireRenderer.class`
- `moriyashiine/nycto/neoforge/client/renderer/HunterRenderer.class`
- `assets/nycto/lang/zh_cn.json`
- `data/nycto/neoforge/biome_modifier/vampire_spawns.json`
- `data/nycto/recipe/vampire_altar.json`
- `data/nycto/recipe/oak_coffin.json`
- `data/nycto/loot_table/entities/vampire.json`
- `data/nycto/loot_table/entities/hunter.json`

## 剩余风险

- 尚未启动 Minecraft 客户端进入世界实测；当前验证覆盖编译、资源处理、打包和 jar 内容。
- 视觉效果是基础可用版，不是旧版所有粒子、后处理、祭坛 GUI 动画的逐像素复刻。
- 若后续继续精修，可以优先做游戏内实测脚本、能力粒子/音效时机、祭坛专用 GUI。
