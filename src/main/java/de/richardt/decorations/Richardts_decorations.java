package de.richardt.decorations;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Richardts_decorations implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("richardts_decorations");
	public static final CustomItem CUSTOM_ITEM = new CustomItem(new FabricItemSettings().maxCount(16));
	public static final CleanedCoalItem CLEANED_COAL_ITEM = new CleanedCoalItem(new FabricItemSettings());
	public static final PlasticItem PLASTIC_ITEM = new PlasticItem(new FabricItemSettings());
	public static final LightBulbItem LIGHT_BULB_ITEM = new LightBulbItem(new FabricItemSettings().maxCount(64));
	public static final QuadBladeItem QUAD_BLADE_ITEM = new QuadBladeItem(new FabricItemSettings().maxCount(16));
    public static final TvItem TV_ITEM = new TvItem(new FabricItemSettings());
	public static final TVBlock TV_BLOCK = new TVBlock(FabricBlockSettings.create().strength(4.0f).luminance(state -> state.get(TVBlock.POWER) ? 7 : 0));
	public static final TVBlock_large TV_BLOCK_LARGE = new TVBlock_large(FabricBlockSettings.create().strength(4.0f).luminance(state -> state.get(TVBlock_large.POWER) ? 10 : 0));
	public static final ExampleBlock EXAMPLE_BLOCK  = new ExampleBlock(FabricBlockSettings.create().strength(4.0f).requiresTool());
	public static final FloorLampBlock FLOORLAMPBLOCK = new FloorLampBlock(FabricBlockSettings.create().strength(4.0f).luminance(state -> state.get(FloorLampBlock.GLOWING) ? 15 : 0));
    public static final ThermoMixBlock THERMOMIXBLOCK = new ThermoMixBlock(FabricBlockSettings.create().strength(4.0f));
	public static final NotebookBlock NOTEBOOKBLOCK = new NotebookBlock(FabricBlockSettings.create().strength(4.0f));
	public static final MicrowaveBlock MICROWAVE_BLOCK = new MicrowaveBlock(FabricBlockSettings.create().strength(4.0f).luminance(state -> state.get(MicrowaveBlock.LIT) ? 8 : 0));;
	public static final BlockEntityType<MicrowaveBlockEntity> MICROWAVE_BLOCK_ENTITY = Registry.register(
		Registries.BLOCK_ENTITY_TYPE, new Identifier("richardts_decorations", "microwave_block_entity"),
		FabricBlockEntityTypeBuilder.create(MicrowaveBlockEntity::new, MICROWAVE_BLOCK).build()
	);
	private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
    	.icon(() -> new ItemStack(THERMOMIXBLOCK))
    	.displayName(Text.translatable("itemGroup.richardts_decorations.test_group"))
            .entries((context, entries) -> {
    		entries.add(CUSTOM_ITEM);
			entries.add(CLEANED_COAL_ITEM);
			entries.add(PLASTIC_ITEM);
			entries.add(TV_ITEM);
			entries.add(EXAMPLE_BLOCK);
			entries.add(ChargeableBlock.CHARGEABLE_BLOCK);
			entries.add(FLOORLAMPBLOCK);
			entries.add(THERMOMIXBLOCK);
			entries.add(NOTEBOOKBLOCK);
			entries.add(LIGHT_BULB_ITEM);
			entries.add(QUAD_BLADE_ITEM);
			entries.add(TV_BLOCK);
			entries.add(TV_BLOCK_LARGE);
			entries.add(MICROWAVE_BLOCK);

    	})
    	.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "custom_item"), CUSTOM_ITEM);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "cleaned_coal_item"), CLEANED_COAL_ITEM);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "plastic_item"), PLASTIC_ITEM);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "light_bulb_item"), LIGHT_BULB_ITEM);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "quad_blade_item"), QUAD_BLADE_ITEM);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "tv"), TV_ITEM);
		FuelRegistry.INSTANCE.add(CUSTOM_ITEM, 300);
		CompostingChanceRegistry.INSTANCE.add(CUSTOM_ITEM, 100.0f);
		Registry.register(Registries.ITEM_GROUP, new Identifier("richardts_decorations", "test_group"), ITEM_GROUP);
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "example_block"), EXAMPLE_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "example_block"), new BlockItem(EXAMPLE_BLOCK, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "chargeable_block"), ChargeableBlock.CHARGEABLE_BLOCK);
        Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "chargeable_block"), new BlockItem(ChargeableBlock.CHARGEABLE_BLOCK, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "floor_lamp_block"), FLOORLAMPBLOCK);
        Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "floor_lamp_block"), new BlockItem(FLOORLAMPBLOCK, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "tv_block"), TV_BLOCK);
        Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "tv_block"), new BlockItem(TV_BLOCK, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "tv_block_large"), TV_BLOCK_LARGE);
        Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "tv_block_large"), new BlockItem(TV_BLOCK_LARGE, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "thermo_mix_block"), THERMOMIXBLOCK);
        Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "thermo_mix_block"), new BlockItem(THERMOMIXBLOCK, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "notebook_block"), NOTEBOOKBLOCK);
        Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "notebook_block"), new BlockItem(NOTEBOOKBLOCK, new FabricItemSettings()));
		Registry.register(Registries.BLOCK, new Identifier("richardts_decorations", "microwave_block"), MICROWAVE_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("richardts_decorations", "microwave_block"), new BlockItem(MICROWAVE_BLOCK, new FabricItemSettings()));
	}
}
