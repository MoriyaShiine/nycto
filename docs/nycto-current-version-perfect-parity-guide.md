# Nycto 当前版本完美复刻指导文档

日期：2026-06-04

目标：把当前 `D:\mcmodding\nycto` 中的 1.21.1 NeoForge 移植版，推进到尽可能完整复刻原版 Nycto 的玩法、视觉、声音、界面和验收体验。

原版参考仓库：<https://github.com/MoriyaShiine/nycto>

功能清单参考：`C:\Users\Lenovo\Desktop\吸血鬼功能清单.txt`

视频文字稿参考：用户提供的 “Vampires 吸血鬼模组” 玩法拆解。该稿件强调了吸血鬼转化流程、血滴 HUD、吸血蓄力条、匕首蓄血条、祭坛 UI、Dark Form 大型恶魔吸血鬼模型、猎人装备和粒子表现。本文档后续验收必须同时参考该稿件。

当前工程：`D:\mcmodding\nycto`

当前目标版本：Minecraft 1.21.1 + NeoForge 21.1.x

NeoForge 规则参考：

- 网络包：<https://docs.neoforged.net/docs/1.21.1/networking/>
- 方块与注册：<https://docs.neoforged.net/docs/1.21.1/blocks/>
- 事件总线概念：<https://docs.neoforged.net/docs/1.21.4/concepts/events/>
- 1.21.1 `PayloadRegistrar` API：<https://lexxie.dev/neoforge/1.21.1/net/neoforged/neoforge/network/registration/PayloadRegistrar.html>

## 1. 这份文档给谁看

这份文档同时给两类人看：

- 非技术玩家：看每个功能现在像不像原版，差在哪里，修完后应该看到什么。
- 开发者：看要改哪个文件、哪类代码、哪些 NeoForge 规则不能违反。

它不是泛泛的“规划”，而是针对当前仓库版本的复刻说明。后续每补一个功能，都应该回到这份文档对应小节，把“当前状态”和“剩余差异”更新掉。

## 2. 用户定制规则

当前移植版必须保留两个定制决定：

- 不恢复原版的白天阳光惩罚。也就是说，玩家成为吸血鬼后，白天不应该因为太阳直接受伤或被迫躲避。
- 不恢复原版“能力和弱点组合后产生副作用”的机制。弱点可以作为祭坛选择内容、图标、说明或兼容数据存在，但不能强制给玩家带来用户不要的负面副作用。

这两点不是缺失功能，而是本项目的目标差异。所有后续开发和验收都要按这个规则判断。

## 3. 当前版本一句话判断

当前版本已经不是空壳。它有可启动的 NeoForge 工程、吸血鬼状态、血液数据、祭坛界面、多个能力、猎人路线、投射物、HUD、声音和粒子注册。

但它仍未达到“完美复刻”。主要差距集中在：

- 有些功能能用，但流程不完全像原版。
- 有些视觉资源已经在项目里，但还没有全部被当前 NeoForge 代码调用。
- 有些能力的核心行为已经接近，但缺少原版级别的动画、第一人称表现、特殊 AI、粒子节奏或界面反馈。
- 旧 `common`、`client`、`mixin` 包中有大量原版逻辑，但当前构建只编译 NeoForge 新包，不能误以为旧代码仍在运行。

## 4. 当前构建范围

这是判断“功能是否真的生效”的第一条规则。

当前 `build.gradle` 只把下面这些 Java 代码放进最终构建：

- `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`
- `src/main/java/moriyashiine/nycto/neoforge/**`

因此，下面这些旧代码大多只是参考材料，不会自动进入当前 jar：

- `src/main/java/moriyashiine/nycto/common/**` 中除 `NyctoNeoForge.java` 以外的大量旧实现
- `src/main/java/moriyashiine/nycto/client/**`
- `src/main/java/moriyashiine/nycto/mixin/**`

实际入口在：

- `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`

它现在负责注册：

- 护甲材料：`NyctoArmorMaterials`
- 方块：`NyctoBlocks`
- 实体：`NyctoEntityTypes`
- 猎人内容：`NyctoHunterContent`
- 物品：`NyctoItems`
- 菜单：`NyctoMenuTypes`
- 粒子：`NyctoParticleTypes`
- 声音：`NyctoSoundEvents`

验收时不能只看资源文件是否存在。只有当前构建范围里的代码注册并调用了资源，玩家才能真的看到或听到。

## 5. NeoForge 1.21.1 必须遵守的规则

### 5.1 注册规则

新增方块、物品、实体、菜单、声音、粒子时，应继续使用 `DeferredRegister`。

当前正确落点：

- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoBlocks.java`
- `src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoEntityTypes.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoParticleTypes.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMenuTypes.java`

非技术解释：不能在游戏运行到一半时随便“塞进去”一个新方块或新声音。NeoForge 要求在固定阶段登记好，游戏才能稳定识别。

### 5.2 事件规则

运行时行为要挂到正确事件上。

当前主要运行事件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoGameplay.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampirePowerEvents.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/HunterEvents.java`

客户端视觉注册在：

- `src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`

非技术解释：服务端决定“事情是否发生”，客户端决定“玩家看到什么”。比如血屏障挡住伤害是服务端逻辑，血环模型显示在身上是客户端渲染逻辑。两边不能混在一起，否则多人服务器容易崩。

### 5.3 网络包规则

1.21.1 NeoForge 使用 `CustomPacketPayload` 和 `RegisterPayloadHandlersEvent` 注册网络消息。

当前落点：

- `src/main/java/moriyashiine/nycto/neoforge/network/NyctoPayloads.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncPlayerDataPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/UseActivePowerPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SetActivePowerPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncBatFormPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncMistFormPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncDarkFormPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncBloodrushPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncCarnagePayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/AddBloodBarrierParticlesPayload.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SwapAltarPowersPayload.java`

非技术解释：服务端知道玩家是不是蝙蝠形态，但客户端如果不知道，就画不出蝙蝠。网络包就是把“服务端发生的状态”告诉“负责显示画面的客户端”。

### 5.4 Mixin 规则

当前项目不能打开旧的整套 `nycto.mixins.json`。旧 mixin 是给原平台和旧结构写的，直接启用会带来崩溃风险。

当前只允许使用极小范围的 NeoForge 专用 mixin：

- `src/main/resources/nycto-neoforge.mixins.json`
- `src/main/java/moriyashiine/nycto/neoforge/mixin/MistFormBlockStateMixin.java`
- `src/main/java/moriyashiine/nycto/neoforge/mixin/client/MistFormLivingEntityRendererMixin.java`

非技术解释：Mixin 是“改 Minecraft 原本代码”的工具。它很强，但也很危险。现在的原则是，只有事件做不到、且必须复刻视觉或碰撞规则时，才加最小范围 mixin。

### 5.5 数据存储规则

当前大部分玩家状态存在 `Player#getPersistentData()` 里：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`

这能工作，但不是长期最佳结构。后续如果要做多人长期稳定版，建议把核心状态迁移到 NeoForge Data Attachments。

当前可以暂时保留 `PersistentData`，但要保证：

- 玩家死亡、换维度、重进服务器后状态不丢。
- 服务端数据变化后及时同步给客户端。
- 客户端只负责显示，不自己决定真实玩法结果。

## 6. 当前代码总览

### 6.1 主玩法数据

核心文件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- `src/main/java/moriyashiine/nycto/neoforge/NyctoGameplay.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java`

当前已经覆盖：

- 吸血鬼身份
- 血量和最大血量
- 能力列表
- 当前选中能力
- 能力冷却
- 血屏障层数
- 回血阻断剩余时间

还需要继续确认：

- 致死时血液保命是否完整复刻原版。
- 吸血目标过滤是否完整复刻原版。
- 饮用血瓶、提取血瓶、匕首蓄血的流程反馈是否完整。

### 6.2 能力系统

核心文件：

- `src/main/java/moriyashiine/nycto/neoforge/power/NyctoPowers.java`
- `src/main/java/moriyashiine/nycto/neoforge/power/NyctoPower.java`
- `src/main/java/moriyashiine/nycto/neoforge/power/NyctoPowerRegistry.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampirePowerEvents.java`

当前 14 个吸血鬼能力都已经有入口：

- `night_vision`
- `bat_form`
- `bat_swarm`
- `batstep`
- `blood_barrier`
- `blood_flechettes`
- `bloodrush`
- `carnage`
- `dark_form`
- `haemogenesis`
- `hypnotize`
- `keen_senses`
- `mist_form`
- `vampiric_thrall`

非技术判断：玩家已经能看到“能力系统”的轮廓，但不是每个能力都已经达到原版细节。

## 7. 主吸血鬼流程差异

### 7.1 吸血鬼生成

原版体验：

- 吸血鬼在特定夜晚和特定生物群系出现。
- 玩家能通过击杀它获得吸血鬼血瓶，开始转化路线。

当前状态：

- `nycto:vampire` 已注册。
- 生成规则在 `src/main/java/moriyashiine/nycto/neoforge/entity/Vampire.java` 和 `src/main/resources/data/nycto/neoforge/biome_modifier/vampire_spawns.json` 附近。
- 需要继续实机确认森林、针叶林、新月夜的生成效果。

需要改进：

- 确认 biome tag 和生成判断没有互相冲突。
- 确认生成频率、亮度、时间与原版接近。
- 确认吸血鬼模型、眼睛发光、叫声、受伤声、死亡声全部使用原版资源。

验收标准：

- 玩家在正确环境能自然遇到吸血鬼。
- 吸血鬼看起来像原版吸血鬼，而不是普通僵尸或普通人形怪。
- 白天不对玩家吸血鬼产生阳光惩罚，但自然怪物生成规则可以仍按原版夜间逻辑判断。

### 7.2 转化成吸血鬼

原版体验：

- 击杀吸血鬼获得吸血鬼血瓶。
- 饮用后进入转化流程。
- 转化完成后玩家获得吸血鬼身份和基础能力。

当前状态：

- 吸血鬼血瓶已存在：`src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java`
- 状态写入在：`src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- 目前应继续检查是否仍有“立刻变成吸血鬼”的简化逻辑。

需要改进：

- 如果当前仍是瞬间转化，应补回饮用动作、声音、延迟转化、提示反馈。
- 保留用户定制：转化后不触发阳光惩罚。
- 汉化文本要说明当前规则，避免玩家以为白天不受伤是 bug。

验收标准：

- 玩家能明确感到“喝血瓶 -> 转化 -> 成为吸血鬼”的过程。
- 转化音效正常。
- 玻璃瓶返还正常。
- 成为吸血鬼后 HUD、能力热栏、血液系统同步出现。

### 7.3 吸血与血液

原版体验：

- 吸血鬼空手潜行右键可以吸血。
- 目标类型会影响是否能吸血。
- 被催眠、睡眠、迷雾形态等状态会影响安全性。

当前状态：

- 相关逻辑主要在 `src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java`。
- 血液数据在 `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`。
- 回血阻断现在已经能同步到客户端，并由 HUD 使用 `hud/heal_block/*` 资源显示。

需要改进：

- 补全目标过滤：无血实体、亡灵、特殊生物、类人生物、优质血液要按原版区分。
- 补全吸血粒子和声音。
- 补全安全吸血逻辑：催眠、睡眠、迷雾等状态要影响目标反应。

验收标准：

- 玩家不能从无血目标吸血。
- 吸血时目标受伤、玩家回血、玩家补血液都符合原版节奏。
- 被回血阻断时，心形 HUD 会变成原版回血阻断心。

## 8. 祭坛和能力选择

原版体验：

- 玩家打开吸血鬼祭坛界面。
- 放入材料、经验、血瓶后选择能力。
- 已拥有能力可显示、排序或切换。
- 弱点可作为选择系统的一部分存在，但本项目不恢复强制副作用。

当前状态：

- 菜单：`src/main/java/moriyashiine/nycto/neoforge/menu/VampireAltarMenu.java`
- 屏幕：`src/main/java/moriyashiine/nycto/neoforge/client/gui/VampireAltarScreen.java`
- 菜单注册：`src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMenuTypes.java`
- 屏幕注册：`src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`
- 交换能力顺序网络包：`src/main/java/moriyashiine/nycto/neoforge/network/SwapAltarPowersPayload.java`
- 资源：`src/main/resources/assets/nycto/textures/gui/container/vampire_altar.png`

当前已经接近原版：

- 有祭坛 GUI。
- 使用原版贴图。
- 有材料槽、玩家背包、能力图标、确认按钮、材料成本提示。
- 支持当前能力顺序交换。

仍需验收：

- 鼠标悬停提示是否遮挡。
- 图标位置是否和原版一致。
- 点击能力、消耗材料、刷新能力列表是否稳定。
- 多人环境下服务端是否能正确拒绝非法点击。

需要保持：

- 不通过祭坛恢复阳光惩罚。
- 不通过祭坛强制弱点副作用。

## 9. HUD 完美复刻目标

原版 HUD 应包括：

- 血液 HUD
- 能力热栏
- 当前能力选中框
- 冷却遮罩
- 血屏障层数
- 回血阻断心
- Carnage 屏幕覆盖
- Keen Senses 视觉反馈
- 蓄力跳状态
- 阳光暴露 HUD

本项目定制：

- 阳光暴露 HUD 不需要恢复成伤害系统。如果保留图形，也只能作为不造成惩罚的提示或完全不显示。

当前状态：

- HUD 文件：`src/main/java/moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.java`
- 注册位置：`src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`
- 玩家数据同步：`src/main/java/moriyashiine/nycto/neoforge/network/SyncPlayerDataPayload.java`
- Keen Senses 客户端同步：`src/main/java/moriyashiine/nycto/neoforge/network/SyncKeenSensesPayload.java`
- Keen Senses 客户端状态：`src/main/java/moriyashiine/nycto/neoforge/client/KeenSensesClientState.java`
- Keen Senses 本地高亮：`src/main/java/moriyashiine/nycto/neoforge/client/event/KeenSensesClientRenderEvents.java`
- Keen Senses 轮廓颜色：`src/main/java/moriyashiine/nycto/neoforge/mixin/client/KeenSensesEntityColorMixin.java`
- Keen Senses 声音压低：`src/main/java/moriyashiine/nycto/neoforge/mixin/client/KeenSensesClientLevelMixin.java`
- Keen Senses 后处理接入：`src/main/java/moriyashiine/nycto/neoforge/mixin/client/KeenSensesGameRendererMixin.java`
- Keen Senses 后处理资源：`src/main/resources/assets/nycto/shaders/post/keen_senses.json`
- 不被压低的声音标签：`src/main/resources/data/nycto/tags/sound_event/not_muffled.json`

当前已实现：

- 血滴资源驱动显示。
- 能力热栏资源驱动显示。
- 选中框和冷却遮罩。
- 血屏障图标和层数。
- Carnage 屏幕覆盖。
- 回血阻断心资源显示。
- Keen Senses 已从定时 buff 改为更接近原版的开关型能力，并接入基础黑色视觉覆盖、感知范围框、逐步扩大的感知距离、持续耗血和心跳节奏。
- Keen Senses 目标显示已改为客户端本地效果：开启者本地扫描距离内玩家/Mob，显示轮廓和生命心形提示；服务端不再给目标加全局 `GLOWING`，避免多人里未开启能力的玩家也看到发光目标。
- Keen Senses 目标过滤已按原版补齐：Mist Form 目标探测距离缩短到四分之一；拥有大蒜压制效果、至少两件吸血鬼猎人护甲形成的大蒜 aura、或位于 garlic wreath 12 格半径内的目标不会被感知高亮。
- Keen Senses 描边颜色已按原版红/白/灰规则接入：优质血液 `0xFF0000`，普通有血 `0xFFFFFF`，无血 `0x3F3F3F`；生命心形提示也使用同一组颜色。
- Keen Senses 声音压低已接入客户端 `ClientLevel` mixin：能力开启时，除 `nycto:not_muffled` 白名单中的技能反馈音和心跳外，普通环境声与实体声会按原版规则降为约六分之一。
- Keen Senses 后处理 shader 已按 1.21.1 `PostChain` 规则接入：在 `GameRenderer.render` 的 `doEntityOutline()` 之前处理主画面，让世界画面变成原版灰蓝色感官视野，同时保留后续实体轮廓颜色不被二次染色。

需要改进：

- 实机确认 HUD 位置不和原版快捷栏、经验条、护甲条冲突。
- 回血阻断心现在是覆盖式显示，需确认是否完全替代 vanilla 心形，而不是叠在上面显得混乱。
- 实机确认 Keen Senses 轮廓颜色、Mist Form 距离缩短、大蒜 aura 和 garlic wreath 过滤在多人环境下仍只影响开启能力的客户端。
- 补蓄力跳状态显示。

验收标准：

- 玩家成为吸血鬼后，HUD 不再是简单文字调试面板。
- 每个图标位置稳定，不随文字长度乱跳。
- 冷却时玩家能一眼看出能力不可用。
- 回血阻断时心形显示清晰。

## 10. 视觉和模型复刻

### 10.1 吸血鬼实体

当前状态：

- 渲染器：`src/main/java/moriyashiine/nycto/neoforge/client/renderer/VampireRenderer.java`
- 注册：`src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`
- 贴图：`src/main/resources/assets/nycto/textures/entity/vampire/vampire.png`
- 眼睛：`src/main/resources/assets/nycto/textures/entity/vampire/vampire_eyes.png`

需要验收：

- 模型比例是否与原版一致。
- 眼睛发光层是否正常。
- 血屏障和 Carnage layer 在野生吸血鬼身上是否正确显示。

### 10.2 猎人实体

当前状态：

- 实体和内容：`src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java`
- 渲染器：`src/main/java/moriyashiine/nycto/neoforge/client/renderer/HunterRenderer.java`
- 贴图：`src/main/resources/assets/nycto/textures/entity/hunter/vampire_hunter.png`

需要补齐：

- 原版猎人模型细节。
- 猎人装备切换。
- 远程武器、木桩、开门、追踪契约目标等 AI。

### 10.3 Dark Form

原版体验：

- 玩家变成黑暗野兽。
- 有专用模型、动画、拍翼动作、强化属性、空中跳跃或滑翔体验。

当前状态：

- 能力入口：`NyctoPowers.DARK_FORM_POWER`
- 运行逻辑：`src/main/java/moriyashiine/nycto/neoforge/event/VampirePowerEvents.java`
- 实体：`src/main/java/moriyashiine/nycto/neoforge/entity/DarkForm.java`
- 模型：`src/main/java/moriyashiine/nycto/neoforge/client/model/DarkFormModel.java`
- 渲染器：`src/main/java/moriyashiine/nycto/neoforge/client/renderer/DarkFormRenderer.java`
- 玩家视觉层：`src/main/java/moriyashiine/nycto/neoforge/client/renderer/layer/DarkFormVisualLayer.java`
- 独立玩家替身渲染：`src/main/java/moriyashiine/nycto/neoforge/client/event/DarkFormClientRenderEvents.java`
- 原版关键帧动画：`src/main/java/moriyashiine/nycto/neoforge/client/animation/DarkFormAnimation.java`
- 跳跃网络包：`src/main/java/moriyashiine/nycto/neoforge/network/DarkFormJumpPayload.java`

当前差异：

- 已有模型和属性逻辑，当前已补为更接近原版的“替换玩家外观”：Dark Form 开启时普通玩家本体、普通玩家渲染层和普通第一人称手臂会被隐藏，客户端再单独绘制 Dark Form 模型。
- Dark Form 模型已补回原版关键几何细节：头后毛发/鬃毛、红色下摆、嘴唇/獠牙、分段腿脚、更准确的手部层级和持物定位。
- Dark Form 下的 Carnage aura 已改用 Dark Form 模型外轮廓绘制，不再只套普通玩家人形 aura。
- 原版关键帧动画 `DarkFormAnimation` 已迁入当前 NeoForge 客户端包，`DarkFormModel` 已改用 1.21.1 的 `HierarchicalModel` 动画链路播放 idle、sneak idle、walk、run、jump、fly、left attack、right attack。
- 第一人称已补首版 Dark Form 爪/手臂：`RenderArmEvent` 会替换普通玩家手臂并绘制 Dark Form 单臂；`RenderHandEvent` 不再取消，避免手持物品消失。
- jump/fly 当前由客户端根据 `onGround` 和速度推断，视觉上接近，但若要完全复刻原版二段跳阶段和冷却反馈，后续还需要服务端同步更精确的 Dark Form jump phase。
- 第一人称爪臂位置、地图/弓/弩/进食/盾牌等特殊持物动画仍需实机逐项校准。
- 持物限制、装备隐藏和攻击动画实机节奏需要继续验收。

验收标准：

- 第三人称看自己时，应像原版黑暗形态，而不是普通玩家套黑影。
- 别人看你时，也应该看到黑暗形态。
- 第一人称不能出现明显穿模或普通手臂破坏沉浸感。

### 10.4 Bat Form

原版体验：

- 蝙蝠形态是开关形态，不是短时间 buff。
- 玩家变成蝙蝠、能飞行、生命上限降低、受伤降低、持续耗血。

当前状态：

- 能力入口：`NyctoPowers.BAT_FORM_POWER`
- 客户端状态：`src/main/java/moriyashiine/nycto/neoforge/network/BatFormClientState.java`
- 同步包：`src/main/java/moriyashiine/nycto/neoforge/network/SyncBatFormPayload.java`
- 渲染事件：`src/main/java/moriyashiine/nycto/neoforge/client/event/BatFormClientRenderEvents.java`
- 玩家隐藏 mixin：`src/main/java/moriyashiine/nycto/neoforge/mixin/client/MistFormLivingEntityRendererMixin.java`

当前接近原版：

- 开关形态已实现。
- 飞行、生命上限降低、受伤降低、持续耗血已实现。
- 第三人称蝙蝠模型已接入。
- 第一人称手臂隐藏已接入。

剩余差异：

- 需要核对第一人称蝙蝠翅膀是否与原版一致。
- 需要补 Bat Form 下的 Blood Barrier、Carnage 特殊 aura。
- 需要确认非 boss 生物对蝙蝠形态的目标判断是否接近原版。

### 10.5 Mist Form

原版体验：

- 玩家变成雾。
- 免疫摔落。
- 可穿过多数非完整碰撞方块。
- 第一次受到非摔落伤害时取消形态。
- 攻击时强制暴击并退出。

当前状态：

- 能力入口：`NyctoPowers.MIST_FORM_POWER`
- 运行逻辑：`VampirePowerEvents`
- 同步包：`SyncMistFormPayload`
- 碰撞 mixin：`MistFormBlockStateMixin`
- 玩家隐藏 mixin：`MistFormLivingEntityRendererMixin`
- 不可穿过方块 tag：`nycto:mist_form_unpassable`

当前接近原版：

- 开关形态、耗血、免摔、受伤取消、攻击取消、烟雾粒子已经实现。
- 最小范围 mixin 已用于碰撞规则。

剩余差异：

- 需要补更完整的雾状视觉，而不是只隐藏玩家并放烟。
- 需要实机检查穿方块边界，避免卡墙或掉出世界。

## 11. 14 个能力逐项状态

| 能力 | 当前状态 | 玩家可见差异 | 主要代码 |
| --- | --- | --- | --- |
| Night Vision | 基本可用 | 需要确认开关体验和音效是否原版 | `NyctoPowers`、`VampirePowerEvents` |
| Bat Form | 接近可用 | 第一人称翅膀、特殊 aura、目标规则仍需补 | `NyctoPowers`、`BatFormClientRenderEvents`、`SyncBatFormPayload` |
| Bat Swarm | 简化可用 | 原版蝙蝠群吸血和范围互动仍需核对 | `NyctoPowers`、`NyctoParticleTypes` |
| Batstep | 基本可用 | 路径伤害、眩晕、粒子节奏需核对 | `NyctoPowers` |
| Blood Barrier | 接近可用 | 三层血环视觉已接入，需继续核对破碎粒子 | `BloodBarrierLayer`、`AddBloodBarrierParticlesPayload` |
| Blood Flechettes | 接近可用 | 投射物和回血阻断已接入，需核对伤害和吸血反馈 | `BloodFlechetteProjectile`、`BloodFlechetteRenderer` |
| Bloodrush | 接近可用 | 冲刺 aura 已接入，需核对持续音效和碰撞结束 | `BloodrushAuraLayer`、`BloodrushClientSound` |
| Carnage | 接近可用 | 玩家和吸血鬼 aura 已有，Bat/Dark 特殊 aura 未完全验收 | `CarnageAuraLayer`、`VampireCarnageAuraLayer` |
| Dark Form | 接近可用 | 已从叠加层推进为替身渲染，补回原版模型几何细节，接入原版关键帧动画，补 Dark Form Carnage aura 和首版第一人称爪臂；仍需精确跳跃阶段同步、持物限制和实机视觉校准 | `DarkFormModel`、`DarkFormAnimation`、`DarkFormRenderer`、`DarkFormVisualLayer`、`DarkFormClientRenderEvents` |
| Haemogenesis | 基本可用 | 需要核对清除回血阻断、灭火、回血节奏 | `NyctoPowers` |
| Hypnotize | 简化可用 | 原版催眠 AI、粒子和解除逻辑仍需补 | `NyctoPowers` |
| Keen Senses | 接近可用 | 已恢复开关型、持续耗血、心跳、后处理、红/白/灰本地轮廓、生命提示、声音压低、Mist Form 距离缩短和大蒜过滤；仍需多人实机视觉验收 | `NyctoPowers`、`VampirePowerEvents`、`SyncKeenSensesPayload`、`KeenSensesClientRenderEvents`、`KeenSensesEntityColorMixin`、`KeenSensesClientLevelMixin`、`KeenSensesGameRendererMixin`、`NyctoHudLayer` |
| Mist Form | 接近可用 | 需要更完整雾化视觉和碰撞验收 | `MistFormBlockStateMixin`、`MistFormLivingEntityRendererMixin` |
| Vampiric Thrall | 明显不足 | 目前更像临时标记，未达到永久仆从管理 | `NyctoPowers`、待补 thrall AI/renderer |

## 12. Vampiric Thrall 专项差异

原版体验：

- 玩家能把类人生物、马、狼等转化为永久仆从。
- 仆从有主人。
- 仆从可跟随、停留、游荡、防御。
- 仆从有专属皮肤或外观变化。
- 不同生物有不同特殊行为。

当前状态：

- 能力入口已经存在：`NyctoPowers.VAMPIRIC_THRALL_POWER`
- 目前主要是简化标记和基础跟随，离原版差距较大。

必须补齐：

- 永久仆从状态。
- 主人 UUID。
- 仆从模式：跟随、停留、游荡、防御。
- 右键交互或快捷操作。
- 仆从目标规则。
- 村民、灾厄村民、女巫、猪灵、恼鬼、马、狼等专用处理。
- 仆从皮肤：`src/main/resources/assets/nycto/textures/entity/vampiric_thrall/**`

不要恢复：

- 原版中如果存在因太阳或弱点导致的用户不想要副作用，不应照搬。

验收标准：

- 玩家转化一个村民后，离开再回来仍是自己的仆从。
- 仆从外观明显变化。
- 仆从不会随意攻击主人。
- 模式切换能影响它的行动。

## 13. 猎人路线

### 13.1 大蒜

当前状态：

- 注册位置：`NyctoHunterContent`
- 大蒜作物：`GarlicCropBlock`
- 野生大蒜：`WildGarlicBlock`
- 世界生成资源在 `src/main/generated/data/nycto/worldgen/**` 和 `src/main/resources/data/nycto/tags/worldgen/biome/generates_garlic.json`

需要验收：

- 野外是否自然生成。
- 大蒜是否能种植、成长、收获。
- 掉落和配方是否稳定。

### 13.2 大蒜药水

当前状态：

- 物品在 `NyctoHunterContent`。
- 普通、喷溅、滞留版本存在。
- 对吸血鬼压制和回血阻断已有简化逻辑。

剩余差异：

- 酿造路线需要核对原版。
- 喷溅和滞留表现是否完全像药水实体需要验收。
- 粒子和声音需核对。

### 13.3 大蒜花环

当前状态：

- 方块存在。
- 支持墙挂和平放。
- 附近吸血鬼压制已实现。

剩余差异：

- 原版 aura component 机制尚未完全复刻。
- 当前通过范围扫描实现，行为能用，但内部结构不等同原版。

### 13.4 木桩

当前状态：

- 物品：`WoodenStakeItem`
- 投射物：`WoodenStakeProjectile`
- 渲染器：`WoodenStakeRenderer`
- 方块：`WoodenStakeBlock`
- 十字弩模型：`crossbow_wooden_stake`

当前接近原版：

- 可作为箭类被弩发射。
- 潜行右键可放成方块。
- 命中吸血鬼有额外伤害。

剩余差异：

- 木桩近战冷却、弩发射后冷却、落地方块方向、命中粒子需继续核对。

### 13.5 火焰弹

当前状态：

- 物品：`FirebombItem`
- 投射物：`FirebombProjectile`
- 临时火焰方块：`FirebombBlock`
- 命中声音：`NyctoSoundEvents.FIREBOMB_IMPACT`

当前接近原版：

- 已从右键瞬时爆炸改成真实投掷物。
- 命中后生成火焰/烟雾/范围效果。

剩余差异：

- 范围、铺火分布、雨中熄灭、持续时间要对照原版。

### 13.6 猎人契约

当前状态：

- 物品存在：`HUNTER_CONTRACT`、`VAMPIRE_HUNTER_CONTRACT`
- 当前主要是右键召唤猎人。

原版目标：

- 契约来自牧师交易。
- 猎人在远处生成并追踪目标。
- 有失败条件、目标位置寻路、战斗行为。

需要补齐：

- 牧师交易。
- 契约目标记录。
- `PathToContractPosGoal`、`UltimateTargetGoal` 等等价 AI。
- 猎人武器切换、开门、追踪、掉落。

## 14. 声音与粒子

当前声音注册：

- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java`

当前粒子注册：

- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoParticleTypes.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/particle/NyctoSpriteParticle.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`

当前已经比早期版本大幅改进：

- 主要能力声音都有注册项。
- 血液、蝙蝠群、蝙蝠步、催眠、仆从等粒子有注册。

仍要逐项验收：

- 声音是否在正确时机播放。
- 声音字幕 key 是否有中英文。
- 粒子是否有正确贴图、尺寸、透明度、生命周期。
- 客户端是否能在多人中看到别人触发的粒子。

非技术验收方法：

- 每个能力至少使用一次。
- 记录“有没有声音”“有没有粒子”“粒子位置是否在玩家/目标/路径上”。
- 如果只是背包图标能看到，不代表视觉复刻完成。

## 15. 汉化要求

当前已经有：

- `src/main/resources/assets/nycto/lang/en_us.json`
- `src/main/resources/assets/nycto/lang/zh_cn.json`

汉化目标：

- 所有物品名。
- 所有方块名。
- 所有实体名。
- 所有能力名。
- 所有弱点名。
- 祭坛界面文字。
- HUD 文字。
- 按键名。
- 声音字幕。
- 进度文本。
- 工具提示。

特别要写清楚：

- 白天不受阳光惩罚是当前移植版定制设定。
- 弱点不会强制产生用户不要的组合副作用。

验收标准：

- 中文环境下不出现 `item.nycto.xxx`、`power.nycto.xxx` 这类未翻译 key。
- 祭坛界面 tooltip 不溢出。
- 能力说明不误导玩家。

## 16. 资源文件风险

当前项目中存在大量原版资源和旧版资源。它们分布在：

- `src/main/resources`
- `src/main/generated`

风险：

- 同名文件可能重复。
- `src/main/generated` 里仍有 werewolf、aconite、旧配方、旧 advancement 等内容。
- 资源存在不代表功能已接入。

建议：

- 当前明确不做的狼人内容，文档标记为未来内容或占位内容。
- 不要让配方、战利品、进度引用未注册 ID。
- 如果 `src/main/resources` 和 `src/main/generated` 有同名关键文件，要确认最终 jar 里是哪一份。

## 17. 后续最优实施顺序

### 第一阶段：把玩家主路线补完整

目标：玩家能自然开始并完成吸血鬼路线。

任务：

- 验证吸血鬼自然生成。
- 补完整饮用吸血鬼血瓶的转化流程。
- 补吸血目标过滤。
- 补血液保命。
- 验证祭坛 UI 消耗和能力选择。

### 第二阶段：把 14 个能力逐个打磨到原版体验

优先顺序：

1. Dark Form，因为视觉差异最大。
2. Vampiric Thrall，因为当前玩法差距最大。
3. Keen Senses，因为仍需多人实机视觉验收。
4. Bat Form，因为已经接近，适合快速收尾。
5. Hypnotize 和 Bat Swarm，因为 AI/粒子需要细化。
6. 其余能力做数值、声音、粒子和 HUD 校准。

### 第三阶段：补完整猎人路线

任务：

- 大蒜自然生成和种植。
- 大蒜药水酿造、投掷、滞留。
- 花环 aura 机制。
- 木桩完整弩逻辑。
- 火焰弹细节。
- 猎人契约、牧师交易、猎人 AI。

### 第四阶段：集中做视觉复刻

任务：

- 吸血鬼模型。
- 猎人模型。
- 黑暗形态模型和动画。
- 蝙蝠形态第一人称。
- Mist Form 雾化。
- 仆从皮肤。
- 全能力 aura layer。
- Keen Senses 多人实机视觉验收。

### 第五阶段：多人和服务端稳定

任务：

- `runClient` 进入世界测试。
- `runServer` 专用服务器测试。
- 多人同步测试。
- 网络包版本一致性检查。
- 客户端类不被服务端加载。
- 数据包 reload 检查。

## 18. 最终验收清单

### 主路线

- 吸血鬼能自然生成。
- 吸血鬼死亡掉落吸血鬼血瓶。
- 饮用血瓶有转化流程。
- 转化后玩家是吸血鬼。
- 白天不受阳光惩罚。
- 吸血鬼有血液 HUD。
- 潜行右键吸血有效。
- 血瓶补血有效。
- 匕首能蓄血和提取血瓶。
- 祭坛 UI 能选能力。
- 棺材能作为吸血鬼生活设施使用。
- 吸血鬼护甲有专属外观和效果。

### 能力

- Night Vision 能开关并有反馈。
- Bat Form 有蝙蝠外观、飞行、耗血、低生命上限。
- Bat Swarm 有蝙蝠群视觉和吸血逻辑。
- Batstep 有瞬移、路径粒子、伤害或控制效果。
- Blood Barrier 有三层血环和破碎反馈。
- Blood Flechettes 有血刃投射物和回血阻断。
- Bloodrush 有冲刺光效和持续声音。
- Carnage 有红色 aura、流血、伤害增强。
- Dark Form 有完整模型、动画、第一人称处理。
- Haemogenesis 能回血、清回血阻断、灭火。
- Hypnotize 有催眠粒子、AI 影响、解除逻辑。
- Keen Senses 有后处理、目标高亮、心跳或降噪。
- Mist Form 有雾化、穿方块、免摔、受击退出。
- Vampiric Thrall 有永久仆从、皮肤、模式和 AI。

### 猎人

- 大蒜自然生成。
- 大蒜可种植。
- 大蒜药水可酿造和投掷。
- 大蒜花环有方向和 aura。
- 长戟和大蒜长戟有效。
- 木桩可手持、可弩发射、可落地方块。
- 火焰弹是投射物。
- 猎人护甲有外观和套装语义。
- 牧师出售契约。
- 契约召唤猎人追踪目标。

### 视觉声音

- 所有能力至少有一个原版级别的声音、粒子、模型或 HUD 反馈。
- 祭坛 UI 使用原版贴图。
- HUD 使用原版资源，不是调试文字。
- 吸血鬼、猎人、黑暗形态、血屏障、血刃、木桩、火焰弹都有正确贴图。
- 中文文本完整。

### NeoForge 稳定

- `gradlew build` 通过。
- `gradlew runClient` 能进入世界。
- `gradlew runServer` 能启动专用服务器。
- 多人状态同步正确。
- 客户端渲染类不会在服务器端加载。
- 数据包 reload 不报缺失 tag、配方、战利品或进度。

## 19. 当前最重要结论

当前版本已经具备“继续做完”的基础，不需要推倒重来。

接下来不能只补单个数值，而要按“玩家体验链路”补：

1. 先保证主路线完整。
2. 再把 14 个能力逐个补到原版体验。
3. 然后补猎人路线。
4. 最后集中做视觉、声音、粒子、HUD 的实机校准。

整个过程中必须守住两个定制规则：

- 不恢复阳光惩罚。
- 不恢复强制弱点副作用。

只要这两条保持不变，其余原版体验都应尽量完整复刻。

## 20. 如何把“完美复刻”落到代码

这一节把复刻工作翻译成更直接的开发规则。非技术读者可以只看“玩家应该看到什么”和“验收标准”；开发者要同时看“代码落点”和“NeoForge 注意点”。

### 20.1 判断一个功能是否真的完成

一个功能不能只因为“物品已经注册”或“贴图已经存在”就算完成。它至少要同时满足四件事：

1. 玩家能触发它。
2. 服务端能正确计算结果。
3. 客户端能显示原版级别的画面、声音和界面反馈。
4. 退出世界、换维度、死亡、多人旁观时不会坏。

举例：Keen Senses 不是只有一个 `keen_senses` 能力按钮。完整复刻要包括开关、持续耗血、逐步扩大感知范围、世界后处理、目标轮廓、生命心形、心跳声、普通声音压低、大蒜过滤、Mist Form 距离缩短，以及多人中“只有开启者能看到”的本地视觉。

当前代码落点：

- 触发能力：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\power\NyctoPowers.java`
- 每 tick 维持能力：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampirePowerEvents.java`
- 客户端视觉状态同步：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\*.java`
- 客户端 HUD 和渲染：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\**`
- 资源：`D:\mcmodding\nycto\src\main\resources\assets\nycto\**`

NeoForge 规则：

- 服务端只负责真实玩法结果，例如伤害、吸血、冷却、耗血。
- 客户端只负责显示，例如模型、轮廓、后处理、HUD、第一人称手臂。
- 服务端代码不能直接引用 `Minecraft.getInstance()` 这类客户端类。
- 客户端专用 mixin 必须放在 `nycto-neoforge.mixins.json` 的 `client` 数组里。
- 网络包按 1.21.1 `CustomPacketPayload` + `RegisterPayloadHandlersEvent` 注册，不能沿用旧平台网络写法。

### 20.2 原版旧代码应该怎么用

旧代码是复刻答案库，不是可以直接整包启用的代码。

正确做法：

- 先看旧代码确认原版表现。
- 再在 `neoforge` 包里用 1.21.1 NeoForge 的方式重写。
- 最后用实机画面和构建测试确认。

不正确做法：

- 直接启用旧 `nycto.mixins.json`。
- 让旧 `client` 包和当前 `neoforge.client` 混在一起。
- 把 Fabric/旧版本事件照搬到 NeoForge。
- 只复制资源，不注册、不调用。

原版参考区：

- `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\client\**`
- `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\common\**`
- `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\mixin\**`

当前有效实现区：

- `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\**`
- `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\common\NyctoNeoForge.java`

## 21. 按玩家体验拆开的差异文档

### 21.1 成为吸血鬼这条线

玩家应该看到：

- 夜晚遇到吸血鬼。
- 击杀吸血鬼后得到吸血鬼血瓶。
- 喝下血瓶后转化。
- 转化完成后出现吸血鬼 HUD、血液系统和能力系统。

当前代码：

- 吸血鬼实体：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\Vampire.java`
- 实体注册：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\registry\NyctoEntityTypes.java`
- 血瓶物品：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\NyctoItems.java`
- 玩家数据：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\NyctoData.java`
- 生命周期事件：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampireLifecycleEvents.java`

当前主要差异：

- 转化过程需要继续确认是否有原版那种“过程感”，而不是简单切状态。
- 吸血目标分类还需要继续对照原版：无血、普通血、优质血、类人生物、亡灵都要区分。
- 血液濒死保护需要完整验收。

NeoForge 落地要求：

- 转化状态写在服务端，并通过 `SyncPlayerDataPayload` 同步客户端。
- 转化声音、粒子、HUD 是客户端表现，但触发源应来自服务端事件。
- 白天无阳光惩罚是项目定制，不作为差异修复。

验收标准：

- 新世界不用指令也能完成“击杀吸血鬼 -> 喝血瓶 -> 成为吸血鬼”。
- 成为吸血鬼后重新进世界仍保持身份。
- 中文界面明确显示吸血鬼相关文本。

### 21.2 祭坛和能力成长

玩家应该看到：

- 吸血鬼祭坛有原版贴图和布局。
- 放入材料后能解锁能力。
- 能力图标、成本、提示、顺序调整都清楚。

当前代码：

- 祭坛方块：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\block\VampireAltarBlock.java`
- 菜单逻辑：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\menu\VampireAltarMenu.java`
- 客户端界面：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\gui\VampireAltarScreen.java`
- 交换能力网络包：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\SwapAltarPowersPayload.java`
- GUI 贴图：`D:\mcmodding\nycto\src\main\resources\assets\nycto\textures\gui\container\vampire_altar.png`

当前主要差异：

- 弱点可以显示为成长系统的一部分，但不能恢复用户不要的强制副作用。
- 要继续确认服务端是否能阻止非法菜单点击，例如客户端伪造能力交换或材料不足仍尝试购买。

NeoForge 落地要求：

- 菜单必须用服务端容器判断材料是否足够。
- 客户端屏幕只显示按钮和提示，不能自己决定玩家获得能力。
- 所有按钮动作都走明确网络包或菜单交互。

验收标准：

- 材料不足时不能获得能力。
- 材料足够时获得能力并消耗材料。
- 关闭再打开祭坛，能力顺序仍正确。

### 21.3 14 个能力的复刻优先级

| 优先级 | 能力 | 为什么排这里 | 当前关键代码 | 完美复刻判断 |
| --- | --- | --- | --- | --- |
| 1 | Dark Form | 视觉差异最大，第一眼就能看出来 | `NyctoPowers`、`VampirePowerEvents`、`DarkFormModel`、`DarkFormVisualLayer` | 第三人称和别人视角都是真正黑暗野兽，第一人称不露普通手臂 |
| 2 | Vampiric Thrall | 当前玩法差距最大 | `NyctoPowers`、待补 thrall AI/renderer | 仆从永久存在、有主人、有模式、有皮肤、有 AI |
| 3 | Keen Senses | 已大幅接近，但多人视觉必须验收 | `SyncKeenSensesPayload`、`KeenSensesClientRenderEvents`、`KeenSensesGameRendererMixin` | 只有开启者看到后处理、轮廓、心形和听觉变化 |
| 4 | Bat Form | 已接近，适合收尾 | `SyncBatFormPayload`、`BatFormClientRenderEvents` | 蝙蝠外观、飞行、耗血、第一人称翅膀、特殊 aura 都正确 |
| 5 | Mist Form | 机制接近，但视觉还弱 | `MistFormBlockStateMixin`、`MistFormLivingEntityRendererMixin` | 真正像雾，穿方块边界稳定，受伤/攻击退出 |
| 6 | Hypnotize | AI 影响和粒子节奏需要补 | `NyctoPowers`、`VampirePowerEvents` | 生物和玩家都出现原版催眠反馈 |
| 7 | Bat Swarm | 需要原版群体行为 | `NyctoPowers`、粒子注册 | 蝙蝠群会攻击、吸血、和玩家站位互动 |
| 8 | 其余战斗能力 | 多数已有基础 | `BloodBarrierLayer`、`BloodFlechetteProjectile`、`BloodrushAuraLayer`、`CarnageAuraLayer` | 数值、声音、粒子、HUD 和原版一致 |

### 21.4 Dark Form 专项复刻说明

玩家应该看到：

- 玩家从普通人形变成黑色野兽。
- 第三人称、其他玩家视角、回放视角都看不到普通玩家模型外露。
- 第一人称不会出现普通手臂破坏沉浸感。
- 空中跳跃或拍翼时有动作和声音。
- Carnage 等视觉层在 Dark Form 下也应该有对应外观。

当前代码：

- 能力开关和属性：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\power\NyctoPowers.java`
- 维持耗血、跳跃、村民恐惧等：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampirePowerEvents.java`
- Dark Form 实体：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\DarkForm.java`
- 模型：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\model\DarkFormModel.java`
- 渲染器：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\renderer\DarkFormRenderer.java`
- 玩家视觉层：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\renderer\layer\DarkFormVisualLayer.java`
- 同步：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\SyncDarkFormPayload.java`
- 跳跃包：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\DarkFormJumpPayload.java`
- 原版动画参考：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\client\renderer\entity\animation\DarkFormAnimation.java`

当前主要差异：

- 已处理早期“在玩家身上叠模型”的问题：客户端 mixin 会在 Dark Form 开启时隐藏普通玩家本体和普通玩家层，`DarkFormClientRenderEvents` 负责绘制 Dark Form 替身模型。
- 需要迁入或等价重写原版 `DarkFormAnimation`。
- 第一人称目前隐藏普通手臂，仍需要补原版爪/手臂表现。
- Dark Form 下的 Carnage aura 已补为 Dark Form 模型 aura，仍需实机确认透明度、滚动方向和贴图贴合。

NeoForge 落地要求：

- 隐藏玩家本体属于客户端渲染问题，应该放在客户端事件或客户端 mixin。
- Dark Form 是否开启由服务端决定，再同步给客户端。
- 第一人称手臂处理不能影响服务器，因为服务器没有第一人称渲染。

验收标准：

- F5 看自己，完全是 Dark Form。
- 旁边另一个玩家看你，也完全是 Dark Form。
- 空手、持物、穿甲时都不露普通玩家外观。
- 跳跃时能听到 `entity.dark_form.flap`，并有合理动作。

### 21.5 Vampiric Thrall 专项复刻说明

玩家应该看到：

- 使用能力后，合适目标变成自己的吸血鬼仆从。
- 仆从不是短时间临时效果，而是长期跟随玩法的一部分。
- 仆从有外观变化。
- 仆从能跟随、停留、游荡、防御。

当前代码：

- 能力入口：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\power\NyctoPowers.java`
- 当前 thrall 标签：`TAG_THRALL_OWNER`、`TAG_THRALL_UNTIL`
- 资源贴图：`D:\mcmodding\nycto\src\main\resources\assets\nycto\textures\entity\vampiric_thrall\**`

当前主要差异：

- 现在更像“临时标记和基础跟随”，不是原版完整仆从系统。
- 缺主人持久化、模式切换、专用 AI、专属 renderer。
- 多种实体的仆从皮肤还没有全部接入当前 NeoForge 渲染。

NeoForge 落地要求：

- 仆从主人 UUID 和模式必须由服务端存储。
- 客户端只根据同步或实体数据决定显示哪张皮肤。
- AI 目标选择必须在服务端执行。

验收标准：

- 转化村民，退出重进后仍是仆从。
- 仆从不会攻击主人。
- 切换“跟随/停留/游荡/防御”后行为明显变化。
- 村民、女巫、掠夺者、猪灵、马、狼至少各有对应外观或明确的阶段性处理。

### 21.6 Hunter 路线专项复刻说明

玩家应该看到：

- 大蒜能自然生成、种植、收获。
- 大蒜药水能酿造、投掷、滞留。
- 大蒜花环能摆放，并影响附近吸血鬼。
- 木桩能手持、能用弩发射、能插在地上。
- 火焰弹是投射物，不是简单右键效果。
- 猎人契约来自牧师交易，并能召唤追踪目标的猎人。

当前代码：

- 猎人内容总入口：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\registry\NyctoHunterContent.java`
- 猎人工具逻辑：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\hunter\**`
- 猎人实体：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\hunter\HunterEntity.java`
- 木桩：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\item\WoodenStakeItem.java`
- 火焰弹：`D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\item\FirebombItem.java`

当前主要差异：

- 契约追踪 AI 需要继续对照原版 `PathToContractPosGoal` 和 `UltimateTargetGoal`。
- 猎人武器切换、开门、目标追踪、失败条件和掉落要继续验收。
- 大蒜花环当前是范围扫描方案，体验可用，但内部不等同原版 aura component。

NeoForge 落地要求：

- 牧师交易用 NeoForge 的 villager trades 事件注册。
- 世界生成数据要符合 1.21.1 JSON 字段，例如已修正过的 `minecraft:uniform` 分布。
- 投射物实体必须注册 renderer，否则玩家只能看到隐形弹体或错误模型。

验收标准：

- 生存模式能从零做出猎人路线核心物品。
- 猎人不是原地生成后发呆，而是会追踪契约目标。
- 所有猎人工具对吸血鬼有明确克制效果。

## 22. 汉化和说明文字的验收规则

汉化不是最后装饰，它会直接影响玩家能不能理解当前定制版规则。

必须覆盖：

- 物品名：`item.nycto.*`
- 方块名：`block.nycto.*`
- 实体名：`entity.nycto.*`
- 能力名和说明：`power.nycto.*`
- 弱点名和说明：`weakness.nycto.*`
- 菜单文字：祭坛、按钮、成本、提示
- HUD 文字
- 按键名
- 声音字幕：`subtitles.nycto.*`
- 进度文本

当前文件：

- 英文：`D:\mcmodding\nycto\src\main\resources\assets\nycto\lang\en_us.json`
- 中文：`D:\mcmodding\nycto\src\main\resources\assets\nycto\lang\zh_cn.json`

必须明确写进中文说明：

- “当前版本吸血鬼白天不会因阳光受伤”。
- “弱点不会强制触发用户不需要的组合副作用”。

验收标准：

- 中文环境下不出现裸 key。
- 祭坛 tooltip 不超出屏幕。
- 能力说明和真实行为一致。

## 23. 给后续子 agent 的统一任务说明模板

后续如果继续调用子 agent，每个子 agent 都应该收到下面这些完整信息，避免它只看局部代码后误判。

必须提供：

- 当前目标：1.21.1 NeoForge 版视觉和功能尽量完美复刻原版 Nycto。
- 原版仓库：<https://github.com/MoriyaShiine/nycto>
- 当前工程：`D:\mcmodding\nycto`
- 功能清单：`C:\Users\Lenovo\Desktop\吸血鬼功能清单.txt`
- 当前文档：`D:\mcmodding\nycto\docs\nycto-current-version-perfect-parity-guide.md`
- 当前有效代码范围：`neoforge` 包和 `common\NyctoNeoForge.java`
- 用户定制规则：不恢复阳光惩罚，不恢复强制弱点组合副作用。
- NeoForge 规则：参考 1.21.1 注册、事件、网络包、客户端/服务端分离和 mixin 最小化。

子 agent 输出必须包含：

- 原版行为是什么。
- 当前实现在哪里。
- 当前实现差在哪里。
- 哪些差异是用户定制，不应该修。
- 哪些差异是真缺失，应该修。
- 修复建议要落到具体文件。
- 如果涉及视觉，要说明模型、贴图、粒子、声音、HUD、第一人称和第三人称分别怎么验收。

## 24. 每次开发后的验收顺序

每次补完一个能力或一条玩法线后，按这个顺序验收：

1. 构建通过。
2. 客户端能进世界。
3. 专用服务器能启动。
4. 生存模式能触发该功能。
5. 创造模式能快速复测边界场景。
6. 第三人称看自己。
7. 另一个客户端看你。
8. 第一人称看手、持物、HUD。
9. 听声音。
10. 看粒子。
11. 退出重进后状态还在。
12. 中文环境无裸 key。

对视觉能力，必须额外截图或肉眼确认：

- 模型有没有重叠。
- 贴图有没有丢失变成紫黑。
- HUD 有没有盖住原版快捷栏。
- 后处理有没有只影响该影响的玩家。
- 发光轮廓有没有泄漏给没开能力的玩家。

## 25. 当前版本最短可执行路线

如果目标是最快接近“完美复刻”，后续不要平均用力，建议按下面路线做：

1. 先完成 Dark Form 视觉替换、动画、第一人称和 Carnage aura。
2. 再完成 Vampiric Thrall 的永久仆从、模式、AI 和皮肤。
3. 然后做 Bat Form 第一人称翅膀和特殊 aura 收尾。
4. 再补 Hypnotize、Bat Swarm 的 AI 与粒子节奏。
5. 最后集中校准猎人契约 AI、声音、粒子、汉化、HUD 位置和多人同步。

这条路线能最快消除玩家最容易看出来的“不像原版”的部分。

## 26. 参考视频稿新增验收矩阵

用户提供的视频文字稿把一些“玩家第一眼能看到”的内容描述得更具体。下面这些点要纳入最终验收，不能只按代码清单判断。

### 26.1 主流程视觉验收

| 视频稿要求 | 玩家应该看到 | 当前文档对应位置 | 当前判断 |
| --- | --- | --- | --- |
| 夜晚针叶林寻找吸血鬼 | 西装、灰白皮肤、黄色发光眼睛的吸血鬼 | 7.1、10.1 | 需实机确认模型比例和眼睛发光 |
| 击杀后掉落吸血鬼血瓶 | 掉落关键转化道具 | 7.2 | 需实机确认掉落表 |
| 饮用后 30 秒 Vampirism | 有红色蝙蝠状态图标和倒计时 | 7.2、22 | 当前可能仍偏简化，需核对 |
| 转化后替换饥饿条 | 原鸡腿变成 10 滴血液图标 | 9、18 | HUD 已有血滴显示，需确认完全替代原饥饿条 |
| 潜行吸血蓄力条 | 快捷栏上方出现红色蓄力条 | 7.3、9 | 需补/验收 |
| 匕首蓄血条 | 用匕首攻击时出现进度条，满后刀刃变红 | 7.3、18 | 需重点验收模型 override 和 HUD |

### 26.2 模型验收

| 模型 | 视频稿描述 | 当前落点 | 当前判断 |
| --- | --- | --- | --- |
| 初始吸血鬼 | 高大人形、西装、灰皮、黄眼 | `VampireRenderer`、`vampire.png`、`vampire_eyes.png` | 需实机视觉比对 |
| 猎人 NPC | 三角帽、皮大衣、手持弩或剑、一眼遮挡 | `HunterRenderer`、`HunterEntity` | 需要继续核对装备和模型细节 |
| 蝙蝠形态 | 紫黑色小蝙蝠 | `BatFormClientRenderEvents` | 当前使用原版蝙蝠贴图，和视频稿描述可能不完全一致 |
| 蝙蝠群 | 类似变身蝙蝠，但红眼发光 | `Bat Swarm` 相关粒子/实体 | 需要补完整实体或视觉 |
| Dark Form | 大型恶魔吸血鬼、巨大破败白翼、红色破损下摆、尖耳、利爪 | `DarkFormModel`、`DarkFormAnimation`、`DarkFormClientRenderEvents` | 已补回原版关键几何、关键帧动画和首版第一人称爪臂，仍需精确跳跃阶段同步与实机校准 |
| 棺材 | 红色软垫木制箱体，有开合动画 | coffin block/model | 需验收多木材模型和开合状态 |
| 匕首 | 未吸血普通刀刃，吸满后血红 | `VampiricDaggerItem`、item models | 需核对模型切换 |

### 26.3 粒子和声音验收

| 效果 | 视频稿描述 | 当前判断 |
| --- | --- | --- |
| 吸血鬼攻击 | 紫色/粉色粒子 | 需确认 `Vampire` 攻击事件是否播放 |
| 吸血/匕首 | 红色血液粒子喷溅 | 粒子已注册，触发时机需逐项验收 |
| Mist Form | 白色烟雾围绕玩家 | 当前有烟雾粒子，但视觉密度需校准 |
| 猎人出现/死亡 | 白色烟雾 | 需补或验收 |
| 火焰弹 | 火焰方块和火星粒子，不大面积烧森林 | `FirebombProjectile`、`FirebombBlock` | 需核对范围和不引燃规则 |
| 猎人受击或技能 | 蓝绿色碎屑粒子 | 当前未明确记录，需后续核对原版来源 |

### 26.4 猎人路线补充验收

视频稿明确列出猎人装备和克制语义。当前 Hunter 路线验收时要额外检查：

- 吸血鬼猎人套装是否显示为视频稿里的猎人外观，而不是普通盔甲。
- 全套是否免疫吸血。
- 全套是否散发大蒜光环。
- 全套是否减少木桩冷却。
- 全套是否免疫吸血鬼暴击伤害。
- 戟和涂蒜戟是否有长柄攻击距离和对吸血鬼额外伤害。
- 木桩是否是近战除魔武器，也能配合弩使用。
- 火焰弹是否像燃烧瓶一样铺火，但不会造成森林级别连锁燃烧。
- 凋零之箭和大蒜尖箭是否存在；如果当前移植版暂未做，必须在差异表里标明。

### 26.5 用户定制规则和视频稿冲突

视频稿提到两类原版负面机制：

- 阳光弱点：白天燃烧并受巨额伤害。
- 祭坛升级必须同时选择负面 Debuff。

这两点和当前项目用户定制冲突，处理方式如下：

- 不恢复白天阳光惩罚。可以在文档中标为“原版/视频稿存在，但当前版本按用户要求移除”。
- 不恢复强制负面 Debuff 副作用。祭坛可以保留弱点展示、图标、说明或兼容数据，但不能强制玩家获得用户不想要的副作用。

除此之外，视频稿中的 UI、模型、粒子、声音、猎人装备语义都应尽量复刻。
