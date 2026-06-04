# Nycto 1.21.1 NeoForge Rewrite Acceptance

## 已实现

- NeoForge 极简核心已具备三件物品的客户端资源覆盖：`vampire_blood_bottle`、`blood_bottle`、`wooden_stake` 均有 1.21 item definition 和 `models/item` 引用。
- `en_us.json` 补齐当前 `wooden_stake` 物品名与第一版 HUD 文案。
- `zh_cn.json` 已重写为有效 UTF-8 JSON，并覆盖英文文件中的物品、方块、实体、能力、命令、提示、HUD、字幕、进度等 key。
- 新增轻量客户端入口 `moriyashiine.nycto.neoforge.client.NyctoClientNeoForge`。
- 新增第一版 HUD 层 `moriyashiine.nycto.neoforge.client.gui.NyctoHudLayer`，显示吸血鬼状态、血液值、当前能力与同步提示。
- `sounds.json` 已修正 `entity.blood_flechette.hit_entity`，指向已有的 `entity/blood_flechette/hit_entity.ogg`。

## 待主 Agent 验收

- 确认客户端入口接线方式：当前 `NyctoClientNeoForge` 带 client-only 自动订阅，也提供 `register(IEventBus modEventBus)` 供主入口显式接入。
- 确认玩家 Nycto 数据同步策略。HUD 当前读取本地玩家 `PersistentData` 中的 `nycto_vampire`、`nycto_blood`、`nycto_powers`；多人和服务端权威状态需要后续同步包或实体数据同步支持。
- 验收 HUD 位置与显示优先级。当前层注册在 `VanillaGuiLayers.EXPERIENCE_LEVEL` 上方，左下显示，避免覆盖热键栏中心。
- 检查完整玩法注册后新增 id 是否都有语言 key。当前中文按已有英文 key 全覆盖，但后续新方块、实体、能力、GUI 文案仍需同步补齐。
- 运行一次游戏内资源检查，确认旧完整资源中的非注册模型不会触发缺纹理或模型警告。

## 后续增强

- 接入真实能力栏与冷却数据后，将 HUD 的 `Power: ready` 替换为当前选中能力、冷却秒数和可用状态。
- 复刻旧版视觉层：实体 renderer、护甲层、粒子、后处理、能力图标、祭坛 GUI。
- 复刻旧版客户端输入：能力快捷栏 keybind、能力切换、使用能力提示。
- 按完整服务器注册清单补齐所有 block/item/entity 的 item definition、模型引用和缺失贴图兜底。
- 给能力声音补字幕 key，并按旧版音量、音高和触发时机逐项验收。
