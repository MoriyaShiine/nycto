package moriyashiine.nycto.common.world.level.block.entity;

import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.nycto.common.component.entity.BloodComponent;
import moriyashiine.nycto.common.component.entity.power.vampire.VampiricThrallComponent;
import moriyashiine.nycto.common.init.*;
import moriyashiine.nycto.common.world.item.consumeeffects.FillBloodConsumeEffect;
import moriyashiine.nycto.common.world.level.block.BloodFountainBlock;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class BloodFountainBlockEntity extends BlockEntity {
	public static final float GRAVITY = 0.06F;
	public static final int LIFETIME = 8;

	private static final int MAX_FEEDING_TICKS = 40;

	private final NonNullList<ItemStack> bottles = NonNullList.withSize(16, ItemStack.EMPTY);

	private LivingEntity feedingEntity = null;
	private int feedingTicks = 0;

	public BloodFountainBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(NyctoBlockEntityTypes.BLOOD_FOUNTAIN, worldPosition, blockState);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, BloodFountainBlockEntity entity) {
		if (entity.feedingEntity == null && level.getGameTime() % 10 == 0 && !entity.getTopStack().isEmpty()) {
			LivingEntity closest = null;
			for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(4, 3, 4), foundEntity -> isValidEntity(level, pos, foundEntity))) {
				if (closest == null || pos.distToCenterSqr(living.getEyePosition()) < pos.distToCenterSqr(closest.getEyePosition())) {
					closest = living;
				}
			}
			if (closest != null) {
				entity.feedingEntity = closest;
			}
		}
		if (entity.feedingEntity != null && isValidEntity(level, pos, entity.feedingEntity)) {
			ParticleOptions particle = entity.getTopStack().is(NyctoItems.AMBROSIA_BOTTLE) ? NyctoParticleTypes.AMBROSIA : NyctoParticleTypes.BLOOD;
			double bx = pos.getX() + 0.5;
			double by = pos.getY() + 1;
			double bz = pos.getZ() + 0.5;

			double tx = entity.feedingEntity.getX();
			double ty = entity.feedingEntity.getEyeY();
			double tz = entity.feedingEntity.getZ();

			double dx = tx - bx;
			double dy = ty - by;
			double dz = tz - bz;

			double vx = dx / LIFETIME;
			double vy = (dy - 0.5 * -GRAVITY * LIFETIME * LIFETIME) / LIFETIME;
			double vz = dz / LIFETIME;

			((ServerLevel) level).sendParticles(particle, bx, by, bz, 0, vx, vy, vz, 1);

			NyctoEntityComponents.VAMPIRIC_THRALL.maybeGet(entity.feedingEntity).ifPresent(VampiricThrallComponent::setFeeding);
			if (++entity.feedingTicks == MAX_FEEDING_TICKS) {
				int fillAmount = 0;
				if (entity.getTopStack().has(DataComponents.CONSUMABLE)) {
					for (ConsumeEffect consumeEffect : entity.getTopStack().get(DataComponents.CONSUMABLE).onConsumeEffects()) {
						if (consumeEffect instanceof ApplyStatusEffectsConsumeEffect applyEffectsConsumeEffect) {
							applyEffectsConsumeEffect.apply(level, entity.getTopStack(), entity.feedingEntity);
						}
						if (consumeEffect instanceof FillBloodConsumeEffect fillBloodConsumeEffect) {
							fillAmount += fillBloodConsumeEffect.fillAmount();
						}
					}
				}
				NyctoEntityComponents.BLOOD.get(entity.feedingEntity).fill(fillAmount);
				SLibUtils.playSound(entity.feedingEntity, NyctoSoundEvents.BLOOD_BOTTLE_DRINK.value());
				entity.bottles.set(entity.getTopIndex(), ItemStack.EMPTY);
				entity.updateFillState();
				entity.setChanged();

				entity.feedingEntity = null;
				entity.feedingTicks = 0;
			}
		} else {
			entity.feedingEntity = null;
			entity.feedingTicks = 0;
		}
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		bottles.clear();
		ContainerHelper.loadAllItems(input, bottles);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		ContainerHelper.saveAllItems(output, bottles);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}

	@Override
	public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	public boolean insertBottle(ItemStack stack) {
		if (level != null) {
			int slot = findEmptySlot();
			if (slot != -1) {
				if (!level.isClientSide()) {
					if (stack.is(NyctoItems.PLAYER_BLOOD_BOTTLE)) {
						stack = NyctoItems.BLOOD_BOTTLE.getDefaultInstance();
					}
					if (stack.is(NyctoItems.PLAYER_VAMPIRE_BLOOD_BOTTLE)) {
						stack = NyctoItems.VAMPIRE_BLOOD_BOTTLE.getDefaultInstance();
					}
					bottles.set(slot, stack);
					updateFillState();
					setChanged();
				}
				return true;
			}
		}
		return false;
	}

	public int getFilledBottles() {
		return getTopIndex() + 1;
	}

	private int getTopIndex() {
		for (int i = bottles.size() - 1; i >= 0; i--) {
			if (!bottles.get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}

	private ItemStack getTopStack() {
		int slot = getTopIndex();
		if (slot != -1) {
			return bottles.get(slot);
		}
		return ItemStack.EMPTY;
	}

	private void updateFillState() {
		BloodFountainBlock.FillState fillState = BloodFountainBlock.FillState.EMPTY;
		ItemStack stack = getTopStack();
		if (!stack.isEmpty()) {
			if (stack.is(NyctoItems.AMBROSIA_BOTTLE)) {
				fillState = BloodFountainBlock.FillState.AMBROSIA;
			} else {
				fillState = BloodFountainBlock.FillState.BLOOD;
			}
		}
		level.setBlock(worldPosition, getBlockState().setValue(BloodFountainBlock.FILL_STATE, fillState), Block.UPDATE_CLIENTS);
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
	}

	private int findEmptySlot() {
		for (int i = 0; i < bottles.size(); i++) {
			if (bottles.get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}

	public static boolean isHungryVampire(LivingEntity entity) {
		if (NyctoAPI.isVampire(entity)) {
			BloodComponent blood = NyctoEntityComponents.BLOOD.get(entity);
			return blood.canFill() && blood.getBlood() + NyctoConsumables.BLOOD_FILL_AMOUNT < BloodComponent.MAX_BLOOD;
		}
		return false;
	}

	private static boolean isValidEntity(Level level, BlockPos pos, LivingEntity entity) {
		BlockPos above = pos.above();
		return isHungryVampire(entity) && entity.getKnownMovement().horizontalDistanceSqr() == 0
				&& Math.sqrt(above.distToCenterSqr(entity.getEyePosition())) < 3
				&& level.clip(new ClipContext(Vec3.atCenterOf(above), entity.getEyePosition(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS;
	}
}
