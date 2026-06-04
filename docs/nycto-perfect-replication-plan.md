# Nycto 1.21.1 NeoForge 完美复刻差异与实施文档

日期：2026-06-03

目标版本：Minecraft 1.21.1 + NeoForge 21.1.x

当前工程：`D:\mcmodding\nycto`

原仓库：<https://github.com/MoriyaShiine/nycto>

参考清单：`C:\Users\Lenovo\Desktop\吸血鬼功能清单.txt`

NeoForge 规则参考：

- 官方网络文档：<https://docs.neoforged.net/docs/1.21.1/networking/>
- 1.21.1 `RegisterPayloadHandlersEvent` API：<https://lexxie.dev/neoforge/1.21.1/net/neoforged/neoforge/network/event/RegisterPayloadHandlersEvent.html>
- 1.21.1 `DeferredRegister` API：<https://aldak.netlify.app/javadoc/1.21.1-21.1.x/net/neoforged/neoforge/registries/deferredregister>

## 1. 这份文档要解决什么

这份文档用于指导当前 Nycto NeoForge 1.21.1 移植版，从“能启动、能玩一部分功能”推进到“尽可能完整复刻原版 Nycto 的玩法、视觉、声音和操作体验”。

文档同时保留两类信息：

- 给非技术人员看的结论：现在差在哪里、玩家会看到什么问题、修完后应该是什么样。
- 给开发人员看的落点：应该改哪些文件、补哪些注册、遵守哪些 NeoForge 1.21.1 规则。

用户明确要求的定制规则必须保留：

- 不需要旧版“白天怕阳光”的惩罚。
- 不需要旧版“能力和弱点组合后产生副作用”的机制。
- 其他原版玩法、视觉、声音、粒子、界面、模型、动画，应尽量复刻。

## 2. 当前版本的总体判断

当前版本不是完整移植版，而是一个“NeoForge 可启动骨架 + 局部玩法重写版”。

它已经有一些关键骨架：

- 可以注册吸血鬼、猎人、物品、方块。
- 能喝吸血鬼血瓶变成吸血鬼。
- 有血液数据和简化 HUD。
- 有 14 个能力的名字和简化效果。
- 有一部分猎人工具和反吸血鬼效果。
- 最近的 `runClient` 日志显示能进入集成世界，配方和进度能加载。

但它距离“完美复刻”还有明显差距：

- 大量原版视觉资源存在，但仍有不少没有被运行时代码使用。
- 原版客户端渲染层、粒子、声音、祭坛界面、能力 HUD、黑暗形态模型等仍未完整接入；血屏障模型和 layer 已开始接入当前 NeoForge 构建。
- 许多玩法只是“直接给结果”，不是原版流程。例如祭坛不是打开界面选择能力，而是右键按顺序给能力。
- 猎人路线缺少自然生成大蒜、作物生长、木桩弩发射、火焰弹投射物、牧师交易、猎人契约追踪。

### 2.1 非技术读者先看这里

如果只判断“能不能进游戏”，当前版本已经接近可玩。

如果判断“像不像原版 Nycto”，当前版本还不能算完成。最明显的问题不是数值，而是体验链路：

- 有些东西已经能用，但不像原版那样有完整动画、声音、粒子和界面反馈。
- 有些资源文件已经在项目里，但还没有被当前 NeoForge 运行时代码真正调用，所以玩家看不到。
- 有些功能用简化逻辑替代了原版逻辑，例如祭坛、部分能力状态、部分猎人道具。
- 当前版本故意不恢复阳光惩罚和能力弱点副作用，这是用户定制，不属于缺失。

这份文档后面的每个功能段都按同一个标准写：

- “原版应该是什么样”：给玩家和验收人员看。
- “当前移植版是什么样”：说明现在差在哪里。
- “需要改哪些代码”：给开发人员落地。
- “验收标准”：修完后怎么确认。

### 2.2 当前执行总表

| 模块 | 当前判断 | 玩家能感受到的问题 | 主要代码落点 |
| --- | --- | --- | --- |
| 主吸血鬼转化 | 部分完成 | 能成为吸血鬼，但转化流程、血液来源和反馈仍简化 | `neoforge/event/VampireLifecycleEvents.java`、`neoforge/util/NyctoBloodUtil.java`、`neoforge/data/NyctoPlayerData.java` |
| 血液与血瓶 | 部分完成 | 基础补血可用，但质量血、吸血过程和回血阻断仍需更像原版 | `neoforge/util/NyctoBloodUtil.java`、`neoforge/event/VampireBloodEvents.java` |
| 祭坛 | 明显缺失 | 不是原版选择界面，玩家无法用原版方式管理能力 | `neoforge/block/VampireAltarBlock.java`、未来 `neoforge/menu/`、`neoforge/client/gui/` |
| 棺材 | 部分完成 | 外观和交互还需核对所有木种、白天睡到夜晚流程 | `neoforge/block/CoffinBlock.java`、`neoforge/registry/NyctoBlocks.java` |
| 吸血鬼护甲 | 部分完成 | 数值已有，但外观、修复材料、套装反馈需继续验收 | `neoforge/item/VampireArmorItem.java`、`neoforge/registry/NyctoArmorMaterials.java` |
| 14 个吸血鬼能力 | 逐步补齐中 | Blood Barrier、Blood Flechettes、Bloodrush、Carnage 已大幅接近；Dark Form、Mist Form、Thrall 等仍差较多 | `neoforge/power/NyctoPowers.java`、`neoforge/event/VampirePowerEvents.java`、`neoforge/client/renderer/layer/` |
| 猎人路线 | 部分完成 | 有些道具能用，但自然生成、交易、契约追踪、投射物视觉仍需逐项补 | `neoforge/registry/NyctoHunterContent.java`、`neoforge/event/HunterEvents.java`、`neoforge/entity/hunter/` |
| 客户端视觉 | 最大差距之一 | 玩家看到的光效、形态、HUD 和粒子仍不完整 | `neoforge/client/NyctoClientNeoForge.java`、`neoforge/client/renderer/`、`neoforge/client/particle/` |
| 声音 | 部分完成 | 新补能力已有声音注册，但全能力音效还需逐项绑定 | `neoforge/registry/NyctoSoundEvents.java`、`src/main/resources/assets/nycto/sounds.json` |
| NeoForge 稳定性 | 构建通过过，但需持续验收 | 客户端能进不等于服务端安全；要避免服务端加载客户端类 | `common/NyctoNeoForge.java`、`neoforge/network/NyctoPayloads.java`、`META-INF/neoforge.mods.toml` |

## 3. 当前构建范围说明

这是理解差异的关键。

当前 `build.gradle` 只编译：

- `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`
- `src/main/java/moriyashiine/nycto/neoforge/**`

这意味着原仓库里很多旧实现虽然还在文件夹里，但不会进入最终 jar。

当前不进入 jar 的重要旧代码包括：

- `src/main/java/moriyashiine/nycto/client/**`
- `src/main/java/moriyashiine/nycto/common/init/**`
- `src/main/java/moriyashiine/nycto/common/payload/**`
- 大量 `mixin/**`
- 原版粒子、声音、菜单、HUD、renderer 的注册入口

所以判断功能是否已经完成，不能只看“资源文件是否存在”，必须看当前 NeoForge 编译范围里是否有对应注册和调用。

## 4. NeoForge 1.21.1 必须遵守的规则

### 4.1 注册规则

NeoForge 推荐使用 `DeferredRegister` 注册方块、物品、实体、声音、粒子、菜单等内容。

当前已有示例：

- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoBlocks.java`
- `src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoEntityTypes.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java`

后续新增内容也应该继续使用这个风格。

需要新增的注册类建议：

- `NyctoSoundEvents`
- `NyctoParticleTypes`
- `NyctoMenuTypes`
- `NyctoProjectileEntityTypes`
- `NyctoClientRenderers`

### 4.2 事件规则

NeoForge 有两个主要事件总线：

- Mod Bus：注册类事件、客户端 renderer 注册、GUI layer 注册、粒子 provider 注册等。
- Game Bus：玩家 tick、实体伤害、右键交互、治疗、掉落、交易等运行时事件。

当前入口在：

- `src/main/java/moriyashiine/nycto/common/NyctoNeoForge.java`

客户端入口在：

- `src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientNeoForge.java`

后续要注意：

- 客户端 renderer、HUD、key mapping 只能在客户端注册。
- 服务端不能加载客户端类，否则 dedicated server 会崩。

### 4.3 网络规则

NeoForge 1.21.1 使用 `CustomPacketPayload` 和 `RegisterPayloadHandlersEvent`。

当前已有：

- `src/main/java/moriyashiine/nycto/neoforge/network/NyctoPayloads.java`
- `SyncPlayerDataPayload`
- `UseActivePowerPayload`
- `SetActivePowerPayload`

当前网络只同步基础玩家数据，不足以复刻原版视觉。

后续需要新增 payload：

- 播放 Bloodrush 持续声音。
- 同步血屏障层数和破碎状态。
- 同步黑暗形态、蝙蝠形态、迷雾形态。
- 同步 Carnage、Keen Senses、Hypnotize 的客户端视觉状态。
- 同步能力冷却、持续耗血、当前形态剩余时间。

### 4.4 数据存储规则

当前玩家吸血鬼状态、血量、能力、冷却都存放在 `Player#getPersistentData()`。

对应文件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`

这能运行，但不是长期最稳的 NeoForge 写法。

建议后续改为 NeoForge Data Attachments：

- 玩家附件：吸血鬼状态、血液、最大血液、能力列表、当前能力、冷却、形态状态。
- 实体附件：血液类型、是否被催眠、是否为仆从、回血阻断、血刃标记。
- 世界附件：蝙蝠群、光环、血泉状态等需要世界级管理的数据。

短期可以继续用 `PersistentData`，但要明确这只是过渡方案。

### 4.5 资源规则

资源文件存在不等于功能完成。

例如：

- `sounds.json` 只是声明声音映射，代码里还必须注册 `SoundEvent` 并主动播放。
- `particles/*.json` 只是声明粒子贴图，代码里还必须注册 `ParticleType` 和粒子 provider。
- `textures/gui/container/vampire_altar.png` 存在，但必须有菜单和屏幕注册才会显示。
- 实体贴图存在，但必须注册 renderer 和模型层才会使用。

## 5. 主吸血鬼流程差异

### 5.1 吸血鬼生成

原版表现：

- 新月夜。
- 森林和针叶林类群系会出现吸血鬼。

当前状态：

- 已注册 `nycto:vampire`。
- 有 biome modifier：`src/main/resources/data/nycto/neoforge/biome_modifier/vampire_spawns.json`
- 但实体生成限制中还有额外森林判断，可能导致针叶林被排除。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/entity/Vampire.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoEntityTypes.java`

需要修复：

- 统一 biome modifier 和 spawn placement 的规则。
- 森林和针叶林都应该能生成。
- 保留新月限制。

验收标准：

- 新月夜在森林能刷吸血鬼。
- 新月夜在针叶林也能刷吸血鬼。
- 白天不会因为用户取消阳光惩罚而影响吸血鬼自然生成规则。

### 5.2 击杀掉落吸血鬼血瓶

原版表现：

- 玩家击杀吸血鬼，掉落吸血鬼血瓶，用于转化。

当前状态：

- 基本已复刻。

相关文件：

- `src/main/resources/data/nycto/loot_table/entities/vampire.json`

需要修复：

- 检查掉率是否与原版一致。
- 检查吸血鬼实体死亡声音和视觉是否复刻。

验收标准：

- 玩家击杀吸血鬼后能获得 `nycto:vampire_blood_bottle`。
- 掉落条件和原版一致。

### 5.3 饮用吸血鬼血瓶并转化

原版表现：

- 饮用血瓶后进入 Vampirism/Transformation 流程。
- 有饮用动作、声音、状态变化、最终变为吸血鬼。

当前状态：

- 当前是右键立即变成吸血鬼。
- 缺少转化过程。
- 缺少饮用反馈、声音、玻璃瓶返还。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java`
- `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- 旧参考：`TransformationMobEffect`、`VampireTransformation`

需要修复：

- 给吸血鬼血瓶实现真正的饮用流程。
- 饮用后给转化效果。
- 转化结束后再设置吸血鬼状态。
- 播放原版转换声音。
- 返回玻璃瓶。

用户定制：

- 转化后不启用阳光惩罚。

验收标准：

- 血瓶不是瞬间生效，而是有饮用过程。
- 饮用后能看到/听到转化反馈。
- 转化完成后玩家成为吸血鬼。
- 白天不会因为转化而受到阳光伤害。

### 5.4 吸血鬼基础体质

原版表现：

- 攻击、速度、跳跃、安全摔落增强。
- 有血液系统。
- 有血液保命。
- 有回血阻断系统。
- 旧版还有阳光暴露。

当前状态：

- 已有速度、攻击、跳跃、安全摔落加成。
- 有血液数据。
- 每 200 tick 消耗 1 点血液。
- 没有阳光惩罚，符合用户要求。
- 缺少血液保命。
- 回血阻断只在部分能力和猎人工具里简化存在。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoGameplay.java`
- `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampirePowerEvents.java`

需要修复：

- 补血液保命：致死伤害时消耗血液保留生命。
- 补完整回血阻断显示和清除逻辑。
- 补 HUD 血量表现。

用户定制：

- 不实现阳光伤害。
- 不让 Thin Blood 等弱点作为副作用出现。

验收标准：

- 吸血鬼基础能力明显增强。
- 血液不足会影响能力使用。
- 致死时如果有足够血液，可以触发血液保命。
- 白天不受阳光伤害。

## 6. 吸血与血瓶系统差异

### 6.1 潜行右键吸血

原版表现：

- 空手潜行右键可从有效生物吸血。
- 催眠、睡眠、迷雾等状态会影响是否安全吸血。

当前状态：

- 空主手、空副手、潜行右键可吸血。
- 会给玩家加血液、回血，并伤害目标。
- 目标过滤较粗。
- 未接催眠、睡眠、迷雾安全吸血。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/event/VampireLifecycleEvents.java`

需要修复：

- 按原版血液类型过滤目标。
- 类人生物、普通生物、无血生物要区分。
- 催眠目标允许更安全吸血。
- 迷雾形态、睡眠状态的特殊逻辑要接入。

验收标准：

- 不能从亡灵、无血实体、无效目标吸血。
- 催眠目标吸血表现更接近原版。
- 目标受伤、血液恢复、声音和粒子都正确。

### 6.2 普通血瓶

原版表现：

- 吸血鬼喝普通血瓶补血液。
- 非吸血鬼喝会中毒、饥饿、反胃。

当前状态：

- 基本已实现。
- 缺少饮用动作、饮用声音、细分血瓶来源。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoItems.java`

需要修复：

- 补饮用动画和声音。
- 补玻璃瓶返还。
- 补不同来源血液的效果差异。

### 6.3 吸血鬼匕首

原版表现：

- 攻击类人生物装血。
- 匕首满后可用玻璃瓶提取血瓶。
- 满匕首有不同模型。

当前状态：

- 可以蓄血和提取。
- 当前攻击任意活体都可能蓄血，范围过宽。
- 满匕首模型条件可能没有注册，视觉切换有风险。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/item/VampiricDaggerItem.java`
- `src/main/generated/assets/nycto/items/vampiric_dagger.json`

需要修复：

- 限定类人生物或原版允许的目标。
- 识别 `nycto:vampire` 实体。
- 注册 `nycto:full_dagger` 客户端模型条件。
- 补蓄血粒子和声音。

验收标准：

- 匕首攻击正确目标才蓄血。
- 满后模型变化。
- 手持玻璃瓶或合成能正确提取血瓶。

## 7. 吸血鬼祭坛差异

原版表现：

- 打开祭坛 UI。
- 放入材料、经验和血瓶。
- 玩家选择能力和弱点。
- 最多选择 6 个能力。
- 每隔一个能力需要配一个弱点。

用户定制：

- 不需要弱点产生副作用。
- 也不需要能力组合副作用。

当前状态：

- 已新增 NeoForge 版 `NyctoMenuTypes.VAMPIRE_ALTAR` 并在主入口注册。
- `VampireAltarBlock` 已从“右键按固定顺序给能力”改为：吸血鬼右键打开祭坛菜单。
- 非吸血鬼仍可用吸血鬼血瓶在祭坛处转化为吸血鬼并获得基础夜视能力。
- 已新增 `VampireAltarMenu`：包含 2 个祭坛材料槽、玩家背包槽位、最多 6 个当前能力、可选能力列表。
- 已接入原版材料成本规则方向：经验等级 + 主材料标签 `weak/average/strong_vampire_altar_upgrades` + 血瓶/吸血鬼血瓶。
- 已新增 `VampireAltarScreen`，使用原版 `textures/gui/container/vampire_altar.png` 背景和原版 sprite 框、勾选按钮、能力图标。
- 已新增 `SwapAltarPowersPayload`，客户端可在祭坛 UI 内交换当前能力顺序。
- 弱点栏目前保留视觉框位但不列出可选弱点，不会强制产生副作用。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/block/VampireAltarBlock.java`
- 旧参考：`VampireAltarMenu`、`VampireAltarScreen`、`AltarMenu`、`AltarScreen`
- 资源：`src/main/resources/assets/nycto/textures/gui/container/vampire_altar.png`

需要修复：

- `runClient` 肉眼验收界面布局：能力图标位置、材料槽位置、勾选按钮、tooltip 与原版是否一致。
- 验收服务端真实消耗：经验、主材料、血瓶数量是否正确。
- 验收选择能力后 HUD 和能力栏是否立即刷新。
- 后续如果要让弱点栏完全像原版，可只做装饰性显示或可选记录，但不能恢复强制负面副作用。

建议实现方式：

- 新增 `src/main/java/moriyashiine/nycto/neoforge/menu/VampireAltarMenu.java`
- 新增 `src/main/java/moriyashiine/nycto/neoforge/client/gui/VampireAltarScreen.java`
- 新增 `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoMenuTypes.java`
- 在 `NyctoClientNeoForge` 中注册 screen。

验收标准：

- 右键祭坛打开原版风格 UI。
- UI 中能看到能力图标、材料槽、确认按钮。
- 最多 6 个能力。
- 弱点不强制产生副作用。
- 选择能力后 HUD 和能力栏更新。

## 8. 棺材差异

原版表现：

- 白天可睡进棺材跳到夜晚。
- 有躺下、起床、占用等床类行为。
- 多木材版本。

当前状态：

- 多木材棺材存在。
- 白天右键直接设置时间到 13000。
- 没有真实睡眠流程。
- 没有占用状态。
- 没有玩家躺下视觉。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/block/CoffinBlock.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoBlocks.java`

需要修复：

- 恢复接近床的睡眠行为。
- 保留白天跳夜晚的吸血鬼规则。
- 保证不会再创建 vanilla bed block entity 导致崩溃。
- 处理多人世界中所有玩家睡眠/跳时间逻辑。

验收标准：

- 玩家能看到躺进棺材。
- 白天睡棺材会跳到夜晚。
- 夜晚交互反馈正确。
- 多人情况下不破坏服务器行为。

## 9. 吸血鬼护甲差异

原版表现：

- 吸血鬼护甲有专属外观。
- 成套提供血液上限、能力消耗降低、回血阻断抗性、阳光抗性等收益。

用户定制：

- 不需要阳光抗性，因为没有阳光惩罚。
- 其他护甲收益应复刻。

当前状态：

- 四件护甲存在。
- 已注册 `NyctoArmorMaterials.VAMPIRE`，不再继承铁甲材质外观。
- 已把现有 `vampire` 装备贴图映射到 1.21.1 可读取的 `textures/models/armor/vampire_layer_1/2.png` 路径。
- 仍保留当前简化回血/血液回复。
- 已按原版分层收益补上：穿 1 件时回血阻断只削弱治疗、不完全阻断；穿 2 件时潜行吸血额外获得血液；穿 3 件时能力消耗降为 75%。
- 已补当前移植版需要的动态血液上限收益：每件吸血鬼护甲提高 5 点最大血液，并通过现有同步让 HUD 显示有效上限。
- 第 4 件原版阳光抗性不需要实现，因为当前定制版没有阳光惩罚。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/item/VampireArmorItem.java`
- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoArmorMaterials.java`
- 旧参考：`ModArmorMaterials`、`VampireArmorRenderer`、`VampireArmorModel`

需要修复：

- 实机检查吸血鬼护甲穿戴 UV；如果新版 128x128 equipment 贴图在 1.21.1 layer 路径下偏移，需要制作 1.21.1 专用 layer 图。
- 继续核对原版完整数值和 tooltip 文案是否完全一致。

验收标准：

- 玩家穿上后不是铁甲外观。
- 成套有原版吸血鬼护甲收益。
- 不引入阳光相关需求。

## 10. 14 个吸血鬼能力差异

清单里写“13 个能力”，但实际列出了 14 个。原仓库 README 也列出 14 个：

1. Night Vision
2. Bat Form
3. Bat Swarm
4. Batstep
5. Blood Barrier
6. Blood Flechettes
7. Bloodrush
8. Carnage
9. Dark Form
10. Haemogenesis
11. Hypnotize
12. Keen Senses
13. Mist Form
14. Vampiric Thrall

当前文件：

- `src/main/java/moriyashiine/nycto/neoforge/power/NyctoPowers.java`
- `src/main/java/moriyashiine/nycto/neoforge/event/VampirePowerEvents.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/NyctoClientInputEvents.java`
- `src/main/java/moriyashiine/nycto/neoforge/client/gui/NyctoHudLayer.java`

当前总体状态：

- 14 个能力名都注册了。
- 多数能力只是简化效果。
- 缺少原版组件、持续状态、视觉、声音、粒子、模型。

### 10.1 Night Vision

当前：

- tick 持续给夜视。

缺失：

- 原版开关逻辑。
- 开关声音。
- 客户端夜视表现调节。

需要修复：

- 改为可开关能力。
- 播放原版夜视开关音效。
- HUD 显示开关状态。

### 10.2 Bat Form

当前：

- 已从短时飞行 buff 改为原版风格的开关型形态。
- 开启消耗 3 血；关闭或血量耗尽时退出形态，关闭后进入 200 tick 冷却。
- 开启期间每 300 tick 持续消耗 1 血；血不够时自动退出。
- 开启时记录玩家原本 `mayfly/flying`，关闭、失去吸血鬼状态或死亡清理时恢复原飞行状态。
- 开启期间授予飞行和慢落，并禁用疾跑；飞行移动速度按原版审计结论压到 0.75。
- 开启期间最大生命使用 `-70% ADD_MULTIPLIED_TOTAL`，按当前生命百分比压低和恢复。
- Bat Form 玩家造成的最终伤害降低为 0.1 倍。
- 已新增 `SyncBatFormPayload` 和 `BatFormClientState`，形态状态会同步给追踪该玩家的客户端。
- 已新增 `BatFormClientRenderEvents`：第三人称使用 Minecraft 原版 Bat 模型和 `textures/entity/bat.png` 渲染玩家。
- 已复用 NeoForge 专用客户端 mixin，在 Bat Form 期间隐藏玩家本体、盔甲和手持物渲染层，避免人形和蝙蝠叠在一起。
- 第一人称已先隐藏普通手臂和手持物，避免蝙蝠形态仍显示人手。
- 开关时播放 `power.bat_form.on/off` 并生成 48 个烟雾粒子。

缺失：

- 第一人称蝙蝠左右翅膀精确替换渲染；当前只是隐藏普通手臂/物品。
- 非 Boss 生物对 Bat Form 玩家的可见度减半。
- 静止/贴近地面时 resting 姿态需要 `runClient` 肉眼确认。
- Bat Form 下 Blood Barrier/Carnage 的蝙蝠专用 aura。

需要修复：

- 进游戏实际验收第三人称蝙蝠位置、朝向、缩放和飞行动画。
- 后续补第一人称翅膀，替代当前“隐藏普通手臂”的过渡方案。
- 后续用小范围 NeoForge 事件/mixin 补怪物可见度减半，不打开旧 `nycto.mixins.json`。

### 10.3 Bat Swarm

当前：

- 生成普通蝙蝠，范围伤害和回血。

缺失：

- 原版蝙蝠群组件。
- `bat_swarm_*` 粒子。
- 攻击/被攻击联动吸血。

需要修复：

- 新增世界或玩家能力状态。
- 注册自定义粒子。
- 让蝙蝠群跟随战斗事件工作。

### 10.4 Batstep

当前：

- 朝视线传送并造成路径伤害。

缺失：

- 原版 `batstep_*` 粒子。
- 声音。
- 眩晕效果。
- 路径表现。

需要修复：

- 注册粒子和声音。
- 补路径上连续粒子。
- 补真正的眩晕状态。

### 10.5 Blood Barrier

当前：

- NeoForge 端已经接入 1.21.1 可用的 `BloodBarrierModel` 和 `BloodBarrierLayer`。
- 玩家 renderer 和吸血鬼 renderer 已挂载血屏障 layer，使用原版 `textures/entity/blood_barrier/blood_barrier.png`。
- 血屏障层数已纳入 `SyncPlayerDataPayload`，客户端 HUD 和 renderer 可读。
- HUD 已显示 `textures/gui/sprites/hud/blood_barrier.png` 图标和剩余层数。
- 语义已向原版对齐：消耗 10 血，冷却 600 tick，持续 300 tick，最多 3 层。
- 受击时取消本次伤害；伤害大于等于 3 时破一层，小伤只播放命中反馈。
- 去掉了当前移植版原先额外给的吸收药水效果。
- 已新增 NeoForge 版 `AddBloodBarrierParticlesPayload`。
- 开盾时三圈血环都会按各自高度冒烟；破环时对应被打碎的血环冒烟；自然过期时剩余血环都会冒烟。
- 野生吸血鬼已新增 `BloodBarrierGoal` 等价 AI：有目标且冷却结束时会主动开血屏障。
- 吸血鬼实体已使用 `SynchedEntityData` 同步血屏障层数，renderer 不再依赖不可自动同步的 `PersistentData`。
- 吸血鬼实体的血屏障会存档保存层数、剩余 tick 和冷却。

缺失：

- 需要 `runClient` 肉眼确认血环高度、旋转方向和 HUD 图标位置。
- 需要实战确认野生吸血鬼开盾频率是否与原版手感一致。

需要修复：

- 若肉眼发现开盾过于频繁或过少，再调 `BloodBarrierGoal` 的优先级和实体冷却。

### 10.6 Blood Flechettes

当前：

- NeoForge 端已经恢复为真实血刃投射物。
- 使用 `BloodFlechetteProjectile` 发射 6 到 8 枚血刃。
- 使用 `BloodFlechetteRenderer` 和原版 `textures/entity/projectiles/blood_flechette.png`。
- 命中方块播放 `entity.blood_flechette.hit_block`。
- 命中实体播放 `entity.blood_flechette.hit_entity`。
- 命中有血液的生物时阻断回血 160 tick，并触发吸血鬼自身少量回血、补血和 `power.blood_flechettes.life_drain` 声音。

缺失：

- 需要 `runClient` 肉眼确认飞行姿态、贴图朝向和命中粒子是否与原版一致。
- 原版使用 ThrowableProjectile；当前 NeoForge 端为了复用 1.21.1 箭矢模型 renderer，实体继承 `AbstractArrow`，并禁用拾取。行为目标一致，但这是实现方式差异。

需要修复：

- 如果肉眼发现血刃旋转角度不一致，再改独立 renderer，而不是改回射线命中。
- 如果后续恢复原版“被标记者受攻击后额外生命吸取”事件链，需要把这部分从投射物命中中拆回独立事件。

### 10.7 Bloodrush

当前：

- NeoForge 端已经从“一次速度”改为 90 tick 持续冲刺状态。
- 使用原版风格的 `startAutoSpinAttack`，冲刺期间持续沿视线方向移动、重置摔落并清火。
- 碰撞墙体或潜行会提前结束 Bloodrush。
- 消耗/冷却已向原版对齐：消耗 3 血，冷却 200 tick。
- 新增 `SyncBloodrushPayload` 和 `BloodrushClientState`，其他客户端可知道玩家正在 Bloodrush。
- 新增 1.21.1 可用的 `BloodrushAuraLayer`，挂到普通/细手臂玩家 renderer，使用原版 `textures/entity/bloodrush/bloodrush_aura.png`。
- 冲刺期间会在眼部持续生成血粒子。
- 新增 `BloodrushSoundInstance`，Bloodrush 状态开始时播放跟随实体的循环声音，状态结束或实体移除时停止。

缺失：

- 原版 `bloodrush_riptide.png` 专用旋转视觉还没有独立接入；当前依赖 vanilla auto spin attack 表现。
- 需要 `runClient` 肉眼确认 aura 贴图贴合、旋转速度、冲刺距离和提前结束手感。
- 需要实机确认循环声音没有重复叠加，并且距离衰减与原版接近。

需要修复：

- 如果 vanilla auto spin attack 视觉不足，再接入 `bloodrush_riptide.png` 的专用 layer/渲染。

### 10.8 Carnage

当前：

- NeoForge 端已对齐原版基础参数：消耗 10 血，冷却 600 tick，持续 300 tick。
- 已使用 `AttributeModifier` 提供原版风格的 `+2 ATTACK_DAMAGE`，不再依赖 Strength/Speed 药水刷效果。
- 已新增 `SyncCarnagePayload` 和 `CarnageClientState`，客户端能知道玩家 Carnage 状态。
- 已新增玩家 `CarnageAuraLayer`，使用原版 `textures/entity/carnage/carnage_aura.png`。
- 已新增野生吸血鬼 `CarnageGoal`：有目标且生命低于 30% 时触发。
- 野生吸血鬼 Carnage 会保存剩余 tick 和冷却，启动后同样获得 `+2 ATTACK_DAMAGE`。
- 已新增 `VampireCarnageAuraLayer`，让野生吸血鬼也显示原版血色流动 aura。
- HUD 已叠加原版 `textures/misc/carnage_overlay.png`，并按原版前后 20 tick 淡入淡出。
- 命中时会给目标 80 tick 回血阻断作为当前移植版的流血近似表现，并生成血粒子。
- `power.carnage.hit` 已改为在受击者位置播放，并使用 0.8 到 1.2 的随机音高。

缺失：

- 原版 `BloodComponent.drainAttack(min(5, damage))` 和 `BleedTicks=80` 还没有真正的数据层复刻；当前用回血阻断和粒子近似。
- Carnage aura 当前覆盖玩家和 Vampire；原版还覆盖 Bat、DarkForm。
- 需要 `runClient` 肉眼确认玩家/野生吸血鬼 aura 贴图、overlay 透明度、低血量触发频率和命中音位置。

需要修复：

- 新增实体血液/流血数据层，替代当前近似回血阻断。
- 等 Bat Form 和 Dark Form 真实 renderer 完成后，再补 Bat/DarkForm Carnage aura。

### 10.9 Dark Form

当前：

- 已从“固定 16 秒药水 buff”改为原版风格的开关型形态。
- 开启消耗 3 血；关闭时播放 `power.dark_form.off` 并进入 200 tick 冷却。
- 开启期间每 300 tick 持续消耗 1 血；血不够时自动退出。
- 已使用 `AttributeModifier` 恢复原版核心属性方向：护甲、护甲韧性、攻击伤害、攻击速度、方块交互距离、实体交互距离、抗击退。
- 已补空中扇翼跳：客户端按住跳跃键会发送 `DarkFormJumpPayload`，服务端确认玩家处于 Dark Form、非飞行/游泳/鞘翅/攀爬/骑乘后执行跳跃。
- 已注册并播放 `entity.dark_form.flap`。
- Dark Form 下落时会降低下落速度并重置摔落距离；附近村民会进入恐慌活动。
- 未恢复旧版“弱点越多 Dark Form 越强”的倍率，符合用户定制规则。
- 已新增 NeoForge 版 `DarkForm` EntityType，尺寸按原版 0.8 x 2.75 注册，并注册基础属性。
- 已新增 `DarkFormModel` 和 `DarkFormRenderer`，使用原版 `textures/entity/dark_form/dark_form.png` 和主要模型部件/贴图坐标。
- 已新增 `SyncDarkFormPayload` 和 `DarkFormClientState`，Dark Form 状态会同步给追踪该玩家的客户端。
- 已新增玩家 `DarkFormVisualLayer`，开启 Dark Form 时会在玩家位置渲染 Dark Form 模型，作为当前 NeoForge 端的视觉替身基础。

缺失：

- 完整原版 DarkForm 动画，当前只有基础头部、手臂和翅膀简化动画。
- 完整第三人称视觉替换；当前是 Dark Form 模型 layer 覆盖在玩家位置，仍未彻底隐藏/替换原玩家模型。
- 第一人称 Dark Form 手臂。
- 禁穿甲、禁工具。
- 手动破坏方块。
- DarkForm Carnage aura。

需要修复：

- 完整迁移 `DarkFormAnimation`。
- 用最小范围客户端渲染处理隐藏原玩家模型和第一人称 Dark Form 手臂。
- 补行为限制。
- 补 DarkForm Carnage aura。

### 10.10 Haemogenesis

当前：

- 治疗、清负面效果、给再生。

缺失：

- 原版快速回血过程。
- 清除回血阻断。
- 灭火。
- 声音。

需要修复：

- 清除 `TAG_HEAL_BLOCK_UNTIL`。
- 补持续回血状态。
- 播放血液再生声音。

### 10.11 Hypnotize

当前：

- 范围缓慢、虚弱、发光、定身。

缺失：

- 按目标血量判断成功率。
- 潜行反向解除。
- 玩家短暂眩晕。
- 催眠后跟随和防御。
- 催眠粒子。

需要修复：

- 迁移 Hypnotized 状态。
- 补 AI 行为。
- 注册 `hypnosis_*` 粒子。

### 10.12 Keen Senses

当前：

- 夜视、附近实体发光。

缺失：

- 血型颜色高亮。
- 显示生命。
- 透墙和隐身目标处理。
- 声音降噪。
- 心跳声。
- `post_effect/keen_senses.json` 后处理。

需要修复：

- 迁移 Keen Senses 客户端 mixin 或等价 NeoForge 客户端事件。
- 接入后处理。
- 补目标颜色和血型信息。

### 10.13 Mist Form

当前：

- 已从固定短时 buff 改为原版风格的开关型形态。
- 开启消耗 3 血；关闭或被打出形态时播放 `power.mist_form.off` 并进入 200 tick 冷却。
- 开启期间每 300 tick 持续消耗 1 血；血不够时自动退出。
- 开启时生成烟雾和白烟，持续期间定期生成 `SMOKE` 与 `WHITE_SMOKE`。
- 形态中持续给予隐身、慢落、抗性，并免疫摔落伤害。
- 首次受到非摔落伤害时会取消本次伤害并显形。
- 形态中攻击会通过 NeoForge `CriticalHitEvent` 强制暴击，命中后显形。
- 已新增 `SyncMistFormPayload` 和 `MistFormClientState`，状态会同步给追踪该玩家的客户端。
- 已新增 NeoForge 专用 `nycto-neoforge.mixins.json`，只挂载当前已迁移的最小 mixin，不打开旧 `nycto.mixins.json`。
- 已补 `MistFormBlockStateMixin`：雾形态玩家可以穿过多数非完整碰撞方块，并尊重 `nycto:mist_form_unpassable` 黑名单标签。
- 已补 `MistFormLivingEntityRendererMixin`：雾形态客户端把玩家本体视为不可见，并关闭盔甲、手持物等渲染层，避免“雾里飘着装备”。

缺失：

- 客户端雾化渲染精修。
- Mist Form 对安全吸血判定的原版加成。

需要修复：

- 把 Mist Form 接入吸血判定。
- 进游戏实际验收雾形态：第三人称本体/装备隐藏、烟雾粒子密度、半砖/楼梯/门/梯子等标签方块穿透行为。

### 10.14 Vampiric Thrall

当前：

- 临时给目标 NBT owner。
- 简单跟随和协助攻击。

缺失：

- 永久仆从。
- 限定类人生物、已驯服马、狼。
- 低血量判定。
- 跟随/停留/游荡/防御模式。
- 喂血瓶。
- 专属皮肤、角、粒子。

需要修复：

- 迁移仆从组件。
- 迁移仆从 AI。
- 迁移仆从 renderer 和皮肤替换。
- 补交互模式切换。

## 11. 弱点和副作用处理

原版弱点：

- Humanity
- Hydrophobia
- Pyrophobia
- Rich Tastes
- Thin Blood
- Vile Presence

当前状态：

- `NyctoData.isRejectedWeakness` 会拒绝这些弱点。
- 祭坛当前不会强制选择弱点。
- 旧阳光相关 mixin 没有加载。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/NyctoData.java`
- `src/main/resources/META-INF/neoforge.mods.toml`
- `src/main/resources/nycto.mixins.json`

必须保持：

- 不启用旧阳光伤害。
- 不让弱点成为强制副作用。
- 不直接打开旧 `nycto.mixins.json`，否则可能恢复不想要的副作用并引发崩溃。

建议：

- 弱点图标和文本可以保留为“不会强制赋予”的说明。
- 如果祭坛 UI 需要复刻原版版式，可以显示弱点栏，但不让它们产生实际负面效果。

## 12. 猎人路线差异

### 12.1 野生大蒜

原版表现：

- 森林和沼泽生成野生大蒜。
- 可采集种植。

当前状态：

- 方块和物品注册了。
- 自然生成缺失。
- `garlic` 是普通方块，不是作物。

相关文件：

- `src/main/java/moriyashiine/nycto/neoforge/registry/NyctoHunterContent.java`
- `src/main/generated/data/nycto/worldgen/**`
- `build.gradle`

需要修复：

- 新增 NeoForge biome modifier。
- 不要排除野生大蒜 worldgen。
- 把 `garlic` 改成作物语义。

### 12.2 大蒜药水

当前：

- 有普通、喷溅、滞留三种物品。
- 但不是原版酿造系统。
- 喷溅/滞留不是投掷实体。

需要修复：

- 接入酿造配方。
- 使用药水实体或等价投掷表现。
- 补粒子和声音。

### 12.3 大蒜花环

当前：

- 可放置。
- 附近吸血鬼被压制。
- 已按原版语义恢复两种放置形态：点击侧面为挂墙，点击上表面为平放，点击底面不能放置。
- 已使用 `facing` + `down` 方块状态接入 `garlic_wreath_side` 和 `garlic_wreath_down` 两套模型。

缺失：

- 原版 aura component。
- 目前仍通过定时范围扫描发现附近花环，行为能用，但不是原版的 aura 组件记录方式。

需要修复：

- 如果继续追求内部实现一致，应把放置/移除花环的位置写入当前 NeoForge 组件系统，替代每 40 tick 的附近扫描。

### 12.4 长戟和大蒜长戟

当前：

- 用 `AxeItem` 简化。
- 能对吸血鬼加伤和阻断治疗。

缺失：

- 长戟专用动作/属性。
- 手持模型完整复刻。

需要修复：

- 保留当前基础伤害。
- 补手持模型、攻击范围、工具提示、原版属性。

### 12.5 木桩

原版表现：

- 可手持使用。
- 可由弩发射。
- 潜行右键可作为方块放置。
- 对吸血鬼强克制。

当前状态：

- 已从普通物品改成 `ArrowItem` 路线的木桩物品，因此弩会把它当作弹药装填和发射。
- 已注册 `nycto:wooden_stake` 投射物实体，并注册木桩箭 renderer，使用原版 `textures/entity/projectiles/wooden_stake.png`。
- 已注册 `nycto:wooden_stake` 方块，潜行右键可放置，破坏后掉回木桩。
- 已把 `nycto:wooden_stake` 加入 `minecraft:arrows` 标签，符合 1.21.1 弩的弹药选择规则。
- 已用客户端 item predicate 接入弩装填木桩的专属模型 `nycto:item/crossbow_wooden_stake`，不再依赖旧客户端 mixin。
- 命中吸血鬼有额外伤害。

需要修复：

- 继续核对原版木桩近战冷却、吸血鬼伤害上限和进度触发是否完全一致。
- 当前没有启用旧 mixin；如果要实现弩发射后的专属冷却，需要用 NeoForge 安全事件或只迁移极小范围 mixin，不能打开整套旧 mixin。

### 12.6 火焰弹

当前：

- 已从右键即时 raycast 爆炸改为真实投掷实体。
- 已注册 `nycto:firebomb` 投射物实体，客户端用 `ThrownItemRenderer` 渲染飞行中的火焰弹物品。
- 命中后会生成原版样式的 `nycto:firebomb` 临时火焰方块，并播放火焰、烟雾、药水溅射粒子和 `entity.firebomb.impact` 声音。

原版表现：

- 投掷实体。
- 飞行、命中、火焰、烟雾、范围伤害。

需要修复：

- 继续核对命中范围、铺火随机分布和雨中熄灭行为是否与原版完全一致。

### 12.7 猎人装备

当前：

- 四件装备注册。
- 有简化减伤和加伤计数。
- 已注册 `NyctoArmorMaterials.VAMPIRE_HUNTER`，猎人护甲不再使用铁甲外观。
- 已把现有 `vampire_hunter` 装备贴图映射到 1.21.1 可读取的 `textures/models/armor/vampire_hunter_layer_1/2.png` 路径。

缺失：

- 狼护甲
- 原版套装效果和 tooltip 仍需进一步核对。
- 由于当前目标版本是 1.21.1，原仓库新版 `assets/nycto/equipment/*.json` 不是主要读取路径；若实机出现 UV 偏差，需要再制作 1.21.1 专用 layer_1/layer_2 贴图，而不是直接依赖新版 equipment JSON。

需要修复：

- 实机检查猎人护甲穿戴 UV。
- 补狼护甲和完整套装语义。
- 补狼护甲 layer 和物品。

### 12.8 猎人契约和猎人 AI

当前：

- 右键在玩家前面生成猎人。
- 猎人是近战怪，追踪吸血鬼。

原版表现：

- 契约来自牧师交易。
- 猎人追踪目标。
- 有远处生成、失败条件、目标位置寻路。
- 有弩/木桩切换、开门、马匹等行为。

需要修复：

- 加牧师交易。
- 恢复契约目标。
- 恢复 `PathToContractPosGoal` 和 `UltimateTargetGoal` 等价逻辑。
- 恢复猎人战斗装备和掉落规则。

## 13. 视觉、声音、粒子、UI 完美复刻清单

这是当前最大的工作量。

### 13.1 实体模型和渲染

需要接入：

- `VampireRenderer`
- `VampireModel`
- `HunterRenderer`
- `HunterModel`
- `DarkFormRenderer`
- `DarkFormModel`
- `DarkFormAnimation`
- `BloodBarrierLayer`
- `BloodrushAuraLayer`
- Carnage 四类 aura layer
- `VampiricThrallRenderer` 系列
- `WoodenStakeRenderer`
- `BloodFlechetteRenderer`
- `AconiteArrowRenderer`，如果保留乌头内容

当前问题：

- 当前吸血鬼和猎人基本使用 vanilla humanoid 模型。
- 黑暗形态没有实体和 renderer。
- 血屏障 layer 已接入玩家/吸血鬼 renderer；血冲刺、屠戮仍没有 layer。

### 13.2 粒子

需要注册：

- `blood`
- `ambrosia`
- `bat_swarm_left`
- `bat_swarm_center`
- `bat_swarm_right`
- `batstep_left`
- `batstep_center`
- `batstep_right`
- `hypnosis_indicator`
- `hypnosis_indicator_inverse`
- `hypnosis_small`
- `hypnosis_star`
- `hypnotized`
- `thralled`

需要新增：

- `NyctoParticleTypes`
- 客户端 `RegisterParticleProvidersEvent`

当前问题：

- 只有资源文件，没有当前 NeoForge 运行时注册。

### 13.3 声音

需要注册：

- 祭坛使用
- 血液阻断
- 移除能力
- 变回人类
- 变成吸血鬼
- 血液飞刃命中
- 蝙蝠步
- 蝙蝠形态开启
- 蝙蝠群
- 血冲刺
- 血液屏障使用、命中、破裂
- 血液飞刃使用、生命吸取
- 屠戮使用、命中
- 黑暗形态开启
- 血液再生
- 催眠和反向催眠
- 敏锐感知开启
- 迷雾形态开启
- 夜视开关
- 仆从转化

需要新增：

- `NyctoSoundEvents`
- 在各能力和交互点播放声音
- 必要时补字幕 key

当前问题：

- `sounds.json` 存在，但没有当前编译范围内的 SoundEvent 注册类。
- 多数能力完全没有播放自有声音。

### 13.4 HUD

原版应有：

- 血量 HUD。
- 回血阻断 HUD。
- 能力热栏。
- 冷却遮罩。
- 血屏障层数。
- 蓄力跳。
- 阳光暴露 HUD。
- Carnage HUD。
- Keen Senses HUD。

用户定制：

- 阳光暴露 HUD 可以移除或保留为永不触发。

当前：

- 已把原先的红色文字面板替换为原版资源驱动的 HUD。
- 血量现在使用 `textures/gui/sprites/hud/blood/blood_0..7.png` 分段血滴显示，并保留数值文本作为当前过渡辅助。
- 能力热栏现在使用 `textures/gui/sprites/hud/power_hotbar/vampire/hotbar.png`、`hotbar_overlay.png`、`selection.png`、`selection_overlay.png`。
- 能力图标继续使用 `textures/power/*.png`，冷却期间有半透明遮罩。
- 血屏障层数继续显示 `textures/gui/sprites/hud/blood_barrier.png` 和层数。
- Carnage overlay 已使用原版 `textures/misc/carnage_overlay.png`。

需要修复：

- `runClient` 肉眼确认血滴位置、能力热栏位置是否与原版和 vanilla hotbar 不冲突。
- 补治疗阻断 heart 替换：需要先把 `TAG_HEAL_BLOCK_UNTIL` 同步到客户端，才能稳定使用 `hud/heal_block/*`。
- 补蓄力跳 HUD：需要确认当前 NeoForge 版蓄力跳状态是否已完整迁入。
- 补 Keen Senses HUD/outline/post-effect。
- 阳光暴露 HUD 不恢复惩罚；如保留也应永不触发负面状态。

### 13.5 祭坛 UI

需要：

- `VampireAltarScreen`
- `VampireAltarMenu`
- `textures/gui/container/vampire_altar.png`
- 能力图标
- 勾选/确认按钮

当前：

- 已新增 NeoForge 版 `VampireAltarMenu`、`VampireAltarScreen`、`NyctoMenuTypes`。
- 已使用原版 `textures/gui/container/vampire_altar.png`、能力图标、祭坛 sprite 框和勾选按钮。
- 已支持材料槽、背包、能力选择、确认按钮和当前能力顺序交换。
- 仍需要 `runClient` 肉眼验收布局和点击流程。

### 13.6 后处理

需要：

- `post_effect/keen_senses.json`
- Keen Senses 客户端状态判断。
- 开启时加载后处理。
- 关闭时移除后处理。

当前：

- 资源存在，但没被使用。

## 14. 数据包和资源清理建议

当前资源里有很多未注册内容，例如：

- werewolf
- aconite
- banner patterns
- 部分旧 damage type
- 部分旧 worldgen

这些不一定会崩，但会误导验收。

建议：

- 当前明确不做的狼人内容，放入“未来功能”清单。
- 如果资源保留，文档和语言文件要明确“占位内容，不代表已实现”。
- 需要防止配方、进度、战利品表引用未注册 ID。

重复资源风险：

- `src/main/resources` 和 `src/main/generated` 有同名文件。
- `build.gradle` 用 `DuplicatesStrategy.EXCLUDE`，可能静默选择其中一份。

建议：

- 对关键 recipe、loot、advancement 做唯一来源整理。
- datagen 输出和手写资源要分清职责。

## 15. 推荐实施顺序

### 第一阶段：修主流程

目标：玩家能完成完整吸血鬼路线。

任务：

- 修正吸血鬼生成规则。
- 补血瓶饮用和转化流程。
- 补血液保命。
- 补吸血目标过滤。
- 补匕首目标过滤和满匕首模型条件。
- 祭坛菜单和屏幕已补；还需要 `runClient` 验收 UI 布局、消耗和能力刷新。

验收：

- 击杀吸血鬼到成为吸血鬼的完整流程可玩。
- 能通过祭坛选择能力。
- 不出现阳光惩罚。
- 不出现强制弱点副作用。

### 第二阶段：修能力语义

目标：14 个能力行为接近原版。

任务：

- 改开关型能力。
- 补持续耗血。
- 补回血阻断和清除。
- 补 Dark Form、Mist Form、Thrall 等复杂状态。
- 补能力同步 payload。

验收：

- 每个能力都能独立测试。
- 行为与原 README 描述一致。
- HUD 能正确显示状态和冷却。

### 第三阶段：修猎人路线

目标：猎人路线不再是简化版。

任务：

- 野生大蒜自然生成。
- 大蒜作物。
- 大蒜药水酿造和投掷。
- 木桩弩发射和落地方块。
- 火焰弹投射物。
- 猎人契约和牧师交易。
- 猎人 AI。

验收：

- 玩家能从自然世界开始获得大蒜。
- 能制作并使用完整猎人工具。
- 能通过契约召唤追踪型猎人。

### 第四阶段：视觉和声音复刻

目标：让体验看起来和听起来接近原版。

任务：

- 注册所有 SoundEvent。
- 注册所有 ParticleType 和 provider。
- 迁移 renderer、model、layer。
- 迁移护甲 renderer。
- 迁移祭坛 UI。
- 迁移完整 HUD。
- 迁移 Keen Senses 后处理。

验收：

- 每个能力至少有对应声音、粒子或模型表现。
- 祭坛 UI 与原版贴图一致。
- 护甲、黑暗形态、血屏障、血冲刺、屠戮、仆从皮肤都能看见。

### 第五阶段：服务端和数据稳定

目标：能稳定用于单人和多人。

任务：

- Dedicated server 测试。
- Data Attachments 替代关键 `PersistentData`。
- 死亡复制、维度切换、重登同步。
- 数据包 reload 检查。

验收：

- `runClient` 能进世界。
- `runServer` 不加载客户端类。
- 多人同步正常。
- 没有配方、战利品、标签、模型、声音硬错误。

## 16. 建议的文件结构

为了避免当前 `neoforge/**` 变成一个大杂烩，建议后续结构如下：

```text
src/main/java/moriyashiine/nycto/neoforge/
  data/
    NyctoPlayerData.java
    NyctoEntityData.java
  registry/
    NyctoBlocks.java
    NyctoItems.java
    NyctoEntityTypes.java
    NyctoSoundEvents.java
    NyctoParticleTypes.java
    NyctoMenuTypes.java
  event/
    VampireLifecycleEvents.java
    VampirePowerEvents.java
    HunterEvents.java
    VampireArmorEvents.java
  power/
    NyctoPowers.java
    impl/
      BatFormPower.java
      BloodBarrierPower.java
      DarkFormPower.java
  client/
    NyctoClientNeoForge.java
    gui/
    renderer/
    particle/
    sound/
  network/
    NyctoPayloads.java
    payloads...
  entity/
    Vampire.java
    DarkForm.java
    projectile/
    hunter/
  block/
  item/
  menu/
```

## 17. 最终验收清单

### 主流程

- 新月夜森林刷吸血鬼。
- 新月夜针叶林刷吸血鬼。
- 击杀吸血鬼掉血瓶。
- 饮用血瓶有转化过程。
- 转化后不怕阳光。
- 吸血鬼有血液 HUD。
- 潜行右键能正确吸血。
- 血瓶能补血。
- 匕首能蓄血和提取。
- 祭坛 UI 能选择能力。
- 棺材白天能睡到夜晚。
- 吸血鬼护甲有专属外观和加成。

### 14 个能力

- Night Vision 可开关。
- Bat Form 有蝙蝠视觉。
- Bat Swarm 有蝙蝠群视觉和吸血逻辑。
- Batstep 有路径粒子和声音。
- Blood Barrier 有三圈血环。
- Blood Flechettes 有投射物。
- Bloodrush 有冲刺光效。
- Carnage 有血色光效和流血逻辑。
- Dark Form 有模型和动画。
- Haemogenesis 能清回血阻断。
- Hypnotize 有催眠粒子和 AI。
- Keen Senses 有后处理和血型高亮。
- Mist Form 能穿非实体方块并雾化。
- Vampiric Thrall 是永久仆从，有皮肤和模式切换。

### 猎人路线

- 野生大蒜自然生成。
- 大蒜能种植和成熟掉落。
- 大蒜药水能酿造和投掷。
- 大蒜花环有方向和 aura。
- 长戟手持视觉正确。
- 木桩可手持、弩发射、落地方块。
- 火焰弹是投射物。
- 猎人装备有专属外观。
- 牧师能卖契约。
- 契约召唤猎人追踪目标。

### 视觉和声音

- 所有能力至少有原版对应声音。
- 所有自定义粒子注册并能显示。
- 祭坛 UI 使用原版贴图。
- HUD 不再是简化文字面板。
- 护甲不是铁甲外观。
- 黑暗形态、血屏障、血冲刺、屠戮视觉明显。
- Keen Senses 后处理能启用和关闭。

### NeoForge 稳定性

- `gradlew clean build` 通过。
- `gradlew runClient` 能进入世界。
- `gradlew runServer` 能启动 dedicated server。
- 新世界和已有世界都能加载。
- 数据包 reload 无缺失 tag、recipe、loot、advancement 错误。
- 客户端类不会在服务端加载。

## 18. 当前最重要的结论

如果目标是“能玩”，当前版本已经有基础。

如果目标是“完美复刻原版”，当前版本还需要一次系统性移植，尤其是客户端视觉、声音、粒子、祭坛 UI 和复杂能力状态。

最正确的下一步不是继续零散补丁，而是按本文件的顺序推进：

1. 先补主流程。
2. 再补能力语义。
3. 再补猎人路线。
4. 最后集中补视觉和声音。
5. 全程保持用户定制：不恢复阳光惩罚，不恢复强制弱点副作用。
