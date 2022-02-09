package net.thecorgi.masks;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.thecorgi.masks.init.blocks.HeadDisplayBlockEntity;
import net.thecorgi.masks.config.MasksConfig;
import net.thecorgi.masks.init.effects.BleedingStatusEffect;
import net.thecorgi.masks.init.blocks.HeadDisplayBlock;
import net.thecorgi.masks.init.LootTableRegistry;
import net.thecorgi.masks.init.MaskRegistry;
import net.thecorgi.masks.init.item.MaskBagItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MaskEmporium implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("masks");
	public static MasksConfig config;

	public static String ModID = "masks";
	public static Identifier id(String path) {
		return new Identifier(ModID, path);
	}

	public static final HeadDisplayBlock HEAD_DISPLAY = new HeadDisplayBlock(FabricBlockSettings
			.of(Material.WOOD).strength(1.0f).breakByHand(true).requiresTool().nonOpaque());
	public static BlockEntityType<HeadDisplayBlockEntity> HEAD_DISPLAY_BLOCK_ENTITY;

	public static final ItemGroup MASKS_GROUP = FabricItemGroupBuilder.create(
					id("mask_group"))
			.icon(() -> new ItemStack(MaskRegistry.RABBIT_MASK)).build();

	public static final MaskBagItem MASK_BAG = new MaskBagItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).group(MASKS_GROUP));

	public static void translateToFace(MatrixStack matrices, EntityModel<? extends LivingEntity> model,
									   LivingEntity entity, float headYaw, float headPitch) {
		if (entity.isInSwimmingPose() || entity.isFallFlying()) {
			if(model instanceof PlayerEntityModel) {
				PlayerEntityModel<AbstractClientPlayerEntity> ctx = (PlayerEntityModel<AbstractClientPlayerEntity>) model;
				matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(ctx.head.roll));
			}
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-45.0F));
		} else {

			if (entity.isInSneakingPose() && !model.riding) {
				matrices.translate(0.0F, 0.25F, 0.0F);
			}
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
		}
		matrices.translate(0.0F, -0.25F, -0.3F);
	}

	public static final StatusEffect BLEEDING = new BleedingStatusEffect();

	@Override
	public void onInitialize() {
		LOGGER.info("corgi's Mask Emporium has booted up.");
		Registry.register(Registry.ITEM, id( "mask_bag"), MASK_BAG);
//		Registry.register(Registry.BLOCK, id("head_display"), HEAD_DISPLAY);
//		Registry.register(Registry.ITEM, id( "head_display"), new BlockItem(HEAD_DISPLAY, new FabricItemSettings().group(MASKS_GROUP)));
		HEAD_DISPLAY_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "masks:head_display_block_entity", FabricBlockEntityTypeBuilder.create(HeadDisplayBlockEntity::new, HEAD_DISPLAY).build(null));

		MaskRegistry.init();
		LootTableRegistry.init();
		Registry.register(Registry.STATUS_EFFECT, id("bleeding"), BLEEDING);

		AutoConfig.register(MasksConfig.class, Toml4jConfigSerializer::new);
		config = AutoConfig.getConfigHolder(MasksConfig.class).getConfig();
	}
}