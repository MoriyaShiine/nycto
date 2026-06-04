# Nycto 1.21.1 NeoForge 完美复刻说明书

日期：2026-06-04  
项目目录：`D:\mcmodding\nycto`  
目标版本：Minecraft `1.21.1` + NeoForge `21.1.x`  
原模组仓库：<https://github.com/MoriyaShiine/nycto>  
功能清单：`C:\Users\Lenovo\Desktop\吸血鬼功能清单.txt`

这份文档面向两类读者：

- 非技术读者可以用它理解“玩家在游戏里应该看到什么、玩到什么、哪里还不像原版”。
- 开发者可以直接按文档里的文件路径去修代码、补资源、做验收。

本文说的“原版”指 MoriyaShiine/nycto 原仓库中的 Nycto 玩法与视觉表现。本文说的“当前版”指本工作区内正在重写的 `1.21.1 NeoForge` 版本。

## 1. 最重要的目标边界

当前移植版要尽可能完整复刻原 Nycto 的玩法、模型、HUD、粒子、声音、UI、物品和实体表现，但保留两个用户指定的改动：

1. 吸血鬼白天不再害怕阳光，也不因阳光受到惩罚或伤害。
2. 能力与弱点之间不再因为组合产生强制副作用。

这两条不是缺失功能，而是当前版本的设计目标。后续看到原仓库里的 `SunExposure`、`Pyrophobia`、`Humanity`、`Hydrophobia`、`Vile Presence` 等相关代码时，只能把它们当作图标、说明、兼容数据或视觉参考，不能直接恢复成惩罚玩家的机制。

非技术解释：玩家应该能像原版那样变成吸血鬼、吸血、使用祭坛、释放能力、召唤或管理仆从、使用猎人工具；但玩家不应该因为白天出门被太阳折磨，也不应该因为选了某些能力搭配而突然被额外惩罚。

## 2. 当前项目哪些代码真正会进游戏

这是判断“某个功能是否真的生效”的第一条规则。

当前 `build.gradle` 只会把下面这些 Java 代码编进 NeoForge 版本：

- `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`
- `src/main/java/moriyashiine/nycto/neoforge/**`

同时，`build.gradle` 会把 `src/main/generated` 作为资源目录加入构建，所以里面的模型、物品模型、方块状态、配方、战利品表、标签和 `sounds.json` 会进入游戏。

因此，下面这些路径里的旧代码多数只是“原版参考”，不会自动在当前 NeoForge 版本里运行：

- `src/main/java/moriyashiine/nycto/common/**` 中除 `NyctoNeoForge.java` 以外的大量旧实现。
- `src/main/java/moriyashiine/nycto/client/**`。
- `src/main/java/moriyashiine/nycto/mixin/**`。

开发规则：不能只说“旧仓库有这个类”。必须确认 `neoforge` 包里有等价实现，或者确认 `src/main/resources/nycto-neoforge.mixins.json` 已经注册了新的 NeoForge 适配 mixin。

## 3. NeoForge 1.21.1 规则，用人话解释

### 3.1 模组入口规则

NeoForge 1.21.1 要求模组有明确的入口类，并且入口类的 `@Mod` 值必须和 `META-INF/neoforge.mods.toml` 里的模组 ID 一致。官方文档说明，初始化逻辑，例如注册事件和添加 `DeferredRegister`，通常写在这个入口类构造函数里。

当前入口：

- `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`

验收要点：

- 入口类必须注册所有注册表。
- 客户端专用逻辑不能在服务器物理端直接加载。
- 新增系统不能散落成“看起来有代码但入口没挂上”的状态。

参考：NeoForge 1.21.1 Mod Files 文档：<https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/>

### 3.2 注册规则

物品、方块、实体、声音、粒子、菜单、状态效果都必须在游戏启动的固定阶段注册。当前项目采用 NeoForge 推荐的 `DeferredRegister`。

当前主要注册入口：

| 内容 | 当前文件 |
| --- | --- |
| 方块 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoBlocks.java` |
| 实体 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoEntityTypes.java` |
| 物品 | `src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java` |
| 猎人路线内容 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java` |
| 声音 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java` |
| 粒子 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoParticleTypes.java` |
| 菜单 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMenuTypes.java` |
| 状态效果 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMobEffects.java` |
| 护甲材料 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoArmorMaterials.java` |

非技术解释：游戏启动时要先拿到完整名单。不能等玩家进世界后才临时塞一个新物品或新声音，否则客户端、服务器、存档和资源包容易对不上。

参考：NeoForge 1.21.1 Registries 文档：<https://docs.neoforged.net/docs/1.21.1/concepts/registries/>

### 3.3 事件规则

NeoForge 通过事件系统处理“玩家右键”“实体受伤”“客户端渲染”“注册粒子”等行为。事件有不同总线：普通游戏事件走 `NeoForge.EVENT_BUS`，启动注册类事件走 mod bus。

当前重要事件文件：

| 系统 | 当前文件 |
| --- | --- |
| 吸血、转化、血量 | `src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java` |
| 血幕保命和吸血鬼伤害保护 | `src/main/java/moriyashiine/nycto/neoforge/event/VampireBloodEvents.java` |
| 吸血鬼能力持续效果和同步 | `src/main/java/moriyashiine/nycto/neoforge/event/VampirePowerEvents.java` |
| 蝙蝠群 | `src/main/java/moriyashiine/nycto/neoforge/event/BatSwarmEvents.java` |
| 仆从行为 | `src/main/java/moriyashiine/nycto/neoforge/event/VampiricThrallEvents.java` |

验收要点：

- 服务器逻辑必须在服务器侧判断和执行。
- 客户端视觉逻辑必须放在客户端事件中。
- 不能靠客户端按键结果直接决定伤害、回血、转换等关键状态。

参考：NeoForge 1.21.1 Events 文档：<https://docs.neoforged.net/docs/1.21.1/concepts/events/>

### 3.4 网络同步规则

凡是“服务器知道，但客户端也必须看见”的状态，都要走 1.21.1 的自定义 payload 网络包。NeoForge 1.21.1 文档说明，payload 通过 `RegisterPayloadHandlersEvent` 和 `PayloadRegistrar` 注册。

当前网络入口：

- `src/main/java/moriyashiine/nycto/neoforge/network/NyctoPayloads.java`

当前已有同步包：

| 状态 | Payload |
| --- | --- |
| 玩家血量、吸血鬼状态、能力数据 | `SyncPlayerDataPayload` |
| Bat Form | `SyncBatFormPayload` |
| Dark Form | `SyncDarkFormPayload` |
| Mist Form | `SyncMistFormPayload` |
| Bloodrush | `SyncBloodrushPayload` |
| Carnage | `SyncCarnagePayload` |
| Keen Senses | `SyncKeenSensesPayload` |
| 血幕粒子 | `AddBloodBarrierParticlesPayload` |
| 仆从皮肤/状态 | `SyncThrallPayload` |
| 使用能力 | `UseActivePowerPayload` |
| 设置当前能力 | `SetActivePowerPayload` |
| 祭坛交换能力 | `SwapAltarPowersPayload` |

非技术解释：如果玩家 A 已经变成蝙蝠，玩家 B 后走过来，B 的电脑不会自动知道 A 现在是蝙蝠。服务器必须在 B 开始看见 A 的时候补发一次“他现在是蝙蝠”的消息。

当前已经做对的方向：

- `VampirePowerEvents.onStartTracking` 已补发 Bat Form、Dark Form、Mist Form、Bloodrush、Carnage 和仆从状态。

参考：NeoForge 1.21.1 Registering Payloads 文档：<https://docs.neoforged.net/docs/1.21.1/networking/payload/>

### 3.5 客户端视觉规则

模型、HUD、粒子、实体渲染器、护甲层、第一人称效果都必须由客户端事件注册或处理。资源文件放进项目并不等于玩家能看到。

当前客户端入口：

- `src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`

当前关键客户端文件：

| 视觉内容 | 当前文件 |
| --- | --- |
| HUD | `src/main/java/moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.java` |
| Bat Form 第一人称和隐藏手臂 | `src/main/java/moriyashiine/nycto/neoforge/client/event/BatFormClientRenderEvents.java` |
| Dark Form 第一人称和状态 | `src/main/java/moriyashiine/nycto/neoforge/client/event/DarkFormClientRenderEvents.java` |
| Mist Form 第一人称和透明状态 | `src/main/java/moriyashiine/nycto/neoforge/client/event/MistFormClientRenderEvents.java` |
| Keen Senses 视觉 | `src/main/java/moriyashiine/nycto/neoforge/client/event/KeenSensesClientRenderEvents.java` |
| 仆从皮肤 | `src/main/java/moriyashiine/nycto/neoforge/client/event/VampiricThrallClientEvents.java` |
| Dark Form 实体渲染 | `src/main/java/moriyashiine/nycto/neoforge/client/renderer/DarkFormRenderer.java` |
| Hunter 实体渲染 | `src/main/java/moriyashiine/nycto/neoforge/client/renderer/HunterRenderer.java` |

NeoForge 模型规则要点：

- 物品和方块模型走资源 JSON。
- 非普通方块/物品上下文中使用的额外模型，要通过客户端模型事件注册。
- 实体模型层要通过 `EntityRenderersEvent.RegisterLayerDefinitions` 注册。

参考：NeoForge 1.21.1 Models 文档：<https://docs.neoforged.net/docs/1.21.1/resources/client/models/>

## 4. 当前版本总体判断

当前版本已经具备可运行的 NeoForge 移植骨架，并且核心内容已经不是空壳：

- 吸血鬼状态与玩家血量数据。
- 吸血鬼转化流程。
- 血瓶、吸血、血量 HUD。
- 吸血鬼祭坛和能力选择。
- 多个吸血鬼能力。
- 猎人工具、猎人护甲、木桩、蒜、戟、火焰弹等内容。
- 实体、模型、声音、粒子和部分客户端视觉层。
- 中文语言文件入口。

但距离“完美复刻原版”仍有明显差距，主要集中在四类：

1. 有些功能可用，但规则还不像原版。
2. 有些视觉资源已经存在，但 NeoForge 代码没有完全接入。
3. 有些能力只实现了玩法核心，缺第一人称、第三人称、粒子、声音或动画细节。
4. 仆从、猎人 AI、祭坛 UI、弱点展示等复杂系统还没有达到原版完整度。

## 5. 主线流程复刻标准

### 5.1 成为吸血鬼

玩家应看到的结果：

- 击杀吸血鬼后获得吸血鬼血瓶。
- 饮用吸血鬼血瓶后进入转化流程。
- 转化完成后成为吸血鬼。
- 血量 HUD 出现。
- 祭坛成长路线可用。

当前代码：

- 数据：`src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- 转化与吸血交互：`src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java`
- 物品：`src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java`
- HUD：`src/main/java/moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.java`
- 饥饿条替换：`src/main/java/moriyashiine/nycto/neoforge/mixin/client/VampireFoodHudMixin.java`

当前已接近原版的点：

- 玩家血量上限按原版方向使用 `100`。
- 初始血量为 `50`。
- 血瓶补血值统一为 `10`。
- 吸血鬼玩家的饥饿条已经被血量 HUD 替代。
- 吸血时已经避免“玩家血满但继续浪费目标血量”的问题。
- 潜行吸血不再要求副手为空，更接近原版主手空手吸血的交互。

剩余差异：

- 原版吸血安全规则更细，包括目标是否睡眠、是否超过半血、玩家是否处于迷雾形态、目标是否被催眠等。当前已经补了一部分，但仍需要逐项对照原仓库。
- 匕首蓄血、用玻璃瓶提取血瓶、自伤取血等流程还未完全对齐。
- 非吸血鬼饮用普通血瓶、吸血鬼饮用各种血瓶时的反胃、声音和粒子节奏还需实测。

验收标准：

- 生存模式从零开始能完成“击杀吸血鬼 -> 获得吸血鬼血瓶 -> 饮用 -> 成为吸血鬼 -> 看到血量 HUD -> 使用祭坛”的闭环。
- 白天不受阳光伤害，这是本项目定制目标，不算失败。
- 血瓶、吸血、匕首抽血都应有清楚但不过度的声音和视觉反馈。

### 5.2 血量与 HUD

玩家应看到的结果：

- 吸血鬼不再看普通饥饿条，而是看血滴。
- 血幕、治疗阻断、能力热栏、血量变化都能清楚显示。
- HUD 不遮挡原版生命、护甲、经验等信息。

当前代码：

- `src/main/java/moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.java`
- `src/main/java/moriyashiine/nycto/neoforge/mixin/client/VampireFoodHudMixin.java`
- `src/main/java/moriyashiine/nycto/neoforge/network/SyncPlayerDataPayload.java`

当前已接近原版的点：

- 血滴位置已经放到原版饥饿条附近。
- 取消了破坏原版观感的额外数字血量文字。
- 血幕图标位置更接近原版 HUD。

剩余差异：

- 原版能力热栏支持更完整的滚轮/数字选择体验；当前主要是按键切换。
- 准星指向可吸血目标时，原版有更明确的目标血量反馈；当前仍需补。
- Keen Senses、Carnage、治疗阻断等 HUD 细节还需逐项视觉验收。

## 6. 吸血鬼能力复刻清单

| 能力 | 玩家应看到的表现 | 当前代码 | 当前差异 |
| --- | --- | --- | --- |
| Night Vision | 夜间视野更清楚 | `NyctoPowers`、客户端同步 | 需确认开关、图标和原版一致 |
| Bat Form | 玩家变成蝙蝠、能飞、体型变小，第一人称有蝙蝠翼 | `NyctoPowers`、`BatFormClientRenderEvents`、`SyncBatFormPayload` | 第一人称翼片已接入，但位置、大小、遮挡需 runClient 微调 |
| Bat Swarm | 召唤蝙蝠群攻击并吸血 | `NyctoPowers`、`BatSwarmEvents` | 消耗、冷却、日照下蝙蝠行为需按定制目标确认 |
| Batstep | 朝视线方向短距离瞬移，有蝙蝠粒子和眩晕 | `NyctoPowers`、`NyctoParticleTypes` | 路径伤害、眩晕时长、粒子节奏需实测 |
| Blood Barrier | 三层血幕保护，受击破层，有环绕视觉 | `VampirePowerEvents`、`AddBloodBarrierParticlesPayload`、`BloodBarrierLayer` | 木桩和蒜戟已能绕过血幕；仍需检查实体吸血鬼表现 |
| Blood Flechettes | 发射血刃，命中伤害、阻断治疗、吸血反馈 | `BloodFlechetteRenderer`、能力逻辑 | 命中声音、治疗阻断和吸血反馈需逐项验收 |
| Bloodrush | 向前冲刺，带血色冲刺光效 | `SyncBloodrushPayload`、客户端渲染事件 | 后进入视野同步已补；光效细节需对照原版 |
| Carnage | 狂暴增伤，目标快速流血，有红色覆盖/aura | `SyncCarnagePayload`、`CarnageClientState` | 玩家 aura 有基础；Dark Form 专用 aura 已接入，仍需 runClient 验收透明度和贴图滚动 |
| Dark Form | 变成大型暗黑形态，有专用模型、动画、攻击体验 | `DarkFormRenderer`、`DarkFormClientRenderEvents`、`DarkFormAnimation` | 第三人称持物层和 Carnage aura 已接入；第一人称爪臂与原版姿态仍需视觉微调 |
| Haemogenesis | 快速回血、清除治疗阻断、灭火 | `NyctoPowers` | 冷却、血耗、声音需对照原版 |
| Hypnotize | 催眠目标，玩家短暂眩晕，潜行可解除 | `NyctoPowers`、`NyctoMobEffects` | `hypnotized`/`stunned` 已注册；粒子、图标、停止行为需 runClient |
| Keen Senses | 高亮血型/生命、透墙感知、降低噪音 | `KeenSensesClientRenderEvents`、`SyncKeenSensesPayload` | 后处理、心跳、轮廓强度需视觉验收 |
| Mist Form | 迷雾化、隐身、免摔、穿过部分方块、首次伤害抵消 | `MistFormClientState`、`SyncMistFormPayload` | 穿墙规则、第一人称隐藏和碰撞体验需更接近原版 |
| Vampiric Thrall | 永久奴役生物并管理行为 | `VampiricThrallEvents`、`SyncThrallPayload` | AI、特殊生物行为、喂食、自愈、传送、模式反馈仍未完整 |

## 7. 仆从系统复刻标准

玩家应看到的结果：

- 吸血鬼能把合适生物变成仆从。
- 仆从外观发生吸血鬼化变化。
- 仆从可切换行为，例如跟随、停留、游荡、防御。
- 仆从能被喂食，能保护主人，必要时跟随传送。
- 村民、女巫、猪灵、唤魔者召唤物、马、狼等特殊生物有原版对应行为。

当前代码：

- 服务端行为：`src/main/java/moriyashiine/nycto/neoforge/event/VampiricThrallEvents.java`
- 客户端皮肤：`src/main/java/moriyashiine/nycto/neoforge/client/event/VampiricThrallClientEvents.java`
- 网络同步：`src/main/java/moriyashiine/nycto/neoforge/network/SyncThrallPayload.java`
- 能力入口：`src/main/java/moriyashiine/nycto/neoforge/power/NyctoPowers.java`

当前已接近原版的点：

- 转化目标查找已改为“看向的 Mob”，不会再因为战斗目标过滤导致动物等目标被提前排除。
- 已有仆从客户端状态同步和皮肤替换方向。
- 后进入视野的玩家会收到仆从状态补发，降低皮肤闪回普通生物的概率。

剩余差异：

- 尚未完整等价原版 `VampiricThrallComponent` 行为。
- 仆从自愈、饮血、共享血泉记忆、主人失去能力后解绑等机制不足。
- 防御模式目标过宽，容易打到不该打的目标。
- 特殊生物行为不完整，例如女巫、村民、猪灵、唤魔者召唤物、马、狼。
- 仆从模式和喂食提示仍需全部语言文件化。

验收标准：

- 每一种模式切换后，玩家能用肉眼看出行为变化。
- 退出重进、跨维度、远离后回来，仆从外观和归属不闪回原版状态。
- 功能清单列出的特殊生物类型逐项测试。

## 8. 猎人路线复刻标准

玩家应看到的结果：

- 能采集蒜并制作猎人工具。
- 木桩、木桩弩、蒜戟、蒜药水、蒜花环、火焰弹能有效克制吸血鬼。
- 猎人护甲成套后有逐级效果。
- 猎人契约能召唤或指挥猎人追踪目标。

当前代码：

- 猎人注册与道具：`src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java`
- 猎人工具辅助：`src/main/java/moriyashiine/nycto/neoforge/hunter/NyctoHunterUtil.java`
- 木桩物品：`src/main/java/moriyashiine/nycto/neoforge/item/WoodenStakeItem.java`
- 木桩投射物：`src/main/java/moriyashiine/nycto/neoforge/entity/projectile/WoodenStakeProjectile.java`
- 猎人实体：`src/main/java/moriyashiine/nycto/neoforge/entity/hunter/HunterEntity.java`
- 猎人渲染：`src/main/java/moriyashiine/nycto/neoforge/client/renderer/HunterRenderer.java`

当前已接近原版的点：

- 木桩、木桩投射物、蒜戟可以绕过 Blood Barrier。
- 猎人实体已经从普通玩家人形模型切换为 NeoForge 版 `HunterModel`，复用了原版三角帽、皮大衣、围巾、胸带、装饰花和衣摆几何。
- 猎人渲染器已经接入 `ItemInHandLayer`，手持戟、木桩、弩等武器会随模型手臂显示。
- 猎人契约生成的猎人现在会保存 `UltimateTarget`、`ContractPos`、`ContractPathTicks`；吸血鬼玩家会成为持久追杀目标，非目标玩家会让猎人前往契约地点。
- 和平难度下使用猎人契约会失败，不再生成敌对猎人。
- 猎人现在默认携带木桩和弩，远距离会切到弩进行射击，近距离会切回木桩近战，更接近原版 `Pillager` 派生猎人的远近武器切换。
- 猎人导航已允许开门，并接入弩持握/拉弩姿态，战斗时不再只是普通近战怪。
- 猎人护甲已有四段效果方向：
  - 1 件：阻止吸血鬼吸血。
  - 2 件：对附近吸血鬼施加蒜气场。
  - 3 件：降低木桩冷却。
  - 4 件：降低吸血鬼暴击威胁。
- 戟和涂蒜戟已补回长柄武器手感，主手持有时增加 `ENTITY_INTERACTION_RANGE +0.5`。

剩余差异：

- 猎人契约 AI 已有目标和地点持久化，但仍未完全等价原版 `Pillager` 派生行为。
- 猎人实体已补开门、弩远程攻击、远近距离主副手武器切换；仍缺骑马猎人、完整 Pillager 袭击兼容和更细的 arm pose 表现。
- 戟手持模型已接入旧式 item model override，但仍必须在 runClient 中检查第一人称和第三人称是否真的像长柄武器。
- 蒜药水目前更像自定义物品逻辑，尚未完整等价原版 potion 系统。
- 猎人护甲视觉依赖 vanilla 护甲层，未完整移植原版专用 `HunterArmorRenderer`。
- 新接入的猎人模型需要 runClient 查看三角帽、外套、衣摆动画和手持武器位置是否完全贴合原版。

验收标准：

- 吸血鬼开 Blood Barrier 时，被木桩、木桩弩、蒜戟攻击应受到有效伤害，而不是被血幕吃掉。
- 猎人护甲穿 1、2、3、4 件分别有可观察效果。
- 戟在第一人称和第三人称都应像原版长柄武器，而不是普通贴图贴手。

## 9. 祭坛、成长和弱点展示

玩家应看到的结果：

- 祭坛 UI 能展示能力、弱点、材料、血瓶、经验、选择状态。
- 选择能力后，玩家能力列表和 HUD 更新。
- 弱点可以作为展示项、记录项或兼容数据存在，但不能触发本项目不想要的强制副作用。

当前代码：

- 方块：`src/main/java/moriyashiine/nycto/neoforge/block/VampireAltarBlock.java`
- 菜单：`src/main/java/moriyashiine/nycto/neoforge/menu/VampireAltarMenu.java`
- 界面：`src/main/java/moriyashiine/nycto/neoforge/client/screen/VampireAltarScreen.java`
- 数据：`src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- 能力注册：`src/main/java/moriyashiine/nycto/neoforge/power/NyctoPowerRegistry.java`

当前差异：

- `NyctoData` 当前会过滤被拒绝的弱点，导致弱点区域展示不够接近原版。
- 祭坛 `stillValid()` 需要确认距离规则，玩家离太远后不应继续操作。
- 祭坛升级材料选择比原版简单，原版更接近按 UUID/世界种子稳定随机。
- UI 文字仍可能有英文直写，应全部走 `en_us.json` 和 `zh_cn.json`。

推荐目标：

- 弱点可以进入“展示/记录/兼容数据”，但不执行惩罚。
- UI 交互距离必须按 NeoForge 容器规则校验。
- 所有按钮、提示、失败原因必须使用语言 key。

## 10. 视觉复刻总标准

完美复刻不能只看功能。Nycto 的辨识度来自模型、HUD、粒子、声音、动画和第一人称表现。

当前资源总体情况：

- `src/main/generated` 已纳入资源输出。
- `blockstates`、`item models`、`sounds.json` 会参与构建。
- 粒子 JSON 指向的 `textures/particle/**` 基本完整。
- 目前没有发现必然导致启动失败的 P0 资源缺失。

仍需重点处理的视觉差异：

### 10.1 戟类手持模型

资源：

- `src/main/generated/assets/nycto/models/item/halberd_in_hand.json`
- `src/main/generated/assets/nycto/models/item/garlic_coated_halberd_in_hand.json`
- `src/main/generated/assets/nycto/models/item/halberd.json`
- `src/main/generated/assets/nycto/models/item/garlic_coated_halberd.json`

当前状态：

- 已通过 1.21.1 兼容的 `models/item` override 接入已有 3D 手持模型。
- 已在客户端 item property 中按“实体持有时”切换到 `*_in_hand`。

仍需验收：

- 物品栏和地面显示应保持普通图标。
- 第一人称持有时应显示长柄武器。
- 第三人称其他玩家持有时应显示长柄武器。

### 10.2 Hypnotized 和 Stunned 图标

资源：

- `src/main/resources/assets/nycto/textures/mob_effect/hypnotized.png`
- `src/main/resources/assets/nycto/textures/mob_effect/stunned.png`

当前状态：

- `NyctoMobEffects` 已注册 `hypnotized` 和 `stunned`。
- `Hypnotize` 和眩晕类能力已开始使用这些效果。

仍需验收：

- 状态栏是否显示正确图标。
- 图标是否与原版 Nycto 风格一致。
- 效果结束后图标是否消失。

### 10.3 Dark Form

当前代码：

- `src/main/java/moriyashiine/nycto/neoforge/client/renderer/DarkFormRenderer.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/model/DarkFormModel.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/animation/DarkFormAnimation.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/event/DarkFormClientRenderEvents.java`

仍需重点：

- 第三人称实体替身是否完整。
- 第一人称爪臂是否像原版。
- Carnage aura 是否在 Dark Form 上正确显示。当前已有 `DarkFormCarnageAuraLayer`，需要 runClient 看透明度、贴图滚动和翅膀遮挡是否自然。
- Dark Form 是否有持物层，或者按原版规则隐藏工具。当前已有 `ItemInHandLayer`，需要确认原版期望是显示持物还是隐藏工具。

### 10.4 专用护甲渲染

原版有专用：

- `VampireArmorRenderer`
- `HunterArmorRenderer`
- `WolfHunterArmorLayer`

当前状态：

- 资源中已有护甲贴图。
- 当前更多依赖 vanilla 护甲层和 `textures/models/armor/*_layer_1/2.png`。

差异：

- 能显示不等于像原版。
- 原版专用模型轮廓、遮挡和部件细节尚未完整复刻。

### 10.5 仆从皮肤

原版对多类生物有专用仆从皮肤，例如村民、猪灵、女巫、马、狼、唤魔者召唤物等。

当前需要逐项检查：

- 转化后是否立刻变皮。
- 远离回来是否保持。
- 重进世界是否保持。
- 旁观者是否看到同样皮肤。
- 特殊生物是否用正确的专用贴图。

## 11. 声音复刻标准

声音不是装饰。Nycto 里很多能力靠声音提示状态变化，缺声音会让玩家感觉“功能像没触发”。

当前声音注册：

- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java`
- `src/main/generated/assets/nycto/sounds.json`

必须覆盖的声音类型：

| 类型 | 玩家期望 |
| --- | --- |
| 吸血鬼实体 | 待机、受伤、死亡有吸血鬼声音 |
| 猎人实体 | 待机、受伤、死亡有猎人声音 |
| 血瓶饮用 | 饮血有清楚反馈 |
| 匕首抽血 | 命中、蓄血、提取血瓶有反馈 |
| 血幕 | 使用、命中、破碎有反馈 |
| Bloodrush | 启动和冲刺有反馈 |
| Batstep | 瞬移有反馈 |
| Bat Form | 开启/维持/结束有反馈 |
| Dark Form | 开启和攻击有反馈 |
| Hypnotize | 施法、反向催眠、目标受影响有反馈 |
| Keen Senses | 开启和心跳有反馈 |
| Mist Form | 开启和维持有反馈 |
| 仆从转化 | 转化成功有反馈 |

当前风险：

- 部分声音事件已经在 `sounds.json` 中，但代码未必都调用。
- 有些实体声音仍可能使用 vanilla 声音。
- 能力声音需要实测“是否太密、太吵、或没播”。

## 12. 汉化标准

玩家期望：

- 中文环境下，所有物品、方块、能力、提示、错误原因、按钮都显示中文。
- 英文环境下，也不能出现硬编码乱码或未翻译 key。

当前语言文件：

- 英文：`src/main/resources/assets/nycto/lang/en_us.json`
- 中文：`src/main/resources/assets/nycto/lang/zh_cn.json`

代码规则：

- 不要在代码里直接写玩家可见英文句子。
- 应使用 `Component.translatable("message.nycto.xxx")` 或类似语言 key。
- 每新增一个提示，必须同时补 `en_us.json` 和 `zh_cn.json`。

已知高风险文件：

- `src/main/java/moriyashiine/nycto/neoforge/power/NyctoPowers.java`
- `src/main/java/moriyashiine/nycto/neoforge/block/VampireAltarBlock.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java`
- `src/main/java/moriyashiine/nycto/neoforge/block/BloodFountainBlock.java`
- `src/main/java/moriyashiine/nycto/neoforge/block/CoffinBlock.java`

验收方法：

- 搜索 `Component.literal`。
- 搜索玩家提示中的英文句子。
- 搜索 `message.nycto`、`item.nycto`、`block.nycto`、`effect.nycto` 是否在中英文语言文件都存在。
- 中文客户端进游戏后逐项查看物品、能力、祭坛、提示。

## 13. 当前差异总表

| 优先级 | 差异 | 玩家会看到的问题 | 代码区域 | 修复方向 |
| --- | --- | --- | --- | --- |
| P0/P1 | 吸血安全规则仍需完整对照 | 某些目标可被不合理吸血，或原版能吸但当前不稳定 | `VampireLifecycleEvents`、`NyctoBloodUtil` | 按原版睡眠、血量、形态、目标类型逐项补齐 |
| P1 | 匕首系统不完整 | 蓄血、提瓶、自伤取血不像原版 | `VampiricDaggerItem`、配方与事件 | 补齐匕首蓄血和血瓶提取流程 |
| P1 | 玩家血瓶变体不足 | 不同玩家血液、普通血瓶、吸血鬼血瓶区分不够 | `NyctoItems`、血瓶相关数据 | 补齐变体模型、数据和交互 |
| P1 | Bat Form 第一人称需实测 | 翼片可能位置不自然或遮挡过多 | `BatFormClientRenderEvents` | runClient 后按截图微调 |
| P1 | Dark Form 视觉层需实测 | 暗黑形态狂暴或持物时可能仍不像原版 | `DarkFormRenderer`、`DarkFormClientRenderEvents` | Carnage aura 和持物层已接入；runClient 后按原版姿态微调 |
| P1 | 仆从 AI 不完整 | 仆从不够聪明，模式行为不像原版 | `VampiricThrallEvents` | 复刻原版组件和特殊生物逻辑 |
| P1 | 猎人契约仍需原版 AI 细化 | 猎人追踪和基础战斗已接近原版，但仍缺骑马猎人和完整 Pillager 行为 | `HunterEntity`、猎人契约物品 | 已补目标/地点/2400 tick 持久化、开门、弩远程、远近武器切换；后续补骑马和类型系统 |
| P1 | 祭坛弱点展示不足 | 祭坛不像原版那样展示弱点 | `NyctoData`、`VampireAltarScreen` | 弱点可展示但不执行副作用 |
| P1 | 戟手持模型需验收 | 可能仍像普通贴图 | generated item models、`NyctoClientNeoForge` | runClient 检查第一/第三人称 |
| P1 | 猎人模型需实测 | 专用模型已接入，但可能需要姿态微调 | `HunterRenderer`、`HunterModel` | runClient 检查三角帽、皮大衣、手持物和攻击动画 |
| P1 | 状态效果图标需验收 | 催眠/眩晕可能不显示或不对 | `NyctoMobEffects` | runClient 查看图标和语言 |
| P2 | 护甲专用模型不足 | 护甲能穿但不够像原版 | `NyctoArmorMaterials`、客户端 layer | 评估移植原版 ArmorRenderer |
| P2 | 粒子供应器简化 | 粒子有但节奏和外观不完全像原版 | `NyctoClientNeoForge`、粒子类 | 按原版粒子 provider 逐个补 |
| P2 | 狼人线资源未接入 | 相关资源存在但玩家用不到 | `NyctoHunterContent` | 若目标包含狼人线，再单独接入 |

## 14. 建议实现顺序

为避免“补了很多但玩家感觉不明显”，建议按这个顺序推进：

1. 主流程和 HUD：转化、吸血、血瓶、血量、血幕、饥饿条替换先稳定。
2. 每个能力的玩家可见表现：按“按键 -> 血耗 -> 冷却 -> 服务端效果 -> 客户端视觉 -> 声音 -> 结束同步”走完。
3. 形态类能力：Bat Form、Mist Form、Dark Form 优先，因为它们最容易在第一人称、第三人称和多人同步上出问题。
4. 仆从系统：先补通用仆从数据和行为，再补特殊生物。
5. 猎人路线：先确保木桩、蒜戟、猎人护甲克制关系正确，再补猎人契约和 AI。
6. 视觉精修：戟手持、护甲专用渲染、Carnage aura、状态图标、粒子节奏、声音。
7. 汉化和体验清理：全量搜索硬编码文字，统一语言 key，清理未翻译 key。

## 15. 每次改动后的验收方法

### 15.1 自动构建

每完成一组修改后运行：

```powershell
.\gradlew.bat build --offline
```

通过标准：

- 构建成功。
- 没有新增资源解析错误。
- 没有网络 payload 注册错误。
- 没有 mixin 应用失败。

### 15.2 runClient 手工验收

运行：

```powershell
.\gradlew.bat runClient --offline
```

进入游戏后至少检查：

- 新建世界能进入。
- 物品栏搜索 `nycto` 不崩溃。
- 吸血鬼、猎人、Dark Form、投射物能生成并显示。
- 血滴 HUD 出现且不遮挡。
- 每个能力都能启动、显示、结束。
- 多人或远近视野同步相关能力不闪回普通玩家或普通皮肤。

### 15.3 视觉验收

每个视觉功能至少截图一次，记录：

- 第一人称是否正确。
- 第三人称是否正确。
- 旁观者视角是否正确。
- 粒子是否出现。
- 声音是否播放。
- HUD 是否遮挡。
- 中文是否正常。

## 16. 当前已完成的关键改进记录

本节记录已经落到当前 NeoForge 代码里的改进，避免后续重复排查。

- `NyctoMobEffects` 已注册 `hypnotized` 和 `stunned`。
- `Hypnotize` 能力现在会给目标施加 Nycto 自己的 `Hypnotized` / `Stunned` 状态，而不是只用原版减速和发光模拟。
- `Batstep` 及范围/路径伤害现在使用 `Stunned` 表达眩晕。
- `VampirePowerEvents` 已在服务端 tick 中处理 `Stunned`，被眩晕实体会短暂停止移动、停止寻路并清除攻击目标。
- `VampiricDaggerItem` 攻击吸血时会生成血液粒子并播放吸血声音；提取血瓶时也会播放反馈声音。
- `Vampire` 实体攻击有优质血液的目标时会扣目标血液、回血并播放吸血声音。
- `VampireBloodEvents` 的血幕保命现在不会吞掉木桩、木桩投射物、蒜戟等吸血鬼克制伤害。
- `VampireLifecycleEvents` 的潜行吸血不再要求副手为空。
- 玩家血液已满时，不会继续吸血浪费目标血量。
- 潜行吸血的“安全吸血”规则更接近原版：催眠目标可安全吸到不致死；半血以上目标只有睡眠或玩家处于 Mist Form 时才算安全。
- `halberd` 和 `garlic_coated_halberd` 已通过 1.21.1 兼容的 `models/item` override 接入 3D 手持模型。
- 戟和涂蒜戟主手持有时增加 `ENTITY_INTERACTION_RANGE +0.5`，更接近原版长柄武器手感。
- 猎人实体待机、受伤、死亡声音已经从 vanilla 村民声音切换到 `NyctoSoundEvents.HUNTER_AMBIENT`、`HUNTER_HURT`、`HUNTER_DEATH`，对应字幕中英文已存在。
- NeoForge 版 `HunterModel` 已接入客户端，猎人现在使用原版风格的三角帽、皮大衣、围巾、胸带、装饰花和衣摆模型，不再使用普通玩家人形模型。
- `HunterRenderer` 已使用 `HunterModel` 并接入 `ItemInHandLayer`，猎人手持武器会按专用模型手臂渲染。
- `HunterEntity` 已补 `UltimateTarget`、`ContractPos`、`ContractPathTicks` 的 NBT 持久化，并接入 `UltimateTargetGoal` / `PathToContractPosGoal`。
- 猎人契约现在在和平难度失败；对吸血鬼生存玩家会设置持久追杀目标，对非目标玩家会设置契约地点。
- 猎人默认装备已改为主手木桩、副手弩；战斗时会按距离自动切换，远距离使用弩，近距离切回木桩。
- 猎人已接入 `OpenDoorGoal`，并在 `HunterModel` 中补了弩持握/拉弩姿态。
- Dark Form 第三人称渲染已接入 `ItemInHandLayer`，模型本身的 `translateToHand` 会把手持物定位到 Dark Form 手爪。
- Dark Form + Carnage 已接入 `DarkFormCarnageAuraLayer`，使用 `textures/entity/carnage/carnage_aura.png` 给 Dark Form 叠加专用狂暴光效。
- `BatFormClientRenderEvents` 不再只是隐藏第一人称手臂，而是在 `RenderArmEvent` 中渲染 vanilla BatModel 的左右翼片。
- `VampirePowerEvents.onStartTracking` 已补发普通 Mob 仆从状态，降低仆从皮肤闪回普通生物的概率。

最近一次已确认：

- `.\gradlew.bat build --offline` 已在接入猎人专用模型、猎人契约持久化、猎人远近武器切换、猎人弩姿态、猎人声音、Dark Form 持物层、Dark Form Carnage aura 后通过。

仍需立刻 runClient 验收：

- Hypnotized / Stunned 图标是否在状态栏正确显示。
- Hypnotize 粒子、目标停止、持续时间是否接近原版。
- Batstep 命中后的眩晕表现是否自然。
- 匕首攻击和提取血瓶的声音/粒子是否过多或过少。
- 吸血鬼实体攻击村民/玩家时的吸血反馈是否明显。
- 戟和涂蒜戟在物品栏、地面、第一人称、第三人称是否按预期切换。
- 猎人待机、受伤、死亡时是否播放 Nycto 猎人声音，而不是村民声音。
- 猎人模型的三角帽、皮大衣、围巾、衣摆动画和手持武器位置是否与原版视觉一致。
- 猎人契约生成后，重进世界或远离回来时是否仍追杀吸血鬼目标或前往契约地点。
- 猎人远距离是否真的切弩射击，近距离是否切回木桩，开门和弩姿态是否自然。
- Dark Form 第三人称持物位置是否自然，是否符合原版期望。
- Dark Form + Carnage 时 aura 是否正确包裹身体和翅膀，透明度是否过亮或过暗。
- Bat Form 第一人称翼片位置、大小、朝向是否自然，是否遮挡过多屏幕。
- 仆从远离后再进入视野、玩家重进世界、多人旁观时，仆从皮肤是否稳定保持。

## 17. 最终“完美复刻”判定

可以认为当前移植版接近完美复刻时，应同时满足：

1. 功能清单里的吸血鬼路线、猎人路线、核心物品、核心能力都能从生存模式自然获得和使用。
2. 原版 Nycto 中玩家能明显看到的模型、HUD、粒子、声音、UI，在当前版本都有等价表现。
3. 服务端状态和客户端视觉一致，不出现别人看你还是普通玩家、重进后皮肤闪回、HUD 不更新等问题。
4. NeoForge 1.21.1 的注册、网络、客户端事件、资源路径规则都没有被绕开。
5. 中文环境没有乱码、英文直写或未翻译 key。
6. 白天不怕阳光、能力组合无强制副作用这两个定制目标被保留。

这份文档应作为后续修复、子任务分配和 runClient 验收的基准。每补完一项，应更新“当前差异总表”，并用实际游戏截图或测试记录证明它已经从“差异”变成“已对齐”。
