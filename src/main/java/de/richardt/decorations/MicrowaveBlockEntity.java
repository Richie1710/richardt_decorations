package de.richardt.decorations;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MicrowaveBlockEntity extends AbstractFurnaceBlockEntity {

    public MicrowaveBlockEntity(BlockPos pos, BlockState state) {
        super(Richardts_decorations.MICROWAVE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING);
    }

    @Override
    public int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel);
    }


    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public Text getContainerName() {
        return Text.translatable("block.richardts_decorations.microwave_block");
    }
}
