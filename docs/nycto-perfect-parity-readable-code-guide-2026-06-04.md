# Nycto 1.21.1 NeoForge 当前版本完美复刻说明书

日期：2026-06-04  
当前工程：`D:\mcmodding\nycto`  
原版参考仓库：<https://github.com/MoriyaShiine/nycto>  
玩家功能清单：`C:\Users\Lenovo\Desktop\吸血鬼功能清单.txt`

这份文档的目标不是只列“还有哪些活没做”。它要同时说明三件事：

1. 非技术玩家应该看到什么，才算“像原版 Nycto”。
2. 当前 1.21.1 NeoForge 移植版已经做到哪里，差在哪里。
3. 开发时应该改哪些具体代码，并且哪些 NeoForge 规则不能违反。

## 1. 本版本的复刻边界

当前目标是尽量完整复刻原版 Nycto 的玩法、视觉、声音、HUD、实体表现和界面体验，但保留用户已经明确决定的两个定制差异：

| 项目 | 原版 Nycto | 当前版本必须保留的决定 |
| --- | --- | --- |
| 白天阳光 | 吸血鬼会被阳光惩罚，甚至受伤 | 不恢复白天怕阳光，不让玩家因为太阳直接受伤 |
| 弱点副作用 | 祭坛能力和弱点组合会带来负面效果 | 不恢复强制副作用，弱点可以显示、记录或兼容，但不能强制惩罚玩家 |

这两点不是缺失功能，而是当前移植版的设计规则。后续任何“完美复刻”都不能把它们改回原版。

## 2. 先看清当前哪些代码真的生效

这是当前项目最容易误判的地方。

仓库里保留了大量原版代码，例如 `src/main/java/moriyashiine/nycto/common/**`、`src/main/java/moriyashiine/nycto/client/**`、`src/main/java/moriyashiine/nycto/mixin/**`。这些文件对照原版很有价值，但当前 NeoForge 构建不是全量使用它们。

当前真正进入构建的核心范围是：

| 作用 | 当前有效代码 |
| --- | --- |
| NeoForge 主入口 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\common\NyctoNeoForge.java` |
| 当前移植版玩法、物品、实体、客户端、网络包 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\**` |
| 当前 NeoForge mixin | `D:\mcmodding\nycto\src\main\resources\nycto-neoforge.mixins.json` |

不能把旧的 `src/main/resources/nycto.mixins.json` 整套打开。那套 mixin 面向旧结构，直接启用很容易造成启动崩溃、客户端渲染错误或服务端类加载错误。

## 3. NeoForge 1.21.1 的硬规则

这些规则是后续复刻时的安全边界。

| 规则 | 非技术解释 | 当前项目落点 |
| --- | --- | --- |
| 注册必须走固定时机 | 方块、物品、声音、实体不能在游戏运行中临时塞进去，必须在加载阶段登记好 | `NyctoItems`、`NyctoBlocks`、`NyctoEntityTypes`、`NyctoHunterContent`、`NyctoSoundEvents`、`NyctoParticleTypes`、`NyctoMenuTypes` |
| 服务端决定玩法 | 是否吸血、是否扣血、是否命中、是否变身，必须由服务端决定 | `NyctoData`、`NyctoPowers`、`VampirePowerEvents`、`VampireLifecycleEvents` |
| 客户端只负责表现 | 模型、HUD、粒子、后处理、第一人称手臂只负责显示，不能自己决定真实玩法 | `NyctoClientNeoForge`、`NyctoHudLayer`、`DarkFormClientRenderEvents`、`KeenSensesClientRenderEvents` |
| 网络包负责同步 | 服务端状态变了，要告诉客户端，否则别人看不到你的变身或 HUD 不刷新 | `NyctoPayloads` 和所有 `Sync*Payload` |
| mixin 最小化 | 只有事件做不到的视觉或碰撞规则才用 mixin，并且只写当前 NeoForge 专用 mixin | `nycto-neoforge.mixins.json` |

参考文档：

- NeoForge 1.21.1 网络包：<https://docs.neoforged.net/docs/1.21.1/networking/>
- NeoForge 1.21.1 方块和注册：<https://docs.neoforged.net/docs/1.21.1/blocks/>
- NeoForge 事件概念：<https://docs.neoforged.net/docs/1.21.4/concepts/events/>
- 1.21.1 `PayloadRegistrar` API：<https://lexxie.dev/neoforge/1.21.1/net/neoforged/neoforge/network/registration/PayloadRegistrar.html>

## 4. 当前版本一句话判断

当前版本已经有完整移植骨架：吸血鬼身份、血液数据、能力热栏、祭坛、部分能力、猎人路线、投射物、HUD、声音和粒子入口都已经存在。

但它还不是“完美复刻”。主要差距在四类：

| 差距类型 | 说明 |
| --- | --- |
| 流程差距 | 有些玩法能触发，但步骤不像原版，例如转化、吸血、匕首蓄血、仆从管理 |
| 视觉差距 | 原版贴图和模型资源很多已经在仓库里，但不一定被当前 NeoForge 代码调用 |
| AI 差距 | 仆从、猎人、吸血鬼实体的目标选择和行为还需要按原版补齐 |
| 验收差距 | 一些功能构建能过，但没有逐项用 `runClient`、第一人称、第三人称、多实体场景验证 |

## 5. 主线玩法复刻目标

### 5.1 成为吸血鬼

玩家应该看到的原版体验：

1. 夜晚在合适生物群系遇到吸血鬼。
2. 击杀吸血鬼后掉落吸血鬼血瓶。
3. 喝下血瓶后进入 Vampirism 转化状态。
4. 转化完成后，血液 HUD 替代普通饥饿体验，能力热栏出现。

当前代码落点：

| 内容 | 当前文件 |
| --- | --- |
| 吸血鬼实体 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\Vampire.java` |
| 实体注册 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\registry\NyctoEntityTypes.java` |
| 血瓶物品 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\NyctoItems.java` |
| Vampirism 状态效果 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\registry\NyctoMobEffects.java` |
| 玩家吸血鬼状态 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\NyctoData.java` |
| 转化倒计时完成 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\NyctoGameplay.java` |
| HUD | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\gui\NyctoHudLayer.java` |

差异和修复方向：

| 项目 | 当前风险 | 应该怎么修 |
| --- | --- | --- |
| 转化过程 | 已补 30 秒 `Vampirism` 状态、状态图标、倒计时结束变身、完成音效、中文完成提示 | 后续 runClient 验证状态图标显示、喝奶/死亡/重进世界时是否符合原版 |
| 掉落表 | 需要确认吸血鬼血瓶掉落率和原版一致 | 核对 `loot_table/entities/vampire.json` 和运行内实际击杀结果 |
| 视觉 | 需要确认吸血鬼模型、眼睛发光、叫声都使用原版资源 | 对照 `vampire.png`、`vampire_eyes.png`、`sounds.json` 和客户端渲染 |

验收标准：

- 生存模式能自然完成“遇到吸血鬼 -> 击杀 -> 喝血瓶 -> 成为吸血鬼”。
- 中文环境下所有提示可读，不出现裸 key。
- 转化后白天不因阳光受伤，这是定制规则，不算缺失。

### 5.2 血液、吸血、血瓶和匕首

玩家应该看到的原版体验：

1. 吸血鬼有自己的血液资源。
2. 潜行空手右键可吸血。
3. 吸血时有蓄力条、红色血液粒子和声音。
4. 吸血鬼匕首攻击类人生物会蓄血。
5. 匕首蓄满后外观变为血红，并能提取血瓶。

当前代码落点：

| 内容 | 当前文件 |
| --- | --- |
| 血液数据 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\NyctoData.java` |
| 吸血交互 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampireLifecycleEvents.java` |
| 血液工具 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\util\NyctoBloodUtil.java` |
| 匕首 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\item\VampiricDaggerItem.java` |
| 匕首满血模型条件 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\NyctoClientNeoForge.java` 和 `D:\mcmodding\nycto\src\main\generated\assets\nycto\models\item\vampiric_dagger.json` |
| HUD | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\gui\NyctoHudLayer.java` |

差异和修复方向：

| 项目 | 当前风险 | 应该怎么修 |
| --- | --- | --- |
| 吸血目标过滤 | 已让 `NyctoBloodUtil` 使用 `has_no_blood`、`has_quality_blood` 标签，并继续排除创造/旁观/吸血鬼/亡灵 | 继续核对标签内容是否覆盖原版所有实体 |
| 目标血液池 | 已新增 100 点实体血液池；吸血和匕首都会消耗目标血液，低血量目标会虚弱并流血 | 后续需要把准星目标血量数字/颜色和更多原版安全吸血条件补齐 |
| 吸血反馈 | 已有血液粒子、声音、弱化、按优质/普通血差异补血；HUD 已补空手潜行瞄准可吸血目标时的红色吸血准备条 | 目前准备条是客户端视觉提示，服务端仍只在实际交互时决定是否成功 |
| 匕首模型和进度 | 已注册 1.21.1 item property `nycto:full_dagger`，模型有 predicate override；HUD 已补手持匕首血量进度条 | 仍需 runClient 手持确认满血红刀显示、进度条位置不压住能力热栏 |

验收标准：

- 对无血实体不能吸血，并有合理失败反馈。
- 对合适目标吸血会回血或补血液，目标受影响。
- 匕首从空到满有清晰可见的状态变化。

## 6. 祭坛和能力系统

玩家应该看到的原版体验：

1. 放置吸血鬼祭坛。
2. 打开专用 UI。
3. 放入材料、经验和血瓶后选择能力。
4. 能力图标、说明、当前能力、可选能力都清晰显示。
5. 可以切换能力顺序。

当前代码落点：

| 内容 | 当前文件 |
| --- | --- |
| 祭坛方块 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\block\VampireAltarBlock.java` |
| 祭坛菜单 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\menu\VampireAltarMenu.java` |
| 祭坛屏幕 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\gui\VampireAltarScreen.java` |
| 能力数据 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\power\NyctoPowerRegistry.java` |
| 能力交换网络包 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\SwapAltarPowersPayload.java` |
| 祭坛贴图 | `D:\mcmodding\nycto\src\main\resources\assets\nycto\textures\gui\container\vampire_altar.png` |

差异和修复方向：

| 项目 | 当前风险 | 应该怎么修 |
| --- | --- | --- |
| 弱点系统 | 原版会强制负面组合，当前不允许恢复 | 保留显示和兼容数据，但不要让弱点强制伤害玩家 |
| UI 细节 | 原版图标、提示框、槽位可能还没完全对齐 | 对照原版 `VampireAltarScreen` 和当前截图逐像素校准 |
| 服务端校验 | UI 点击不能只靠客户端相信玩家 | `VampireAltarMenu` 和网络包处理必须校验材料、经验、血瓶和上限 |

验收标准：

- 没材料时按钮不可用或点击无效。
- 材料足够时能力稳定加入玩家数据。
- 重新进世界后能力仍存在。
- 祭坛不会让玩家恢复阳光惩罚或强制副作用。

## 7. HUD 复刻目标

原版 HUD 不是装饰，它直接告诉玩家当前状态。

必须覆盖：

| HUD 内容 | 玩家看到什么 | 当前代码 |
| --- | --- | --- |
| 血液 | 血滴或血量条显示当前血液 | `NyctoHudLayer.renderBlood` |
| 能力热栏 | 屏幕下方显示能力图标和当前选择 | `NyctoHudLayer.renderPowerHotbar` |
| 冷却 | 能力不能用时有遮罩或倒计时 | `NyctoHudLayer`、`NyctoData.getCooldown` |
| 回血阻断 | 心形 HUD 变为阻断心 | `NyctoHudLayer.renderHealBlock` |
| 血液屏障层数 | 血屏障图标和层数 | `NyctoData.getBloodBarrierLayers` |
| 吸血准备 | 空手潜行并瞄准可吸血目标时出现红色准备条 | `NyctoHudLayer.renderHeldBloodToolProgress` |
| 匕首蓄血 | 手持已有血量的吸血匕首时出现红色进度条 | `NyctoHudLayer.renderHeldBloodToolProgress`、`VampiricDaggerItem.getBloodCharge` |
| Keen Senses | 屏幕后处理、目标轮廓、心跳信息 | `KeenSensesClientRenderEvents`、`KeenSensesGameRendererMixin` |
| Carnage | 狂暴覆盖效果 | `CarnageClientState` 和相关渲染 |
| 蓄力跳 | 原版有蓄力跳视觉 | 当前需要继续核对 |
| 阳光暴露 | 原版有阳光相关 HUD | 当前可以不恢复，或只作为无伤提示，不做惩罚 |

验收标准：

- HUD 不盖住原版快捷栏和经验条。
- 16:9、窗口化、小窗口都不重叠。
- 启用能力时 HUD 立即变化，关闭后立即消失。
- 没开 Keen Senses 的玩家不能看到 Keen Senses 的后处理和轮廓。

## 8. 十四个吸血鬼能力的当前复刻表

| 能力 | 原版体验 | 当前主要代码 | 当前判断 | 后续动作 |
| --- | --- | --- | --- | --- |
| Night Vision | 吸血鬼夜视 | `NyctoPowers`、`VampirePowerEvents` | 基础可用，需确认持续性和关闭条件 | 验证默认夜视和切换逻辑 |
| Bat Form | 变蝙蝠、飞行、耗血 | `SyncBatFormPayload`、`BatFormClientRenderEvents` | 接近可用 | 校准第一人称翅膀、碰撞、耗血节奏 |
| Bat Swarm | 召唤蝙蝠群攻击并吸血 | `NyctoPowers`、`BatSwarmEvents` | 已从一次性召唤推进为 30 秒持续蝙蝠云：会追踪玩家攻击/被攻击目标、周期性吸目标血、储血，并在玩家站进云团时返还血液 | runClient 校准云团位置、粒子密度、回血节奏和目标追踪 |
| Batstep | 短距瞬移，路径伤害和眩晕 | `NyctoPowers` | 有基础触发 | 校准距离、目标方块、粒子和音效 |
| Blood Barrier | 三层血环护身 | `BloodBarrierLayer`、`AddBloodBarrierParticlesPayload` | 基础较完整 | 校准破碎、命中、HUD 和粒子 |
| Blood Flechettes | 发射血刃，命中阻断回血 | `BloodFlechetteProjectile` | 基础可用 | 校准回血阻断时长、命中粒子 |
| Bloodrush | 冲刺、灭火、路径效果 | `BloodrushClientState`、`BloodrushAuraLayer` | 基础可用 | 校准冲刺距离、声音和轨迹 |
| Carnage | 狂暴加伤、目标流血 | `CarnageClientState`、`carnage_aura.png` | 基础可用 | 校准 Dark Form 下 aura 贴合 |
| Dark Form | 大型恶魔吸血鬼形态 | `DarkFormModel`、`DarkFormAnimation`、`DarkFormClientRenderEvents` | 已大幅补齐，仍需实机视觉校准 | F5、第一人称、多人视角逐项验收 |
| Haemogenesis | 快速回血、清除回血阻断、灭火 | `NyctoPowers` | 基础可用 | 校准消耗、声音、状态清除 |
| Hypnotize | 范围催眠、玩家眩晕 | `NyctoPowers` | 需要补 AI 和视觉节奏 | 补粒子、解除条件、目标行为 |
| Keen Senses | 高亮血量、心跳、降噪、后处理 | `KeenSensesClientRenderEvents`、`KeenSensesClientLevelMixin` | 已接近原版 | 多人验证只影响开启者 |
| Mist Form | 迷雾化、隐身、穿部分方块 | `MistFormBlockStateMixin`、`MistFormLivingEntityRendererMixin` | 基础可用，视觉偏弱 | 校准烟雾密度、受击退出 |
| Vampiric Thrall | 永久仆从、模式和专用皮肤 | `NyctoPowers.guideThralls`、`VampireLifecycleEvents`、`VampiricThrallEvents`、`ThrallLivingEntityRendererMixin`、`ThralledHorseHornsLayer`、`VampiricThrallClientEvents` | 已补目标限制、持久化、血瓶优先交互、原版成本/冷却、DEFEND 主动扫描、友军过滤、受伤复仇、村民/猪灵/袭击者转化特化、主体皮肤替换、马角 layer 和可转化目标提示 | 继续补血泉/自愈、特殊实体更细 AI、实机截图校准 |

## 9. Dark Form 专项

玩家应该看到：

- 普通玩家外观完全消失，替换成大型 Dark Form。
- F5 看自己、别人看自己、录像回放视角都不露普通玩家模型。
- 第一人称不是普通手臂，而是 Dark Form 爪臂。
- 动作包含待机、潜行待机、走、跑、跳、飞、左右攻击。
- Carnage 状态在 Dark Form 下也有正确 aura。

当前代码落点：

| 内容 | 当前文件 |
| --- | --- |
| 能力开关和耗血 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\power\NyctoPowers.java` |
| 每 tick 维护 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampirePowerEvents.java` |
| 模型 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\model\DarkFormModel.java` |
| 动画 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\animation\DarkFormAnimation.java` |
| 玩家替身渲染 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\event\DarkFormClientRenderEvents.java` |
| 隐藏普通玩家模型 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\mixin\client\MistFormLivingEntityRendererMixin.java` |
| 客户端状态 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\DarkFormClientState.java` |
| 同步包 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\network\SyncDarkFormPayload.java` |

当前差异：

| 项目 | 当前判断 |
| --- | --- |
| 模型几何 | 已补回关键原版几何，包括翼、红色下摆、尖耳、利爪 |
| 原版动画 | 已迁入 NeoForge 包，但仍需实机校准动画阶段 |
| 第一人称 | 已有爪臂替换，仍需校准位置、缩放、持物时遮挡 |
| 多人同步 | 需要两客户端或录像视角确认 |

子agent 视觉审计新增风险：

| 项目 | 风险 | 后续动作 |
| --- | --- | --- |
| Dark Form 重复渲染 | 已移除 `DarkFormVisualLayer` 注册和文件，当前只保留 `DarkFormClientRenderEvents` 后置渲染路径 | runClient 确认 F5 下只有一层 Dark Form |
| Dark Form 第三人称持物 | 普通玩家 layer 被隐藏后，Dark Form 替身需要自己处理持物 | 对照原版 `ItemInHandLayer` 效果补持物层 |
| Bat Swarm | 已新增 `BatSwarmEvents`，具备持续云、目标列表、储血、站入回血的服务端骨架 | 后续实机校准粒子密度、移动轨迹、目标选择和血液返还观感 |
| Bat/Mist 第一人称 | Bat Form 和 Mist Form 仍需确认普通手臂是否隐藏且替代视觉是否足够 | runClient 逐项截图验收 |

验收标准：

- 开启 Dark Form 后没有任何普通玩家皮肤外露。
- 拿物品时物品仍可见，爪臂不把屏幕遮死。
- 跳跃或飞行动作有明显翼部表现。
- 退出 Dark Form 后玩家模型、手臂和装备正常恢复。

## 10. Vampiric Thrall 专项

这是当前最容易“看起来有，但不像原版”的能力。

原版体验：

- 不是短时间控制，而是长期仆从。
- 可转化目标有限，不是所有怪都能变仆从。
- 村民、女巫、掠夺者、唤魔者、猪灵、马、狼等目标有各自规则。
- 马和狼必须已经被玩家驯服并属于玩家。
- 类人生物通常需要血量低于阈值才可转化。
- 仆从有模式：跟随、停留、游荡、防御。
- 仆从不会攻击主人，不会攻击友军。
- 仆从有专门的吸血鬼皮肤。

当前代码落点：

| 内容 | 当前文件 |
| --- | --- |
| 仆从转化 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\power\NyctoPowers.java` |
| 模式切换交互 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampireLifecycleEvents.java` |
| 仆从运行时事件 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\event\VampiricThrallEvents.java` |
| 仆从贴图资源 | `D:\mcmodding\nycto\src\main\resources\assets\nycto\textures\entity\vampiric_thrall\**` |
| 原版参考 AI/mixin | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\mixin\power\vampire\vampiricthrall\**` |

当前差异：

| 项目 | 当前状态 | 应该补到什么程度 |
| --- | --- | --- |
| 转化目标限制 | 已接入 `can_be_thralled` 和 `cannot_be_targeted_by_thralls` 标签，并补了马/狼主人检查、普通目标低血量检查 | 继续实机确认所有原版目标类型 |
| 持久化 | 转化时已写入主人 UUID、模式，并调用 `setPersistenceRequired()` | 退出重进仍需 runClient 实机验证 |
| 模式 | 已加入 FOLLOW、STAY、WANDER、DEFEND；DEFEND 已能主动扫描 16 格目标；马/狼这类驯服仆从不走普通模式切换 | 还需补远距传送和更多原生 AI 抑制 |
| 喂血 | 已改为拿血瓶潜行右键优先喂仆从，空手潜行右键才切模式 | 后续可补更完整的喂食音效、粒子和中文文本 |
| AI | 已新增 `VampiricThrallEvents`：仆从不能锁定主人、同主人的其他仆从和不应攻击目标；主人或仆从被攻击时，非 STAY 仆从会尝试反击合法攻击者 | 继续补更细的实体专属 AI，例如女巫、唤魔者、猪灵、村民工作脑等行为差异 |
| 特殊实体转化 | `NyctoPowers.applyThrallConversionRules` 已在转化时清理催眠负面状态；村民刷新大脑并校准速度；猪灵免疫僵尸化；袭击者脱离袭击并禁止重新加入 | 继续实机确认不同实体在世界重载、跨维度、袭击事件中的稳定性 |
| 皮肤 | 已新增 `SyncThrallPayload`、`ThrallClientState`、`ThrallLivingEntityRendererMixin` 和 `ThralledHorseHornsLayer`，能把已同步仆从替换成 `vampiric_thrall` 主体贴图，并给仆从马追加角 | 部分实体特化贴图和实机视觉校准仍需补 |
| 可转化提示 | 已新增 `VampiricThrallClientEvents`，当前选中 Vampiric Thrall 时会给可转化目标加催眠粒子，准星目标会有紫色 outline | 仍需实机确认粒子密度、outline 颜色和原版观感 |
| Bat Swarm 冲突 | 蝙蝠群已不再依赖临时原版蝙蝠实体，改由 `BatSwarmEvents` 维护持续云团 | 后续确认旧 `nycto_bat_swarm_owner`/`nycto_bat_swarm_until` 标记是否可以清理 |
| 成本和冷却 | Vampiric Thrall 已按原版审计校准为消耗 20 血、冷却 200 tick | 后续实机确认祭坛/HUD 中显示一致 |

验收标准：

- 转化村民后，退出重进仍是仆从。
- 潜行右键空手切换模式，拿血瓶时优先喂血。
- 仆从不会打主人。
- 仆从会保护主人。
- 村民、女巫、掠夺者、猪灵、马、狼至少各有正确外观或明确阶段性说明。

## 11. Bat Form、Mist Form、Keen Senses 的视觉验收

| 能力 | 原版视觉重点 | 当前代码 | 验收重点 |
| --- | --- | --- | --- |
| Bat Form | 玩家变成蝙蝠，别人也看到蝙蝠；第一人称不能露普通手 | `BatFormClientRenderEvents`、`SyncBatFormPayload` | F5、别人视角、飞行、耗血、退出恢复 |
| Mist Form | 玩家像烟雾，穿过允许方块，受击退出 | `MistFormBlockStateMixin`、`MistFormLivingEntityRendererMixin` | 不露普通模型，烟雾密度足够，碰撞稳定 |
| Keen Senses | 目标轮廓、心跳、生命信息、声音变闷、后处理 | `KeenSensesClientRenderEvents`、`KeenSensesClientLevelMixin`、`KeenSensesGameRendererMixin` | 只影响开启者，不污染其他玩家视角 |

## 12. 猎人路线复刻目标

玩家应该看到：

1. 大蒜可以自然生成、种植、收获。
2. 大蒜药水可以酿造，并克制吸血鬼。
3. 大蒜花环可以摆放，并影响附近吸血鬼。
4. 戟和涂蒜戟有长柄武器手感。
5. 木桩可以作为近战除魔武器，也能配合弩发射并插在地上。
6. 火焰弹是投射物，落地后产生火焰区域。
7. 牧师可交易猎人契约。
8. 猎人会追踪契约目标，不只是原地发呆。
9. 吸血鬼猎人护甲有对应外观和套装效果。

当前代码落点：

| 内容 | 当前文件 |
| --- | --- |
| 猎人内容总注册 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\registry\NyctoHunterContent.java` |
| 猎人实体 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\hunter\HunterEntity.java` |
| 猎人工具逻辑 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\hunter\**` |
| 木桩物品 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\item\WoodenStakeItem.java` |
| 木桩投射物 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\projectile\WoodenStakeProjectile.java` |
| 火焰弹 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\item\FirebombItem.java` |
| 火焰弹投射物 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\entity\projectile\FirebombProjectile.java` |
| 猎人渲染 | `D:\mcmodding\nycto\src\main\java\moriyashiine\nycto\neoforge\client\renderer\HunterRenderer.java` |

差异和修复方向：

| 项目 | 当前风险 | 应该怎么修 |
| --- | --- | --- |
| 猎人 AI | 当前能攻击吸血鬼，但契约追踪细节需要核对 | 对照原版 `PathToContractPosGoal`、`UltimateTargetGoal` 重写 NeoForge 版 |
| 护甲外观 | 资源存在，需确认当前 1.21.1 equipment 资源和渲染生效 | 在创造模式穿全套并截图核对 |
| 木桩 | 需要确认手持、弩发射、插地、命中克制都生效 | 分别用手、弩、地面方块三种方式验收 |
| 火焰弹 | 需要确认火焰范围和不造成灾难性连锁燃烧 | 实机在草地、石头、森林边缘测试 |

## 13. 模型、贴图、粒子、声音的统一要求

完美复刻不能只看数值。玩家第一眼看到的是视觉和声音。

| 类型 | 必须检查的资源或代码 |
| --- | --- |
| 吸血鬼实体 | `textures/entity/vampire/vampire.png`、`vampire_eyes.png`、`VampireRenderer` |
| 猎人实体 | `textures/entity/hunter/vampire_hunter.png`、`HunterRenderer` |
| Dark Form | `textures/entity/dark_form/dark_form.png`、`DarkFormModel`、`DarkFormAnimation` |
| Carnage | `textures/entity/carnage/carnage_aura.png` |
| Blood Barrier | `textures/entity/blood_barrier/blood_barrier.png`、HUD 图标 |
| 仆从 | `textures/entity/vampiric_thrall/**` |
| 投射物 | 木桩、乌头箭、血液飞刃贴图 |
| HUD | `textures/gui/sprites/hud/**` |
| 祭坛 | `textures/gui/container/vampire_altar.png` |
| 粒子 | `blood`、`bat_swarm_*`、`hypnosis_*`、`hypnotized`、`thralled` |
| 声音 | `sounds.json`、`NyctoSoundEvents` |

验收方法：

1. 创造模式逐个拿出物品、实体和能力。
2. 第一人称看手和持物。
3. F5 看自己。
4. 旁观者或第二客户端看玩家。
5. 开启字幕检查声音 key。
6. 截图确认没有紫黑缺失贴图。

## 14. 汉化要求

中文不是附加项。当前版本面向中文玩家，汉化必须和真实玩法一致。

当前文件：

| 语言 | 文件 |
| --- | --- |
| 英文 | `D:\mcmodding\nycto\src\main\resources\assets\nycto\lang\en_us.json` |
| 中文 | `D:\mcmodding\nycto\src\main\resources\assets\nycto\lang\zh_cn.json` |

必须覆盖：

- 物品名：`item.nycto.*`
- 方块名：`block.nycto.*`
- 实体名：`entity.nycto.*`
- 能力名和说明：`power.nycto.*`
- 弱点名和说明：`weakness.nycto.*`
- 祭坛 UI 文本
- HUD 文本
- 按键名
- 字幕：`subtitles.nycto.*`
- 进度文本
- 当前定制规则说明

中文说明必须明确：

- 当前版本吸血鬼白天不会因为阳光受伤。
- 弱点不会强制触发玩家不想要的组合副作用。

验收标准：

- 中文环境无裸 key。
- tooltip 不超出屏幕。
- 文本描述和实际玩法一致。

## 15. 后续开发优先级

如果目标是最快接近“完美复刻”，建议按这个顺序推进：

| 优先级 | 内容 | 原因 |
| --- | --- | --- |
| 1 | 完成 Vampiric Thrall 的目标限制、持久化、AI、喂血和皮肤 | 这是当前最大玩法差距 |
| 2 | 实机校准 Dark Form 的第一人称、动画、多人视角 | 这是最显眼的视觉能力 |
| 3 | 补齐吸血、匕首、血瓶、转化的流程反馈 | 主线体验必须顺 |
| 4 | 校准 Bat Form、Mist Form、Keen Senses 的第一人称和多人表现 | 这些能力很依赖视觉 |
| 5 | 补猎人契约 AI、护甲效果、木桩和火焰弹细节 | 猎人路线是完整玩法的另一半 |
| 6 | 全面扫汉化、字幕、tooltip、HUD 布局 | 最终玩家体验收口 |

猎人路线子agent审计新增高优先事项：

| 优先级 | 内容 | 当前判断 |
| --- | --- | --- |
| H1 | 猎人契约 NPC 追踪 | 当前猎人更像普通近战怪，缺契约位置、目标 UUID、远距寻路、武器切换和原版追踪 goal |
| H2 | 木桩/涂蒜戟克制吸血鬼 | 需要统一“吸血鬼弱点伤害来源”，让木桩和涂蒜戟绕过血幕并阻断回血 |
| H3 | 猎人护甲四件套效果 | 需要拆成 1 件吸血抗性、2 件大蒜光环、3 件木桩冷却减半、4 件吸血鬼暴击免疫 |
| H4 | 猎人护甲人形外观 | 当前 equipment 资源疑似只有狼甲层，需补 humanoid 层并截图确认 |
| H5 | 戟手感 | 需要补长柄武器交互距离，并让涂蒜戟接入同一套克制吸血鬼规则 |

## 16. 每次改完后的固定验收流程

每补完一个功能，都按这个顺序验收：

1. 构建通过。
2. `runClient` 能进主菜单。
3. 能进入单人世界。
4. 生存模式能自然触发功能。
5. 创造模式能快速复测边界情况。
6. 第一人称检查手、持物、HUD。
7. F5 检查自己。
8. 第二客户端或旁观者检查别人看到的样子。
9. 检查声音和字幕。
10. 检查粒子。
11. 退出重进后状态仍正确。
12. 中文环境无裸 key。
13. 日志无新崩溃、无客户端类加载到服务端的问题。

## 17. 给后续子 agent 的统一任务说明

如果继续调用子 agent，每个子 agent 都必须拿到完整上下文，不能只看单个文件。

必须提供：

- 当前目标：Minecraft 1.21.1 NeoForge 版尽量完美复刻原版 Nycto。
- 原版仓库：<https://github.com/MoriyaShiine/nycto>
- 当前工程：`D:\mcmodding\nycto`
- 玩家清单：`C:\Users\Lenovo\Desktop\吸血鬼功能清单.txt`
- 当前文档：`D:\mcmodding\nycto\docs\nycto-perfect-parity-readable-code-guide-2026-06-04.md`
- 当前有效代码范围：`common\NyctoNeoForge.java` 和 `neoforge\**`
- 定制规则：不恢复阳光惩罚，不恢复强制弱点副作用
- NeoForge 规则：注册、事件、网络包、客户端/服务端分离、mixin 最小化

子 agent 输出必须包含：

- 原版行为是什么。
- 当前实现在哪里。
- 当前实现差在哪里。
- 哪些差异是用户定制，不能修。
- 哪些差异是真缺失，应该修。
- 修复建议落到具体文件。
- 如果涉及视觉，必须分别说明模型、贴图、粒子、声音、HUD、第一人称、第三人称如何验收。


## 17.1 2026-06-04 Implemented Update

This section records fixes already implemented after the audit and verified with `build --offline`.

- Vampire player blood now uses 100 max blood and 50 starting blood after conversion. Blood bottles now restore 10 blood.
- `VampireFoodHudMixin` hides vanilla food icons for vampires. `NyctoHudLayer.renderBlood` now draws blood droplets in the vanilla food HUD area.
- Vampire hunter armor effects are split by piece count: 1 piece blocks blood drain, 2 pieces add garlic aura, 3 pieces halve wooden stake cooldown, 4 pieces reduce vampire critical damage.
- Wooden stake melee, wooden stake projectile, and garlic-coated halberd now share a vampire-weakness damage check and bypass Blood Barrier.
- Vampiric Thrall conversion now uses a dedicated `Mob` look target search instead of combat-target filtering, so owned tameable targets are not rejected before `canBeThralled`.

Still requires runClient validation: blood HUD layout on multiple resolutions, crossbow wooden stake behavior, Blood Barrier bypass behavior in game, hunter armor humanoid rendering, and thrall texture sync after relog/range changes.

## 18. 当前结论

当前版本的方向是正确的：它已经从“原版代码仓库”变成了一个能在 1.21.1 NeoForge 上工作的独立移植版。

接下来要做的不是盲目把旧代码搬进来，而是逐项把原版体验翻译成 NeoForge 1.21.1 下安全可运行的实现：

- 服务端保存真实状态。
- 网络包同步给客户端。
- 客户端只负责画面和声音。
- 注册走 `DeferredRegister`。
- mixin 只做事件无法完成的最小补丁。
- 视觉资源必须真实接入当前 NeoForge 渲染链。
- 所有玩家可见文本必须汉化。

最终判断标准很简单：玩家不需要知道代码怎么写，只要实际玩起来像原版 Nycto，并且保留本项目明确要求的两条定制规则。
