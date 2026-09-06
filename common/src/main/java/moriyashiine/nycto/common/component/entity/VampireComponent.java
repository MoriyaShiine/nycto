package moriyashiine.nycto.common.component.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.init.NyctoEntityComponents;
import moriyashiine.nycto.common.init.NyctoMobEffects;
import moriyashiine.nycto.common.init.NyctoPowers;
import moriyashiine.nycto.common.world.transformation.VampireTransformation;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class VampireComponent implements AutoSyncedComponent, CommonTickingComponent {
	private static final AttributeModifier STEP_HEIGHT_MODIFIER = new AttributeModifier(VampireTransformation.MODIFIER_ID, 1, AttributeModifier.Operation.ADD_VALUE);

	private final Player obj;
	private int jumpStrength = 0;

	public VampireComponent(Player obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		jumpStrength = input.getIntOr("JumpStrength", 0);
	}

	@Override
	public void writeData(ValueOutput output) {
		output.putInt("JumpStrength", jumpStrength);
	}

	@Override
	public void tick() {
		if (NyctoAPI.isVampire(obj)) {
			if (obj.onGround() && obj.isShiftKeyDown() && NyctoEntityComponents.SYNCED_CONFIG_VALUES.get(obj).hasVampireChargeJump() && !NyctoAPI.hasSunDebuff(obj) && !NyctoAPI.hasPower(obj, NyctoPowers.HUMANITY)) {
				if (jumpStrength < 20) {
					jumpStrength++;
				}
			} else {
				jumpStrength = 0;
			}
		} else {
			jumpStrength = 0;
		}
	}

	@Override
	public void serverTick() {
		tick();
		SLibUtils.applyAttributeModifier(obj, Attributes.STEP_HEIGHT, STEP_HEIGHT_MODIFIER, NyctoAPI.isVampire(obj) && NyctoEntityComponents.SYNCED_CONFIG_VALUES.get(obj).hasVampireStepHeight() && !obj.hasEffect(NyctoMobEffects.VAMPIRE_WARD) && !NyctoAPI.hasSunDebuff(obj) && !NyctoAPI.hasPower(obj, NyctoPowers.HUMANITY));
		if (obj.level().getGameTime() % 6000 == 0) {
			obj.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
		}
	}

	public void sync() {
		NyctoEntityComponents.VAMPIRE.sync(obj);
	}

	public float getChargeJumpBoostProgress() {
		return jumpStrength / 20F;
	}
}
