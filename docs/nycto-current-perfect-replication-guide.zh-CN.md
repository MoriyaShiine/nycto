# Nycto 1.21.1 NeoForge 当前版本完美复刻指南

更新日期：2026-06-04

适用仓库：`D:\mcmodding\nycto`

目标版本：Minecraft 1.21.1 + NeoForge 21.1.x

原版参考：`MoriyaShiine/nycto`

## 1. 这份文档解决什么问题

这份文档不是普通开发计划，而是当前移植版的“复刻说明书”。

它同时面向两类人：

| 读者 | 应该怎么读 |
|---|---|
| 非技术玩家或验收者 | 看“玩家应该看到什么”“当前差异”“验收方式”。只要游戏里的表现不符合这些描述，就可以认为还没复刻完。 |
| 开发者或后续子 agent | 看“当前代码位置”“应该怎么改”“NeoForge 规则”。这些信息能直接定位到文件和实现方式。 |

本项目的目标是：在 1.21.1 NeoForge 上尽量完整复刻原版 Nycto 的玩法、视觉、声音、粒子、HUD、实体 AI 和界面体验。

但有两个地方是用户已经明确决定的定制差异，不允许按原版改回去：

1. 吸血鬼白天不需要害怕阳光，也不应该因为白天阳光受到惩罚。
2. 能力和弱点组合后，不需要强制产生原版那种副作用。

所以本文中说“完美复刻”时，默认排除这两个定制差异。它们不是缺失功能。

## 2. 当前仓库真正参与构建的代码

当前项目不是把原版 Fabric/旧代码全部直接搬进 NeoForge。`build.gradle` 明确限制了当前会被编译的 Java 范围：

| 类型 | 当前有效范围 |
|---|---|
| 主入口 | `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java` |
| NeoForge 新实现 | `src/main/java/moriyashiine/nycto/neoforge/**` |
| 资源 | `src/main/resources` 和 `src/main/generated` |

这意味着：

- `src/main/java/moriyashiine/nycto/common/**` 里除了 `NyctoNeoForge.java` 以外的大量旧代码，主要是参考资料，不一定在游戏里运行。
- `src/main/java/moriyashiine/nycto/client/**`、`src/main/java/moriyashiine/nycto/mixin/**` 中的旧客户端和 mixin 代码，也主要是参考资料。
- 真正修当前版本时，优先修改 `neoforge` 包下的新代码。

非技术解释：仓库里有些文件像“旧版说明书”，不代表游戏正在用它们。当前游戏实际使用的是 NeoForge 新实现。

## 3. NeoForge 1.21.1 的硬规则

后续复刻必须遵守 NeoForge 1.21.1 的结构，否则可能出现能编译但进游戏崩溃、单人能玩但服务器坏掉、视觉只在自己这里正常等问题。

官方参考：

- NeoForge 文档首页：<https://docs.neoforged.net/>
- 1.21.1 网络包文档：<https://docs.neoforged.net/docs/1.21.1/networking/>
- 1.21.1 数据附件文档：<https://docs.neoforged.net/docs/1.21.1/datastorage/attachments/>
- 1.21.1 事件文档：<https://docs.neoforged.net/docs/1.21.1/concepts/events/>
- 1.21.1 注册系统文档：<https://docs.neoforged.net/docs/1.21.1/concepts/registries/>

### 3.1 注册必须走 NeoForge 注册流程

方块、物品、实体、声音、粒子、菜单、效果都不能随便在运行时创建。它们要在模组加载阶段注册。

当前代码位置：

| 内容 | 文件 |
|---|---|
| 主注册入口 | `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java` |
| 方块 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoBlocks.java` |
| 物品 | `src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java` |
| 实体 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoEntityTypes.java` |
| 猎人内容 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java` |
| 声音 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java` |
| 粒子 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoParticleTypes.java` |
| 效果 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMobEffects.java` |
| 药水 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoPotions.java` |
| 菜单 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMenuTypes.java` |

验收标准：

- 新增物品能在创造栏或配方里正常出现。
- 新增实体能召唤、保存、重新进世界后仍存在。
- 新增声音、粒子不会报缺失资源。

### 3.2 服务端管玩法，客户端管视觉

吸血、扣血、耗血、冷却、死亡、AI、掉落这些真实玩法必须由服务端决定。

模型、HUD、粒子、后处理、第一人称手臂这些表现可以放客户端。

当前代码分布：

| 类型 | 文件例子 |
|---|---|
| 服务端玩法 | `NyctoGameplay.java`、`VampireLifecycleEvents.java`、`VampirePowerEvents.java`、`NyctoPowers.java` |
| 客户端视觉 | `NyctoClientNeoForge.java`、`NyctoHudLayer.java`、`DarkFormClientRenderEvents.java`、`KeenSensesClientRenderEvents.java` |
| 网络同步 | `src/main/java/moriyashiine/nycto/neoforge/network/*.java` |

验收标准：

- 多人游戏中，能力效果不能只在本地看起来生效。
- 只有该看到视觉效果的人才能看到，例如 Keen Senses 的轮廓和后处理应该只给开启者看。
- 服务端重启、玩家死亡、换维度后，关键状态不能乱掉。

### 3.3 网络包必须使用 1.21.1 的 CustomPacketPayload 方式

当前网络入口：

`src/main/java/moriyashiine/nycto/neoforge/network/NyctoPayloads.java`

已有同步包包括：

| 网络包 | 用途 |
|---|---|
| `SyncPlayerDataPayload` | 同步玩家吸血鬼数据、血液、能力、冷却等 |
| `SetActivePowerPayload` | 切换当前能力 |
| `UseActivePowerPayload` | 使用当前能力 |
| `SwapAltarPowersPayload` | 祭坛选择能力/弱点 |
| `SyncBatFormPayload` | 同步 Bat Form 视觉状态 |
| `SyncDarkFormPayload` | 同步 Dark Form 视觉状态 |
| `SyncMistFormPayload` | 同步 Mist Form 视觉状态 |
| `SyncBloodrushPayload` | 同步 Bloodrush 视觉状态 |
| `SyncCarnagePayload` | 同步 Carnage 视觉状态 |
| `SyncKeenSensesPayload` | 同步 Keen Senses 本地视觉状态 |
| `SyncThrallPayload` | 同步仆从客户端视觉状态 |
| `AddBloodBarrierParticlesPayload` | 触发血液屏障粒子 |

后续规则：

- 能力是否激活由服务端决定，再发包给客户端显示。
- 客户端按键只能“请求使用能力”，不能直接把自己改成最终状态。
- 新视觉如果其他玩家也应该看到，必须同步给追踪该实体的玩家。

### 3.4 客户端渲染注册只能放在客户端事件里

当前客户端注册入口：

`src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`

这个文件负责：

- HUD 图层。
- 祭坛屏幕。
- 能力热栏按键。
- 实体渲染器。
- 模型层。
- 玩家和实体额外渲染层。
- 粒子工厂。
- 物品模型谓词。

验收标准：

- 专用模型必须在 `RegisterLayerDefinitions` 里注册。
- 实体渲染器必须在 `RegisterRenderers` 里注册。
- 玩家身上的 aura、屏障、仆从视觉层必须在 `AddLayers` 里挂上。
- 第一人称特殊手臂、后处理、HUD 不能污染服务端启动。

### 3.5 Mixin 只能做事件做不到的事情

当前 NeoForge 专用 mixin 配置：

`src/main/resources/nycto-neoforge.mixins.json`

当前 mixin 主要用于：

- Mist Form 穿过方块或隐藏实体。
- Keen Senses 的颜色、后处理和世界渲染辅助。
- 仆从实体贴图/渲染状态辅助。
- 吸血鬼 HUD 中饥饿条替换。

规则：

- 能用 NeoForge 事件解决的，不要新增 mixin。
- mixin 必须只碰当前功能需要的最小方法。
- 不要把旧 Fabric mixin 整包照搬。

## 4. 当前版本总体判断

当前 1.21.1 NeoForge 版本已经有一套可运行的骨架，并且很多重要内容已经实现：

- 吸血鬼状态与血液数据。
- 吸血鬼祭坛和能力选择界面。
- 多个吸血鬼能力。
- 血液 HUD 和能力热栏。
- 吸血鬼实体、Dark Form 实体、猎人实体。
- 木桩、火焰弹、血液飞刃等投射物。
- 猎人武器、契约、部分 AI。
- 血液屏障、Bloodrush、Carnage、Dark Form、Keen Senses 等视觉层。
- 部分声音、粒子和汉化。

但它还不是“完美复刻”。剩余差异主要集中在：

1. 某些原版模型和视觉资源已经接到 NeoForge 当前渲染里；当前 `runClient` 已能加载到主菜单，但完整视觉验收还没有全部完成。
2. 部分能力的机制已可用，但粒子、声音、第一人称、数值和多人同步还需要逐项验收。
3. 吸血鬼仆从和猎人路线已经成型，但 AI、外观、猎人类型、猎人热度和契约行为仍要继续比对。
4. 一些原版内容还停留在资源或旧代码参考层，并没有完全进入当前构建，尤其是乌头线、旗帜图案、锻造模板、蒜香食物和神馔瓶。
5. `runClient` 已修复模组构造、吸血鬼模型层和猎人印记注册表崩溃；本轮 Computer Use 本地运行环境报 `failed to write kernel assets: 系统找不到指定的路径`，因此实机截图和进世界逐项点击验收仍未完成。

当前已经推进过、但仍需要实机确认的重点包括：

| 内容 | 当前状态 | 还需要确认什么 |
|---|---|---|
| 初始吸血鬼模型 | 已接入专用 `VampireModel`，不再以普通玩家模型作为最终形态 | 眼睛发光层、攻击时爪臂切换、Carnage aura 是否贴合 |
| 野生吸血鬼能力池 | 已按原版方向接入 Batstep、Bat Swarm、Blood Barrier、Blood Flechettes、Carnage、Haemogenesis 的随机能力池 | 六种能力是否都会在战斗中自然触发，冷却是否合理 |
| 吸血蓄力 | 已补服务端蓄力门槛，并让 HUD 条件和服务端一致；吸血/匕首进度条已改用原版式 `vampire_charge_jump` 贴图 | 第一人称蓄力条、右键时机、多人同步 |
| Nycto 夜视图标 | 已用 `nycto:night_vision` 显示专属图标，同时用隐藏的原版夜视负责照明 | 状态栏图标是否正确、是否重复或闪烁 |
| 吸血鬼化图标 | 已有 `nycto:vampirism` 状态效果和 `textures/mob_effect/vampirism.png` 专属图标 | 饮用吸血鬼血瓶后倒计时图标是否显示为红色蝙蝠 |
| 血滴饥饿栏 | 已隐藏原版鸡腿栏，并改用 `hud/blood/hunger/blood_*` 专用血滴贴图；已上移技能栏，避免覆盖血滴 | 实机确认 10 个血滴位置是否与原版饥饿栏对齐 |
| 物品模型 predicate | 已通过 `FMLClientSetupEvent` 注册木桩弩、满血匕首、长戟手持模型 predicate | `runClient` 确认模型是否按状态切换 |
| 组合形态血液屏障和形态状态同步 | Bat Form 和 Dark Form 的替身渲染已补 Blood Barrier 手动绘制；玩家/野生吸血鬼血环层数已补 tracking 同步；玩家重登、换维度、重生和被他人追踪时会补发 Bat/Dark/Mist/Bloodrush/Carnage 视觉状态 | 实机确认血环高度、旋转、替身模型和多人旁观视角不会错位；重登或换维度后自己和旁观者都仍能看到正确形态 |
| 瞄准目标血液预览 | 潜行瞄准可吸血目标时，准星旁会显示 5 滴目标血量预览 | 实机确认优质血/较差血贴图、位置和蓄力条不会互相遮挡 |
| 玩家人形护甲模型 | 已新增 NeoForge 客户端专用护甲模型层，并通过 `RegisterClientExtensionsEvent` 挂到吸血鬼护甲和猎人护甲；模型几何来自原模组的 Vampire/Hunter armor mesh | 必须 `runClient` 穿戴四件套确认帽檐、衣摆、披风/装饰、腿部和靴子贴图没有错位；吸血鬼披风当前按静态披风显示，未恢复原版组件控制开关 |
| Bat Swarm/Batstep 蝙蝠粒子 | 已从通用精灵粒子改为 `NyctoBatParticle` 专用粒子：两帧扇翼、随机扑动、Batstep 短寿命小尺寸、Bat Swarm 较大尺寸；Bat Swarm 储血后由服务端额外稳定发送红色血液粒子 | 仍需 `runClient` 确认红眼蝙蝠轨迹、血液喷溅密度、烟雾遮挡比例和多人旁观视角 |
| Haemogenesis | 玩家和野生吸血鬼使用时已清除回血阻断并灭火 | 实机确认回血量、粒子、音效和冷却是否贴近原版 |
| 玩家提示文本 | 能力使用、血液不足、仆从喂食/模式、血泉提示已改为中英文翻译 key | `runClient` 中文环境确认实际显示不露英文或未翻译 key |

## 5. 原版功能复刻总表

这张表用于判断“当前还缺什么”。它不是说所有内容都没做，而是把每个功能的完美标准写清楚。

| 模块 | 原版玩家体验 | 当前主要代码位置 | 当前差异/风险 | 完美复刻标准 |
|---|---|---|---|---|
| 成为吸血鬼 | 击杀吸血鬼得到吸血鬼血瓶，饮用后转化 | `Vampire.java`、`NyctoItems.java`、`NyctoGameplay.java`、`VampireLifecycleEvents.java` | 需要继续确认掉落、转化时间、声音和粒子是否完整 | 玩家可以按原版流程自然成为吸血鬼，过程有正确提示、音效和状态变化 |
| 吸血 | 潜行瞄准目标出现红色蓄力条，蓄满后右键吸血；催眠/睡眠/迷雾形态影响安全性 | `VampireLifecycleEvents.java`、`NyctoHudLayer.java`、`NyctoBloodUtil.java` | 已补服务端蓄力门槛、HUD 副手显示一致性、原版式贴图进度条、准星旁目标血滴预览，以及猎人护甲阻挡吸血时的失败音效；仍需实机确认右键时机和多人同步 | 吸血目标、蓄力条、血量变化、目标伤害、声音粒子都符合原版，但不恢复阳光惩罚 |
| 血液系统 | 吸血鬼有血液储量，血瓶和吸血会补充 | `NyctoData.java`、`SyncPlayerDataPayload.java`、`NyctoHudLayer.java` | 数据目前是自定义实现，长期最好迁移到 Data Attachments | 单人和多人都能稳定保存、同步、死亡后按规则继承 |
| 血液保命 | 致死时血液可抵消死亡 | `VampireBloodEvents.java` | 已避免弱点伤害错误触发，需要继续测木桩/大蒜长戟/回血阻断 | 原版能保命的伤害会保命，原版不能保命的克制伤害不会保命 |
| 祭坛 | 选择能力和弱点，消耗材料、经验和血瓶 | `VampireAltarBlock.java`、`VampireAltarMenu.java`、`VampireAltarScreen.java` | UI 已有；弱点区域已显示旧版弱点图标但不会强制应用副作用，祭坛提示已汉化；菜单有效性已恢复到原版方向：玩家必须仍是吸血鬼、仍在祭坛旁、祭坛方块仍存在；主材料槽已有真实材料占位，副材料槽已补血瓶/吸血鬼血瓶轮播占位；仍需实机对照布局、材料位置、轮播观感和点击反馈 | 祭坛外观、菜单分栏、能力图标、勾选、材料消耗都与原版一致，同时保留当前版本“不强制弱点副作用”的定制规则 |
| 棺材 | 白天睡棺材跳到夜晚，多木材版本 | `CoffinBlock.java`、资源方块模型 | 多木材开/合模型、白天跳夜、汉化提示和 `sleep_in_coffin` 进度触发已补；现在使用时会短暂驱动两半方块的 `occupied=true`，让闭合模型实际显示约 40 tick 后恢复；仍需实机验收碰撞、全部木材变体、闭合持续时间和多人跳夜 | 白天可睡、夜晚跳转、碰撞、开合状态和外观与原版一致 |
| 吸血鬼护甲 | 成套提供吸血鬼专属加成 | `VampireArmorItem.java`、`NyctoArmorMaterials.java`、`NyctoItems.java`、`NyctoHumanoidArmorModel.java`、`NyctoArmorClientExtensions.java` | 已补 NeoForge 客户端护甲模型层和物品客户端扩展，不再只是普通铁甲轮廓；仍需实机确认头盔、胸甲披风/衣摆、护腿、靴子和贴图 UV 与原版一致 | 护甲外观、属性、回血/血量/能力消耗相关加成都符合原版 |
| 吸血鬼匕首 | 攻击类人生物装血，满后用玻璃瓶提取血瓶 | `VampiricDaggerItem.java`、`BloodExtractionRecipe.java`、`NyctoRecipeSerializers.java` | 已补吸血粒子、饮血声、满血模型切换、右键玻璃瓶提取和工作台提取特殊配方；仍需实机确认不同血型瓶和返还匕首 | 匕首状态、模型切换、提取配方、声音粒子完整 |
| 野生大蒜 | 野外生成，可采集种植和制作猎人工具 | `NyctoHunterContent.java`、方块/资源 | 已补野生大蒜和大蒜作物接触吸血鬼时造成伤害、压制和绿色反馈粒子；仍需核对世界生成与成长逻辑 | 森林/沼泽等区域能自然找到，采集和种植体验符合原版 |
| 大蒜药水 | 治疗药水加大蒜酿造，对吸血鬼有克制，可进一步做成大蒜药箭 | `NyctoHunterContent.java`、`NyctoPotions.java`、`NyctoMobEffects.java` | 已接入自定义大蒜药水物品的吸血鬼压制、虚弱 II、回血阻断和绿色反馈粒子；已把 NeoForge 正规大蒜药水改回原版式“瞬间治疗 + Vampire Ward”，普通/延长/强效三种药水和“强治疗 + 大蒜 = 强大蒜药水”酿造链已对齐；Vampire Ward 现在通过 NeoForge 药效添加事件只对吸血鬼追加压制，普通生物不再因为药水自带虚弱而误伤；酿造配方事件已从 MOD 总线挪到 NeoForge 游戏总线，修复 `runClient` 模组构造崩溃；仍按用户定制排除原版“喝大蒜药水删除能力/治愈成人类”惩罚；仍需实机验收颜色、名称、酿造链、投掷/滞留范围和药箭命中效果 | 配方、图标、药效、对吸血鬼的克制表现都正确 |
| 大蒜花环 | 放置后形成驱离/削弱吸血鬼的区域 | `NyctoHunterContent.java` | aura 范围已与 Keen Senses 过滤统一为 12 格，并会触发压制与绿色反馈粒子；仍需实机确认范围手感 | 吸血鬼接近会有明显负面表现，普通生物不被误伤 |
| 木桩 | 可近战、可由弩发射、可落地成方块 | `WoodenStakeItem.java`、`WoodenStakeProjectile.java`、`WoodenStakeBlock.java`、`WoodenStakeRenderer.java`、`NyctoDamageTypes.java` | 已有主体；木桩方块已补原版式含水状态，落到木桩上的伤害改用 `nycto:wooden_stake` 专用伤害类型，并放开对应 damage_type/tag 资源打包；仍需实机测发射、命中、落地、死亡提示和克制规则 | 近战和弩发射都能稳定克制吸血鬼，视觉和物品模型正确 |
| 火焰弹 | 投掷后产生火焰、烟雾和点燃范围实体 | `FirebombItem.java`、`FirebombProjectile.java`、`FirebombBlock.java`、`NyctoHunterContent.java` | 已回退到原版式行为：命中后铺不会蔓延烧森林的临时 Firebomb 火焰，并点燃范围内不在水/雨中的实体 8 秒；已移除移植版额外直接爆发伤害和吸血鬼大蒜压制；仍需实机确认范围、投掷者排除和火焰残留时间 | 投掷轨迹、爆开、火焰残留和点燃表现都像原版 |
| 猎人装备 | 猎人套装、长戟、木桩弩路线，以及大蒜药箭/凋零之箭 | `NyctoHunterContent.java`、`NyctoHunterUtil.java`、`WoodenStakeItem.java`、`NyctoPotions.java`、`HunterModel.java`、`HunterRenderer.java`、`NyctoHumanoidArmorModel.java`、`data/nycto/banner_pattern/hunters_mark.json` | 猎人模型、持物层、出现/死亡白烟、受击蓝绿色碎屑已推进；玩家人形猎人护甲已补原版式帽檐、面具、胸饰、衣摆几何；NPC 猎人已补随机装备池：木桩+弩、涂蒜长戟、长戟+木桩、铁剑+盾牌、铁剑+弩，并修正远近距离只在副手确实是远程武器时切换；契约猎人已补 1/8 概率骑马分支，骑马时切换为涂蒜长戟+黑底黄色猎人印记盾牌；猎人印记 banner pattern 已补 1.21.1 必需的 `translation_key`，修复点击创建/进入世界时的注册表崩溃；拿盾猎人已补间歇性主动举盾 AI；骑乘姿态、盾牌图案贴图实际显示、举盾节奏、完整战斗手感和护甲 UV 仍需实机确认 | 玩家和猎人穿戴/持有时都显示正确，不出现普通玩家模型替代；猎人套装逐件加成和特殊箭矢能稳定克制吸血鬼 |
| 猎人契约 | 牧师购买契约，召唤猎人追踪目标 | `HunterEntity.java`、`PathToContractPosGoal.java`、`UltimateTargetGoal.java`、`NyctoHunterContent.java` | 目标持久化已补，仍需实机验证远距离追踪和重进世界 | 猎人会追踪吸血鬼或契约位置，保存重载后继续工作 |
| 吸血鬼仆从 | 永久奴役类人生物或驯服动物，能跟随/停留/游荡/防御 | `VampiricThrallEvents.java`、`VampiricThrallClientEvents.java`、`SyncThrallPayload.java`、`ThrallClientState.java`、`NyctoPowers.java` | 已补已加载仆从每秒独立维护，不再只靠主人附近扫描；已补恼鬼进入可转化标签并使用现有恼鬼仆从贴图；客户端仆从状态会在断线时清空；喂血瓶会补仆从血液储备，仆从受伤时会消耗储备自愈，附近未锁定且装血的血泉可被仆从取用；仆从现在会记住最近可用血泉坐标，并与附近同主仆从共享该记忆；唤魔者仆从召唤出的恼鬼会继承仆从归属、跟随唤魔者目标并在约 30 秒后消散；村民仆从已补基础脑记忆压制，会清理繁殖、家、职业点、集会点、躲藏和铁傀儡检测记忆，并压回 idle；动物仆从已补普通繁殖食物交互拦截、求爱状态清理和幼崽生成取消；女巫仆从投出或自饮的治疗/再生药水已反转为伤害药水；猪灵仆从会保持免疫僵尸化，并清理指向主人或同主仆从的攻击/愤怒记忆；仍需实机验证血泉记忆共享、女巫药水表现、猪灵群体仇恨传播边界和逐实体外观；如果继续追求原版内部细节，还需扩展为多血泉/瓶数/过期状态列表 | 仆从外观、名字/状态、跟随命令、战斗目标和保存都完整 |

## 6. 14 个吸血鬼能力复刻表

| 能力 | 原版应该是什么样 | 当前主要代码 | 当前差异/风险 | 完美复刻标准 |
|---|---|---|---|---|
| Night Vision | 吸血鬼能在夜间看清，并显示 Nycto 专属蓝色夜视状态图标 | `NyctoPowers.java`、`NyctoGameplay.java`、`NyctoMobEffects.java` | 已用隐藏的原版夜视负责照明，同时用 `nycto:night_vision` 显示专属图标；需实机确认图标位置和持续刷新 | 吸血鬼在黑暗中稳定有视觉优势，状态栏显示 Nycto 夜视图标而不是普通原版夜视图标 |
| Bat Form | 玩家变成蝙蝠，能飞，体型和第一人称变化 | `NyctoPowers.java`、`BatFormClientState.java`、`BatFormClientRenderEvents.java` | 第一人称已补蝙蝠翅膀方向，第三人称蝙蝠替身已加深紫色调以贴近视频稿里的紫黑蝙蝠；Bat Form 下也会手动绘制 Blood Barrier 和 Carnage aura；仍需实机校准高度、休息姿态、旋转和是否需要去掉色调 | 自己和别人都看到蝙蝠形态，第一人称不露普通手臂 |
| Bat Swarm | 召唤蝙蝠群攻击并吸血 | `BatSwarmEvents.java`、`NyctoParticleTypes.java`、`NyctoBatParticle.java` | 已补专用蝙蝠粒子、随机扑动、两帧扇翼和吸到血后的红色血液反馈；本轮已把玩家耗血/冷却拉回原版方向的 10 血/30 秒，野生吸血鬼蝙蝠群也改为约半程持续；远距离可见性和血粒子密度仍需实机核对 | 有明显蝙蝠群轨迹、攻击反馈、吸血反馈 |
| Batstep | 朝视线瞬移，路径造成伤害和眩晕 | `NyctoPowers.java`、`VampirePowerEvents.java`、`NyctoBatParticle.java` | Batstep 路径粒子已切到短寿命小尺寸蝙蝠专用粒子；本轮已把冷却改回原版方向的 8 秒；仍需核对路径密度、眩晕时长和声音 | 位移、路径伤害、粒子、声音都像原版 |
| Blood Barrier | 三圈血环保护玩家，被击中会破裂 | `BloodBarrierLayer.java`、`BloodBarrierModel.java`、`AddBloodBarrierParticlesPayload.java`、`SyncBloodBarrierPayload.java` | 模型层已接入，并已补 Bat Form/Dark Form 替身渲染下的组合显示；本轮已补玩家和野生吸血鬼血环层数 tracking 同步；仍需核对破裂粒子和耐久 | 玩家身上有清楚血环，受到重击后按原版破碎；多人旁观者也能稳定看到血环 |
| Blood Flechettes | 发射血刃，命中后阻断回血；后续攻击被标记目标时抽血回血 | `BloodFlechetteProjectile.java`、`BloodFlechetteRenderer.java`、`VampirePowerEvents.java` | 已从“命中立即回血”改为“命中标记，之后吸血鬼攻击才生命抽取”，更接近原版节奏；仍需实机核对发射音、命中音、命中特效和标记持续手感 | 投射物贴图、轨迹、命中反馈、封疗、后续生命抽取都完整 |
| Bloodrush | 向前血冲刺，带 aura，能灭火 | `BloodrushClientState.java`、`BloodrushAuraLayer.java`、`BloodrushClientSound.java` | aura 和声音已接入，需多人同步验收 | 开启者和旁观者都看到正确血色冲刺效果 |
| Carnage | 狂暴提高伤害，让目标流血，有 overlay/aura | `CarnageClientState.java`、`CarnageAuraLayer.java`、`VampireCarnageAuraLayer.java`、`VampirePowerEvents.java` | 已有玩家/吸血鬼/Dark Form aura；近战命中会封疗、抽取目标血液储备，并让目标短时间持续流血；仍需实机校准透明度、流血频率和粒子密度 | 近战、屏幕覆盖、目标流血、aura 都与原版一致 |
| Dark Form | 变成黑暗野兽，动作、爪子、跳跃、飞行和禁用装备规则完整 | `DarkForm.java`、`DarkFormModel.java`、`DarkFormAnimation.java`、`DarkFormRenderer.java`、`DarkFormClientRenderEvents.java`、`BeastFormEvents.java` | 已推进模型和动画，Dark Form 下也会手动绘制 Blood Barrier；已补原版方向的 0.68 受击减伤、20 tick 二段跳/飞行冷却、兽形态可挖方块徒手加速，以及护甲/盾牌/工具使用限制和 Dark Form 中自动退回不允许装备的物品；仍需修正第三人称替身持物显示、第一人称爪子校准、白翼/红破损下摆比例和动画阶段实机观感 | 第三人称和别人视角都是黑暗野兽，不是普通玩家套皮 |
| Haemogenesis | 快速回血、清除回血阻断、灭火 | `NyctoPowers.java`、`Vampire.java`、`NyctoData.java` | 已补玩家和野生吸血鬼使用时清除回血阻断并灭火；本轮已把玩家能力从一次性大回血改为更接近原版的持续治疗：10 血消耗、20 秒冷却、每 2 tick 治疗 1 点，总治疗量约为最大生命的 1/3；仍需核对粒子、声音、回血量 | 使用后有清楚血液再生反馈，数值符合原版 |
| Hypnotize | 范围催眠，生物停止，玩家短暂眩晕 | `NyctoPowers.java`、`NyctoMobEffects.java`、`VampirePowerEvents.java` | 已补 `HYPNOTIZED` 和 `STUNNED`，需验收不同实体 | 被催眠目标有粒子、停止行动，玩家控制受限时间正确 |
| Keen Senses | 开关型感知，透墙轮廓、心形、后处理、心跳、降噪、耗血 | `KeenSensesClientRenderEvents.java`、`KeenSensesGameRendererMixin.java`、`KeenSensesEntityColorMixin.java`、`SyncKeenSensesPayload.java` | 已接近原版，重点测多人只给开启者显示 | 只有开启者看到后处理、轮廓、血量提示和听觉变化 |
| Mist Form | 雾化、隐身、免摔、穿过非实体方块，受伤/攻击退出 | `MistFormBlockStateMixin.java`、`MistFormLivingEntityRendererMixin.java`、`SyncMistFormPayload.java`、`VampirePowerEvents.java` | 已增强持续白烟/灰烟/云团，开启和退出时也有更明显白雾爆发；形态临时隐身/缓降/抗性刷新时长已从 40 tick 缩短到 10 tick，降低退出后残留。仍需实机确认雾团密度、多人可见性和是否遮挡过强 | 玩家肉眼看到自己像雾，不是简单消失或冒少量烟 |
| Vampiric Thrall | 永久制造仆从，并能管理行为 | `VampiricThrallEvents.java`、`VampiricThrallClientEvents.java`、`ThrallClientState.java`、`NyctoPowers.java` | 已有跟随/停留/游荡/防御、喂血瓶、友军伤害拦截、追踪同步和贴图替换；已补已加载仆从独立 tick、恼鬼可转化、客户端断线清理、仆从血液储备、自愈消耗、附近血泉取血、单血泉坐标记忆和同主仆从共享、唤魔者仆从召唤恼鬼继承归属/目标/临时生命周期、村民仆从基础生活 AI 压制、动物仆从繁殖限制、女巫仆从治疗/再生投掷与自饮药水反转，以及猪灵仆从攻击/愤怒记忆清理；仍需逐实体核对外观、AI、血泉共享实际寻路、女巫药水实际效果和猪灵群体仇恨传播边界；如果继续追求原版内部细节，还需把单血泉记忆扩展为原版式多血泉/瓶数/过期状态列表 | 每类原版支持实体都有对应仆从外观、行为和保存 |

## 7. 弱点系统的特殊说明

原版 Nycto 有弱点系统，例如 Humanity、Hydrophobia、Pyrophobia、Rich Tastes、Thin Blood、Vile Presence 等。

但当前用户明确要求：不要恢复“能力与弱点组合后产生副作用”的原版强制惩罚。

所以当前复刻策略是：

| 内容 | 应不应该复刻 |
|---|---|
| 祭坛里能看到弱点格子和选择结构 | 应该复刻 |
| 弱点图标、汉化、UI 说明 | 应该复刻 |
| 弱点作为平衡选择的基础效果 | 可以按当前设计保留或逐步复刻 |
| 组合后强制副作用 | 不应该复刻 |
| 白天阳光惩罚 | 不应该复刻 |

代码注意点：

- 不要把原版 `SunExposureComponent` 一类逻辑完整恢复到当前吸血鬼玩家身上。
- 如果后续为了 HUD 或视觉需要太阳图标，也不能让它重新造成伤害或惩罚。
- 文案里要把“白天无惩罚”写成当前版本特性，避免玩家误以为是 bug。

## 8. 视觉复刻专项

视觉是当前版本离“完美复刻”最容易被玩家看出来的地方。每个视觉功能都要按“模型、贴图、动画、粒子、声音、第一人称、第三人称、多人视角”拆开验收。

### 8.1 初始吸血鬼实体

原版应该看到：

- 高大人形吸血鬼。
- 灰皮、正式服装、明显吸血鬼面部特征。
- 黄眼或发光眼睛。
- 攻击时有动作和吸血反馈。
- 有吸血鬼专属叫声、受伤声、死亡声。

当前代码：

| 内容 | 文件 |
|---|---|
| 实体逻辑 | `src/main/java/moriyashiine/nycto/neoforge/entity/Vampire.java` |
| 渲染器 | `src/main/java/moriyashiine/nycto/neoforge/client/renderer/VampireRenderer.java` |
| 眼睛层 | `VampireRenderer` 内部或相关 layer |
| 声音注册 | `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java` |

当前风险：

- 如果渲染器仍使用普通玩家模型层，外形会不像原版吸血鬼。
- 原版 `VampireModel` 中的耳朵、鼻子、长袍、爪子、交叉手臂需要迁到 NeoForge 当前模型系统。

最新审计结论：

| 项目 | 当前判断 |
|---|---|
| 掉落 | 玩家击杀吸血鬼掉落 `nycto:vampire_blood_bottle` 的掉落表已对齐原版，但仍建议 `runClient` 击杀多次确认资源包优先级没有问题。 |
| 饮用转化 | 吸血鬼血瓶饮用后进入 `vampirism` 倒计时，再转化为吸血鬼的主入口基本可用。 |
| 眼睛发光 | `vampire_eyes.png` 已通过 `RenderType.eyes` 接入。 |
| 声音 | idle、hurt、death 已接入吸血鬼专属声音注册。 |
| 攻击吸血粒子 | 红色血液粒子已保留；成功攻击时已补轻量紫粉色催眠类粒子，用来贴近视频稿中的攻击粒子。 |
| 最大缺口 | 原先吸血鬼仍使用普通玩家基础模型；现在已迁入 NeoForge 版 `VampireModel`，但仍需要 `runClient` 截图确认模型比例、抱臂姿态、攻击爪臂和眼睛层没有错位。 |

建议代码落点：

| 要补的内容 | 建议文件 |
|---|---|
| NeoForge 版吸血鬼模型 | `src/main/java/moriyashiine/nycto/neoforge/client/model/VampireModel.java` |
| 模型层注册 | `src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java` |
| 使用专用模型的渲染器 | `src/main/java/moriyashiine/nycto/neoforge/client/renderer/VampireRenderer.java` |
| 攻击姿态同步 | `src/main/java/moriyashiine/nycto/neoforge/entity/Vampire.java` |
| 攻击粒子 | `Vampire.doHurtTarget` 或专用网络粒子 payload |

当前实现状态：

- `VampireModel` 已迁入当前 NeoForge 客户端模型包；根节点已补 `hat` 部件，修复 `Failed to create model for nycto:vampire / Can't find part hat` 的资源加载问题。
- `VampireRenderer` 已改为使用 `VampireModel.LAYER`，不再使用 `ModelLayers.PLAYER`。
- `NyctoClientNeoForge` 已注册吸血鬼模型层。
- `Vampire` 已加入短时攻击姿态同步字段，攻击时切换为爪臂，平时保持抱臂。
- 成功命中时会生成紫粉色攻击粒子；吸血成功时继续生成红色血液粒子。
- 野生吸血鬼已恢复原版式战斗能力池：生成时从 Batstep、Bat Swarm、Blood Barrier、Blood Flechettes、Carnage、Haemogenesis 中随机抽 3 个，并保存到 NBT。
- 野生吸血鬼已有 40 tick 公共能力间隔和各能力独立冷却。
- 已补 NPC Batstep、Bat Swarm、Blood Flechettes、Haemogenesis goal；Blood Barrier 和 Carnage 也改为受随机能力池控制，不再每只固定都会用。
- Bat Swarm 已从玩家专用 owner 扩展为吸血鬼玩家或野生吸血鬼都能拥有。
- Blood Flechette 已从“投射物命中立即回血”改为“命中后给目标短时间血镖标记；吸血鬼玩家或野生吸血鬼后续攻击该目标时才触发生命抽取”，更接近原版节奏。
- Carnage 已补目标真实失血逻辑：近战命中会封疗、抽取目标血液储备，并让目标短时间持续流血和产生血液粒子。
- 野生吸血鬼目标选择已补“非玩家、非村民、非吸血鬼的优质血液活体”分支，更接近原版。
- `gradlew build --offline` 已通过。
- 仍需 `runClient` 视觉和行为验收：确认模型不是普通玩家轮廓、眼睛发光层位置正确、攻击时爪臂显示正确、Carnage aura 仍能贴合新模型；同时确认随机能力池中 6 种能力都能在实战中触发。

完美标准：

- 召唤吸血鬼后，第一眼就不是普通僵尸或普通玩家模型。
- 眼睛发光层和 `vampire_eyes.png` 正常显示。
- 攻击时手臂/爪子动作明显。
- 击中目标时有血液粒子和饮血声。

### 8.2 Hunter 猎人

原版应该看到：

- 猎人不是普通玩家 Steve 模型。
- 有宽檐帽、外套、围巾、腰带等明显轮廓。
- 能手持木桩、弩、长戟等猎人武器。
- 契约召唤后会追踪吸血鬼或指定位置。

当前代码：

| 内容 | 文件 |
|---|---|
| 猎人实体 | `src/main/java/moriyashiine/nycto/neoforge/entity/hunter/HunterEntity.java` |
| 猎人模型 | `src/main/java/moriyashiine/nycto/neoforge/client/model/HunterModel.java` |
| 猎人渲染器 | `src/main/java/moriyashiine/nycto/neoforge/client/renderer/HunterRenderer.java` |
| 契约目标 AI | `PathToContractPosGoal.java`、`UltimateTargetGoal.java` |
| 猎人内容注册 | `NyctoHunterContent.java` |

当前进展：

- 已从普通玩家模型推进到专用 `HunterModel`。
- 已加入手持物渲染层。
- 已补契约目标和位置保存。
- 已补近远武器切换方向。
- 已补猎人出现和死亡时的白色烟雾。
- 已补猎人受击时的蓝绿色碎屑粒子。

仍需验收：

- 召唤猎人后外观是否完整显示帽子、外套、围巾。
- 弩、木桩、长戟是否显示在正确手上。
- 出现、死亡白烟是否足够明显，是否和视频里的猎人烟雾接近。
- 受击时蓝绿色碎屑是否可见，是否不会被血液粒子完全盖住。
- 退出世界再进入后，契约目标是否仍保留。
- 和吸血鬼玩家距离很远时，是否继续追踪。

### 8.3 Dark Form

原版应该看到：

- 玩家不是变黑的普通人，而是变成独立黑暗野兽。
- 有专用模型、爪子、骨骼动画。
- 跑、跳、飞、攻击都有不同动作。
- 第一人称不应该露出普通玩家手臂。
- Carnage、Blood Barrier 等视觉层在 Dark Form 下也应合理显示。

当前代码：

| 内容 | 文件 |
|---|---|
| Dark Form 实体 | `src/main/java/moriyashiine/nycto/neoforge/entity/DarkForm.java` |
| 模型 | `src/main/java/moriyashiine/nycto/neoforge/client/model/DarkFormModel.java` |
| 动画 | `src/main/java/moriyashiine/nycto/neoforge/client/animation/DarkFormAnimation.java` |
| 渲染器 | `src/main/java/moriyashiine/nycto/neoforge/client/renderer/DarkFormRenderer.java` |
| 玩家视觉替身和第一人称 | `src/main/java/moriyashiine/nycto/neoforge/client/event/DarkFormClientRenderEvents.java` |

当前进展：

- 专用模型和关键帧动画已经迁入。
- 第三人称替身渲染已经推进。
- Dark Form 实体本身已经接入自身 `AnimationState`，不再只走简化手臂/翅膀 fallback。
- Dark Form 下的 Carnage aura 已补。
- 第一人称爪臂已有首版。
- 已补原版方向的 Dark Form 受击减伤：非绕甲、非吸血鬼克制伤害会按 0.68 倍结算。
- 已把 Dark Form 二段跳/飞行冷却从 10 tick 对齐到 20 tick。
- 已新增 `BeastFormEvents`：Dark Form 下对 `nycto:beast_mineable` 方块徒手挖掘加速，阻止护甲、盾牌、工具和 `nycto:beast_unequippable` 物品使用，并会把变身中不应装备的护甲/盾牌退回背包。

剩余风险：

- 第三人称替身持物显示仍需补齐或实机确认。
- 跳跃/飞行动画阶段虽然冷却已对齐，但模型切换仍需要实机确认。
- 第一人称爪臂位置需要实机截图校准。
- 第一人称爪臂位置需要实机截图校准。

完美标准：

- 自己、其他玩家、旁观者看到的 Dark Form 都一致。
- 跑跳飞攻击动作不僵硬。
- 物品栏和手持工具不会破坏黑暗野兽表现。

### 8.4 Keen Senses

原版应该看到：

- 使用后画面进入特殊感知状态。
- 实体轮廓高亮。
- 能看到目标血量/生命提示。
- 背景声音变弱，并有心跳节奏。
- 感知范围逐渐扩大。
- 持续消耗血液。

当前代码：

| 内容 | 文件 |
|---|---|
| 服务端能力 | `NyctoPowers.java`、`VampirePowerEvents.java` |
| 客户端状态 | `KeenSensesClientState.java` |
| 视觉事件 | `KeenSensesClientRenderEvents.java` |
| 后处理 mixin | `KeenSensesGameRendererMixin.java` |
| 实体颜色 mixin | `KeenSensesEntityColorMixin.java` |
| 世界渲染 mixin | `KeenSensesClientLevelMixin.java` |

当前进展：

- 已从普通定时效果推进为更接近原版的开关能力。
- 已有后处理、轮廓、心形、降噪和心跳方向。
- 已加入持续耗血、大蒜过滤、Mist Form 距离缩短等逻辑。

验收重点：

- 多人时，没开 Keen Senses 的玩家不能看到轮廓。
- 玩家打开/关闭能力时，画面、声音、耗血都同步变化。
- 不同生命状态实体颜色要正确。

### 8.5 Mist Form

原版应该看到：

- 玩家像雾一样移动，不是简单透明或消失。
- 可以穿过多数非实体方块。
- 免疫摔落。
- 第一次受伤或主动攻击会退出。

当前代码：

| 内容 | 文件 |
|---|---|
| 能力逻辑 | `NyctoPowers.java`、`VampirePowerEvents.java` |
| 穿方块 | `MistFormBlockStateMixin.java` |
| 渲染隐藏 | `MistFormLivingEntityRendererMixin.java` |
| 同步 | `SyncMistFormPayload.java` |

当前差异：

- 机制已经接近。
- 持续白烟/灰烟雾团已增强，但还需要用真实客户端确认不会过稀或过浓。

完美标准：

- 玩家自己和别人都能清楚看出这是 Mist Form。
- 穿方块不会卡住或掉出世界。
- 退出时机符合原版。

## 9. HUD 和 UI 复刻

### 9.1 吸血鬼 HUD

原版 HUD 应包含：

- 血液条。
- 当前能力热栏。
- 当前能力冷却。
- 回血阻断提示。
- Dark Form 跳跃或能力相关提示。
- 必要时显示血液屏障层数。
- Vampirism 和 Night Vision 等状态应显示 Nycto 专属图标。

当前代码：

`src/main/java/moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.java`

验收标准：

- 普通玩家不应该看到吸血鬼 HUD。
- 成为吸血鬼后 HUD 自动出现。
- 血液变化、能力切换、冷却变化都能及时刷新。
- 汉化文本正常，不出现英文 key。
- Vampirism 当前已有 `nycto:vampirism` 状态效果和 `textures/mob_effect/vampirism.png` 图标，饮用吸血鬼血瓶后应显示红色蝙蝠倒计时图标。
- Night Vision 当前已新增 `nycto:night_vision` 状态效果和 `textures/mob_effect/night_vision.png` 图标，原版夜视效果继续隐藏并负责真实照明。
- 血液饥饿栏当前使用 `textures/gui/sprites/hud/blood/hunger/blood_*.png`，不再使用普通血滴资源冒充饥饿栏。

### 9.2 祭坛 UI

原版祭坛 UI 应包含：

- 能力选择区。
- 弱点选择区。
- 当前已选能力。
- 当前已选弱点。
- 勾选/高亮/不可选状态。
- 材料、经验、血瓶消耗反馈。

当前代码：

| 内容 | 文件 |
|---|---|
| 方块 | `VampireAltarBlock.java` |
| 菜单 | `VampireAltarMenu.java` |
| 屏幕 | `VampireAltarScreen.java` |
| 网络 | `SwapAltarPowersPayload.java` |

完美标准：

- 打开祭坛后布局和原版接近。
- 点击能力和弱点时有明确视觉反馈。
- 材料不足、等级不足、血瓶不足时不能错误兑换。
- 不要因为当前定制规则恢复原版组合副作用。

## 10. 声音、粒子、贴图资源检查

### 10.1 声音

声音注册位置：

`src/main/java/moriyashiine/nycto/neoforge/registry/NyctoSoundEvents.java`

资源位置：

`src/main/resources/assets/nycto/sounds.json`

需要逐项确认：

| 场景 | 玩家应该听到 |
|---|---|
| 饮用血瓶 | 饮用/血液相关声音 |
| 吸血 | 血液抽取或饮血声；如果目标穿猎人护甲挡住吸血，应听到 `entity.generic.blood_drain_blocked` 的失败反馈 |
| 吸血鬼实体 | idle、hurt、death |
| 猎人实体 | idle、hurt、death |
| Blood Barrier | 开启、被击中、破裂 |
| Bloodrush | 持续冲刺声 |
| Carnage | 开启和命中反馈 |
| Dark Form | 开启、攻击、飞行动作相关反馈 |
| Hypnotize | 催眠和解除催眠 |
| Keen Senses | 心跳和环境降噪 |
| Mist Form | 开启雾化 |

### 10.2 粒子

粒子注册位置：

`src/main/java/moriyashiine/nycto/neoforge/registry/NyctoParticleTypes.java`

客户端工厂：

`src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`

当前已有粒子类型包括：

- blood
- ambrosia
- bat swarm
- batstep
- hypnosis
- hypnotized
- thralled

验收标准：

- 使用能力时不是只有数值变化，必须有肉眼可见反馈。
- 粒子不能所有人都乱看见，应该按原版需要同步。
- 粒子不能在服务端专用环境调用客户端类。

### 10.3 贴图和模型

重点检查资源：

| 类型 | 资源例子 |
|---|---|
| 吸血鬼 | `textures/entity/vampire/vampire.png`、`vampire_eyes.png` |
| 猎人 | `textures/entity/hunter/vampire_hunter.png` |
| Dark Form | `textures/entity/dark_form/dark_form.png` |
| Blood Barrier | `textures/entity/blood_barrier/blood_barrier.png` |
| Carnage | `textures/entity/carnage/carnage_aura.png` |
| Bloodrush | `textures/entity/bloodrush/bloodrush_aura.png` |
| 祭坛 | `textures/gui/container/vampire_altar.png` |
| HUD | `textures/gui/sprites/hud/**` |
| 仆从 | `textures/entity/vampiric_thrall/**` |

规则：

- 资源文件存在不等于功能完成，必须有当前 NeoForge 渲染代码使用它。
- 物品图标正确不等于手持模型正确，长戟、木桩、弩都要在手上看。
- 实体第三人称正确不等于第一人称正确，Bat Form 和 Dark Form 必须单独验收第一人称。

## 11. 汉化检查

汉化文件位置：

`src/main/resources/assets/nycto/lang/zh_cn.json`

英文文件位置：

`src/main/resources/assets/nycto/lang/en_us.json`

当前检查结果：

- `zh_cn.json` 是有效 UTF-8 JSON。
- 中文键数量与 `en_us.json` 一致，当前没有英文有、中文缺的 key。
- 已用 Python JSON 解析复核当前文件合法；如果 PowerShell 直接显示乱码，优先按游戏和 UTF-8 解析结果判断。
- 能力使用反馈、仆从交互、血泉提示已补中英文 key，当前构建通过。
- 在 PowerShell 里直接显示成乱码时，不代表游戏内汉化坏了；这是终端编码显示问题。验收仍以游戏中文语言界面为准。

验收方法：

1. 进入中文语言。
2. 打开创造栏搜索 `nycto`。
3. 查看所有物品、方块、效果、按键、菜单标题、能力名称、能力说明。
4. 使用祭坛查看所有能力和弱点。
5. 使用命令或实机流程获得每种状态效果。

完美标准：

- 不出现 `item.nycto.xxx`、`block.nycto.xxx`、`power.nycto.xxx` 这种未翻译 key。
- 能力说明能让非技术玩家理解用途。
- 当前定制差异要在说明中避免误导，例如不要写“白天会被太阳惩罚”。

## 12. 验收路线

### 第一轮：能不能完整玩主线

按这个顺序测：

1. 创建新世界。
2. 找到或召唤吸血鬼。
3. 击杀吸血鬼。
4. 获得吸血鬼血瓶。
5. 饮用并转化为吸血鬼。
6. 右键吸血补血。
7. 制作或打开吸血鬼祭坛。
8. 选择能力。
9. 使用每个能力。
10. 做吸血鬼装备。
11. 白天活动，确认没有阳光惩罚。
12. 确认能力组合没有强制副作用。

### 第二轮：逐个验收能力视觉

每个能力都要记录：

- 自己第一人称看到什么。
- 自己第三人称看到什么。
- 其他玩家看到什么。
- 是否有声音。
- 是否有粒子。
- HUD 是否变化。
- 退出世界再进入是否保存或正确消失。

### 第三轮：猎人路线

按这个顺序测：

1. 找到或种植大蒜。
2. 制作大蒜相关物品。
3. 使用大蒜药水。
4. 放置大蒜花环。
5. 使用木桩近战。
6. 用弩发射木桩。
7. 使用火焰弹。
8. 穿猎人装备。
9. 使用猎人契约。
10. 观察猎人外观、武器、追踪、攻击、声音。
11. 退出世界再进入，确认猎人目标没有丢失。

### 第四轮：多人同步

至少两名玩家测试：

| 功能 | 重点 |
|---|---|
| 成为吸血鬼 | 其他玩家是否看到状态和外观变化 |
| 吸血 | 目标和旁观者是否看到正确反馈 |
| Bat Form | 其他玩家是否看到蝙蝠 |
| Dark Form | 其他玩家是否看到黑暗野兽 |
| Keen Senses | 未开启者不能看到专属轮廓 |
| Mist Form | 其他玩家是否看到雾化 |
| Blood Barrier | 其他玩家是否看到血环 |
| 仆从 | 其他玩家是否看到仆从皮肤和行为 |
| 猎人 | 猎人是否追踪正确目标 |

## 13. 后续开发优先级

如果目标是最快接近“完美复刻”，建议按下面顺序推进。

| 优先级 | 内容 | 原因 |
|---|---|---|
| 1 | `runClient` 视觉验收初始吸血鬼、夜视图标、吸血蓄力和野生吸血鬼六能力 | 这些已经接入代码，但还没用真实客户端逐项确认 |
| 2 | Dark Form 第一人称/第三人称实机校准 | 视觉差异最大，是吸血鬼能力核心卖点 |
| 3 | Hunter 契约、模型、武器、粒子和 AI 实机校准 | 猎人路线是另一半核心玩法 |
| 4 | Keen Senses 多人同步验收 | 很容易出现只有本地正确的问题 |
| 5 | Mist Form 雾化视觉增强 | 机制接近但视觉还不够像 |
| 6 | Vampiric Thrall 全实体外观和 AI | 覆盖实体多，容易漏 |
| 7 | 血瓶、匕首、配方、掉落 | 主流程细节，影响长期游玩 |
| 8 | 全量汉化和说明文案 | 功能越多，未翻译 key 越容易出现 |

## 14. 给后续子 agent 的完整任务说明模板

如果后续继续使用子 agent，可以直接复制下面这段作为任务前置信息。

```text
仓库：D:\mcmodding\nycto
目标：当前 1.21.1 NeoForge 移植版要尽量完美复刻 MoriyaShiine/nycto 原版功能和视觉。

必须保留的定制差异：
1. 不恢复白天阳光惩罚。
2. 不恢复能力/弱点组合后的强制副作用。

当前有效代码范围：
- src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java
- src/main/java/moriyashiine/nycto/neoforge/**
- src/main/resources
- src/main/generated

旧 common/client/mixin 包多数是参考，不代表当前构建会使用。

必须遵守 NeoForge 1.21.1：
- 注册走 DeferredRegister/注册事件。
- 服务端决定玩法，客户端只做表现。
- 网络同步走 CustomPacketPayload 和 NyctoPayloads。
- 客户端模型、渲染、HUD、粒子只在客户端事件里注册。
- mixin 只能用于事件做不到的最小视觉/碰撞场景。

请只做审计或只做指定模块，不要顺手重构无关代码。
报告必须包含：
1. 原版应该是什么表现。
2. 当前代码做到哪里。
3. 当前差异。
4. 需要改哪些具体文件。
5. 是否涉及客户端/服务端同步。
6. 是否可能误恢复用户明确不要的阳光惩罚或组合副作用。
```

## 15. 最终完成定义

当前版本可以被认为“复刻完成”，必须同时满足：

1. `gradlew build` 通过。
2. `gradlew runClient` 能进入主菜单，不崩溃；进入世界仍需 Computer Use 恢复后继续点击验收。

## 2026-06-04 追加核对与修复记录

本轮使用 4 个子 agent 分别核对视觉资源、数据/注册表、吸血鬼核心玩法、猎人路线。结论是：当前没有新的服务端世界加载级 P0，但客户端在进入世界并打开创造栏时暴露了创造栏重复药水/药箭崩溃，已修复。

已修复内容：

- 野生吸血鬼 Carnage aura 从普通玩家模型改回 `VampireModel`，避免狂怒红色轮廓不贴合长袍、耳朵和爪臂。
- `VampireModel` 行走动画速度按原版方向放大，并让追击/攻击期间显示爪臂，不再只在命中后短暂闪爪。
- NPC 猎人拿弩时会选择 `nycto:wooden_stake` 作为弹药，补上原版“猎人弩木桩路线”的无限弹药效果。
- 玩家用弩射出木桩后，会同时给弩和木桩加木桩冷却，冷却长度继续走猎人护甲减半规则。
- 目标实体血液储备新增 `SyncEntityBloodPayload`，服务端吸血/回血后同步到客户端，HUD 目标血滴预览不再长期停留在本地初始满血。
- 潜行吸血现在要求主手和副手都为空；HUD 蓄力条也使用同一条件。
- 失去吸血鬼身份时会清理 Bat Form、Keen Senses、Dark Form、Mist Form、Bloodrush、Carnage 和 Blood Barrier 残留状态。
- `halberd` 配方进度从不存在的 `#minecraft:diamond_tool_materials` 改为 `minecraft:diamond_axe`，服务端启动不再报警该标签缺失。
- 删除手动加入创造栏的 Potion / Splash Potion / Lingering Potion / Tipped Arrow 变体。1.21.1 创造栏会根据已注册 potion 自动生成这些变体，手动加入会导致 `Itemstack minecraft:potion/tipped_arrow already exists` 崩溃。

验证结果：

- `gradlew build --offline` 通过。
- `gradlew runServer --offline` 成功到 `Done`，未再出现 registry crash，也未再出现 `minecraft:diamond_tool_materials` 缺失警告。
- `gradlew runClient --offline` 成功完成资源加载、进入单人世界、玩家登录成功；修复创造栏重复药水/药箭后，没有生成新的客户端崩溃报告。
- Computer Use 当前仍不可用，错误为 `failed to write kernel assets: 系统找不到指定的路径。 (os error 3)`，所以本轮仍不能完成截图级视觉验收。

仍需实机视觉/行为验收：

- Bat Form 第一人称/第三人称翅膀、持物显示和色调是否与原版一致。
- 吸血鬼、猎人、Dark Form、护甲、棺材、血环、Carnage aura 的截图对照。
- 猎人骑马姿态、盾牌猎人印记、弩装填姿态、木桩弩模型。
- 吸血 HUD 目标血滴同步在多人/高延迟下的实际观感。
- 木桩投射物是否还需要进一步复刻原版绕甲/附魔绕过细节。
3. 主吸血鬼流程完整可玩。
4. 猎人路线完整可玩。
5. 14 个吸血鬼能力都有正确机制、视觉、声音和 HUD。
6. 吸血鬼实体、猎人、Dark Form、仆从都不是临时替代模型。
7. 单人和多人同步都通过。
8. 中文语言下没有明显未翻译 key。
9. 白天无阳光惩罚。
10. 能力/弱点组合无强制副作用。
11. 所有偏离原版的地方都在文档里明确标注为“用户定制”或“尚未完成”。

只要第 9 和第 10 条保持不变，其余部分都应该尽可能贴近原版 Nycto。

## 2026-06-04 第二轮专项核对与修复记录

本轮继续使用 2 个子 agent 做只读专项核对：

- 客户端视觉同步专项：Keen Senses 颜色、目标血量同步、仆从客户端状态、Bat Form 第一人称、Carnage aura。
- 猎人路线专项：猎人契约、配方覆盖、木桩投射物、药水与创造栏。

本轮已修复：

- Keen Senses 的描边和生命心形颜色改为复用服务端同一套血液分类规则。玩家、村民、灾厄村民、女巫、猪灵和怪物类现在不会再因为客户端只看 tag 而显示成错误颜色。
- 目标实体血量在玩家开始追踪实体时会补发一次 `SyncEntityBloodPayload`，避免重新进入视野时 HUD 短时间显示本地默认满血。
- 仆从客户端状态增加缺失实体清理；玩家开始追踪 Mob 时也会给非仆从显式同步 `false`，降低实体 ID 复用导致的仆从皮肤残留风险。
- Bat Form 不再取消整个 `RenderHandEvent`，只替换手臂显示，保留原版手持物渲染路径，避免第一人称拿物品时物品被吞掉。
- 木桩投射物命中吸血鬼时接入与近战木桩相同的压制和额外伤害路径；额外伤害从过高的 12 下调到 3，使投射物基础伤害 3 + 额外 3 接近原版约 6 点封顶语义。
- `nycto:wooden_stake` damage type 加入 `minecraft:bypasses_enchantments` tag，使木桩方块坠落伤害更接近原版“对吸血鬼绕护甲/绕附魔”的语义。
- 基础 `hunter_contract` 改回交易/合成材料，不再直接召唤猎人；`vampire_hunter_contract` 才负责召唤吸血鬼猎人。
- 手写 `resources` 配方同步生成数据：`vampire_hunter_contract = hunter_contract + garlic`，木桩接受 `#minecraft:logs`，大蒜涂层戟使用 `garlic_wreath`，`garlic_brew` 使用 `sugar`。

本轮验证：

- `gradlew build --offline` 通过。
- `gradlew runServer --offline` 到达 `Done (4.364s)!`，无崩溃。
- `gradlew runClient --offline` 到达客户端用户初始化、LWJGL、OpenAL、方块 atlas 创建阶段，未出现 `Crash report`、`Reported exception`、`BUILD FAILED`。
- 本轮启动日志：
  - `runclient-logs/runserver-20260604-080003.out.log`
  - `runclient-logs/runclient-20260604-080132.out.log`

仍未闭环：

- Computer Use 仍因 `failed to write kernel assets: 系统找不到指定的路径。 (os error 3)` 不可用，所以无法做窗口截图和真实点击验收。
- 乌头 `aconite` 尚未作为当前 NeoForge 有效作物/物品完整接回，因此凋零药水仍未恢复为“毒药水 + 乌头”的原版路线；这是后续复刻优先项。
- 大蒜药水目前同时存在原版 Potion 变体和自定义 `garlic_brew` 物品路线，视觉上是否完全等同原版 garlic brew 贴图还需要 runClient 创造栏截图确认。
- 木桩投射物本身仍走 Minecraft 普通箭的基础 damage source；现在已补弱点识别、压制、封疗和伤害校准，但若要 100% 复刻原版绕护甲/附魔细节，后续可能需要最小 mixin 或完整重写投射物命中伤害源。

下一轮有桌面能力后必须截图验收：

1. Keen Senses：玩家、村民、女巫、猪灵、蜘蛛/非 undead 怪物、无血实体的红/白/灰描边和生命心形颜色。
2. Bat Form：空手、主手持物、副手持物、双持时第一人称物品是否显示，第三人称蝙蝠模型是否贴合。
3. 仆从：换维度、离开再返回、实体消失后皮肤是否残留。
4. 猎人契约：基础契约右键不召唤，吸血鬼猎人契约可召唤，吸血鬼玩家会被正确设为目标。
5. 配方书：木桩所有原木可合成，大蒜涂层戟必须消耗大蒜花环，大蒜药水材料显示为糖。

## 2026-06-04 第三轮 HUD/祭坛复刻修复记录

本轮使用 3 个只读子 agent 分别核对 HUD/技能栏、祭坛升级、吸血鬼技能与视觉。结论是：当前最影响“像原版”的差异不是服务端崩溃，而是技能快捷栏和祭坛升级规则。

已修复：

- 技能快捷栏不再常驻显示在原版快捷栏上方。现在按住技能键时才接管原版快捷栏位置，使用 `hud/power_hotbar/vampire/*` 原资源绘制热栏、选择框、图标和冷却遮罩。
- 技能输入改回原版语义方向：按住技能键进入技能栏模式；技能栏模式下滚轮切换技能，数字键选择技能，右键使用当前技能。普通状态下物品快捷栏仍按原版工作。
- 祭坛升级池改为明确白名单，只包含 13 个可通过祭坛学习的吸血鬼主动能力；`night_vision` 不再计入祭坛技能数量，也不会占用 6 个祭坛技能位。
- 祭坛方块移除“拿吸血鬼血瓶右键祭坛立刻变吸血鬼”的额外流程。转化继续交给吸血鬼血瓶饮用和 `VAMPIRISM` 流程，祭坛只服务已成为吸血鬼的玩家。
- 祭坛主材料成本改回按玩家升级种子从弱/中/强材料 tag 中轮换，不再永远取 tag 第一个物品；升级后会刷新种子。
- 升级种子加入玩家同步数据，避免客户端祭坛 UI 显示的材料和服务端校验材料不一致。
- 副材料校验改回 `nycto:usable_blood_bottles` 标签，避免硬编码血瓶物品。
- 补充 `src/main/resources/data/nycto/tags/item/usable_blood_bottles.json`，包含普通血瓶和吸血鬼血瓶。

本轮仍故意不恢复：

- 不恢复白天阳光惩罚。
- 不恢复能力/弱点组合后的强制副作用。
- 不让祭坛重新强制赋予弱点。

验证结果：

- `gradlew build --offline` 通过。
- `gradlew runClient --offline` 通过启动验证，最新日志 `runclient-logs/runclient-20260604-082820.out.log` 显示客户端完成资源加载、OpenAL 启动、进入单人世界并登录玩家；未再出现技能栏 mixin 崩溃，也未再出现 aconite 作物模型父级缺失。
- 日志中仍有 vanilla/环境级警告：goat horn 缺失声音、NeoForge assets URL schema、`rendertype_entity_translucent_emissive` 的 Sampler2 警告。它们不是本轮新增崩溃。

仍需后续实机视觉验收：

1. 技能栏按住 R 时是否完全覆盖原版快捷栏位置，图标、选择框、冷却遮罩是否贴合原版。
2. Blood Barrier HUD 仍是当前移植版的独立文字/图标提示，尚未改回原版“叠在盔甲栏格子上”的表现。
3. Night Vision 目前仍不是原版可开关状态，缺少关闭语义和关闭音效。
4. 野生吸血鬼 Haemogenesis 与玩家技能的持续治疗节奏仍不完全一致。
5. Bat/Dark/Mist 长时间形态视觉状态还需要低频续包或更稳的持续同步策略。
## 2026-06-04 第四轮形态视觉复刻修复记录

本轮继续围绕原版吸血鬼形态视觉做专项收口，重点是 Bat Form、Mist Form、Dark Form 三个高可见度能力。

已修复内容：

- Bat Form 不再给整只蝙蝠额外叠加紫色染色。当前第三人称蝙蝠和第一人称翅膀都使用原版蝙蝠贴图自身颜色，避免出现“贴图正确但整体偏紫”的移植版视觉偏差。
- Mist Form 持续粒子移除额外 `CLOUD` 粒子，只保留 `SMOKE` 与 `WHITE_SMOKE`，并把持续粒子数量降到接近原版组件每 tick 少量烟雾的表现。
- Mist Form 开启/关闭瞬间也移除额外 `CLOUD` 爆发粒子，白烟数量按原版 `SMOKE 48 + WHITE_SMOKE 16` 的方向回调。
- Dark Form 玩家替换渲染路径补上第三人称手持物品渲染。此前独立 `DarkForm` 实体渲染器已有 `ItemInHandLayer`，但玩家变身后的事件渲染路径只画模型、光环和血屏障，没有走物品 layer；现在会按主手/副手把物品挂到 Dark Form 模型的爪子节点。

本轮仍未改动、需要后续实机截图校准的内容：

- Dark Form 玩家替换路径的跳跃/飞行动画仍是客户端根据玩家速度推断，不是完全等价于原版实体组件的 jump/fly 动画状态同步。当前已经接近，但若要做到逐帧一致，后续需要增加服务端形态动画状态同步包。
- Bat Form 第一人称翅膀虽然颜色回到原版，但具体屏幕位置仍需要窗口截图和第一人称持物场景确认。

验证结果：

- `gradlew build --offline` 通过。
- `gradlew runClient --offline` 已进入单人世界并登录玩家，最新日志为 `runclient-logs/runclient-20260604-084953.out.log`。
- 未发现 `Crash report`、`Reported exception`、`Mixin apply failed`、`Critical injection failure`、`BUILD FAILED`。
- 日志中存在 Mojang/Yggdrasil 公钥与 profile 请求的 SSL 握手错误，这是外部认证服务请求失败，不是本模组渲染或玩法代码导致的崩溃。
## 2026-06-04 第五轮实机反馈 P0 修复记录

本轮根据实机反馈处理三个严重问题：吸血蓄力流程、Bat Form 朝向/动画、Dark Form 倒立显示。同时从最新 `runClient` 日志中发现并修复 Blood Flechettes 投射物同步数据错误。

已修复内容：

- 吸血/匕首蓄力条位置移动到原版经验条位置。当前进度条使用 `guiHeight - 29`，对应 vanilla 经验条 `guiHeight - 32 + 3` 的 y 坐标。
- 吸血流程改为“Shift 蓄力，蓄满后右键吸血”。服务端现在会在蓄满时记录短暂的 charged 状态；右键吸血不再要求右键瞬间仍按住 Shift，只要求主副手为空、玩家是吸血鬼、目标可吸血且蓄力已满。
- 成功吸血会消费 charged 状态并进入冷却，避免冷却结束后不用重新蓄力连续吸血。
- Bat Form 和 Dark Form 的玩家替身渲染补上 vanilla `LivingEntityRenderer` 的基础模型矩阵：按玩家身体朝向旋转，执行 `scale(-1, -1, 1)`，再 `translate(0, -1.501, 0)`。这修复了此前在 `RenderLivingEvent.Post` 已经弹出模型矩阵后直接画模型导致的倒立/朝向异常。
- Bat Form 模型动画的头部朝向改为相对身体朝向，不再把绝对头部 yaw 直接传给模型。
- Blood Flechettes 投射物的 `defineSynchedData` 现在调用 `super.defineSynchedData(builder)`，修复使用该技能时报 `has not defined synched data value` 的服务端错误。

验证结果：

- `gradlew build --offline` 通过。
- `gradlew runClient --offline` 已进入单人世界并登录玩家，最新日志为 `runclient-logs/runclient-20260604-090451.out.log`。
- 最新启动日志未发现 `Crash report`、`Reported exception`、`Mixin apply failed`、`Critical injection failure`、`BUILD FAILED`、`has not defined synched data` 或 `Failed to process a synchronized task`。
- 日志开头的 `latest.log/debug.log` 删除失败是上一次客户端日志文件被占用导致的环境残留，不是本轮代码错误。

仍需实机复查：

- Bat Form 第三人称模型是否已经完全朝向正确，飞行/停靠动画是否与原版观感一致。
- Dark Form 第三人称是否不再倒立，持物是否挂在爪子上，跳跃/飞行动画是否自然。
- 吸血蓄力满后释放 Shift 再右键目标，是否符合“只要蓄满，右键都会吸血”的手感。

## 2026-06-04 第六轮六子 agent 核对、修复与 PR 准备记录

本轮按用户要求使用 6 个高强度子 agent 分别核对：祭坛/技能栏、血瓶和初始吸血鬼属性、血条 HUD 和吸血机制、Carnage 屏幕覆盖、Bat Form、Dark Form。结论是：当前版本已经能启动并进入世界，但要让原作者愿意审查 PR，必须把“原版复刻”和“本分支定制差异”明确分开说明。

本轮已修复或推进：

- 祭坛已解除玩家最多只能装配 6 个能力的限制。已获得能力列表增加滚轮窗口，超过显示栏位的能力不会丢失；升级消耗在 6 个能力后固定按高阶材料计算，避免无限涨价。
- 按 R 打开的技能快捷栏现在按“当前选中技能所在页”显示。超出第一屏的技能可以通过鼠标滚轮切换到下一页，数字键会选择当前页内对应技能。
- 普通血瓶修正为原版药水叠层式图标染色，并补回饮用逻辑方向：吸血鬼饮用补血，非吸血鬼饮用会受到中毒、饥饿和反胃。血瓶使用时长改为 40 tick。
- 吸血鬼初始血量改为满血开始，基础属性更贴近原版：移动速度提升、攻击力提升、安全落地高度小幅提升、跨格高度提升；移除移植版额外跳跃力量加成。
- Carnage 屏幕覆盖层补齐半透明混合渲染状态，不再依赖 Minecraft 当前渲染状态“碰巧正确”。
- 玩家血滴 HUD 横向位置回到原版饥饿栏语义的右侧坐标，降低与技能栏、准星目标血量预览错位的概率。
- 潜行蓄力和直接吸血现在只要求主手为空，不再因为副手持物直接禁止 HUD 预览和吸血。成功直接吸血不再额外治疗玩家，也不再发送“血瓶”系统消息，而是播放饮血声音。
- 可吸血优质目标不再把全部怪物粗暴算作优质血液，改为玩家、村民、灾厄村民、女巫、猪灵和 `HAS_QUALITY_BLOOD` 标签目标。
- Bat Form 第三人称休息动画不再简单等同于“站在地上”，而是检测头顶可附着方块，更接近原版蝙蝠倒挂/停靠语义；Bat Form 还补回“非 Boss 目标更难发现蝙蝠形态玩家”的可见度削减。
- Dark Form 客户端跳跃包现在只在黑暗形态激活时发送，避免普通吸血鬼跳跃也触发黑暗形态跳跃状态；形态视觉同步包也补入 jump cooldown。
- 修复一次新的运行期严重错误：仆从目标事件里直接 `setTarget(null)` 会递归触发目标变更事件，导致 `StackOverflowError`。现在只取消事件并改写即将设置的目标，不再递归调用目标设置。

本轮仍故意保留的定制差异：

- 不恢复阳光惩罚。原作者如果接受 PR，可以选择把这部分做成配置项或拒绝该行为差异。
- 不恢复能力和弱点组合后的强制副作用。弱点图标和选择结构可以保留用于视觉复刻，但当前分支不强制玩家承受原版副作用。
- 直接吸血仍保留“Shift 蓄满后右键吸血”的当前分支手感。这不是原版完全一致机制，但它是用户明确要求修复后的目标行为。

本轮最新验证：

- `gradlew build --offline` 通过。
- 最新 `runClient` 已保持运行，没有在本轮结束时主动关闭。日志 `runclient-logs/runclient-20260604-094543.out.log` 显示客户端完成资源加载、进入单人世界，并在 09:46:22 登录玩家 `Dev`。
- 最新日志暂未发现 `Crash report`、`Reported exception`、`Mixin apply failed`、`Critical injection failure`、`StackOverflowError`、`Failed to process a synchronized task` 或 `BUILD FAILED`。
- 日志里的 Mojang/Yggdrasil 公钥或 profile 请求失败、goat horn 缺声、中文输出乱码属于外部认证/原版资源/终端编码现象，不是本轮 Nycto 玩法代码崩溃。

提交给原作者 PR 时建议的说明方式：

- 标题应强调这是 “NeoForge 1.21.1 rewrite / port prototype”，不要声称已经 100% 完成原版所有细节。
- PR 正文应明确：本分支包含两类内容，一类是可供原作者采纳的 NeoForge 1.21.1 移植基础设施、视觉资源接线、崩溃修复和功能复刻；另一类是用户定制玩法差异，例如阳光免疫、弱点副作用禁用、蓄力吸血手感。原作者可以只采纳前者，拒绝或改造成配置项处理后者。
- 由于当前工作区包含大量生成数据和旧 Fabric 参考代码，正式 PR 最好保持为草稿 PR，让原作者先评估方向，再决定是否拆分成更小 PR。
