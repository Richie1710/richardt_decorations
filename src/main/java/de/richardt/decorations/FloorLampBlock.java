package de.richardt.decorations;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;


public class FloorLampBlock extends HorizontalFacingBlock {

	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public FloorLampBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
		Direction dir = state.get(FACING);
		switch(dir) {
			case NORTH:
				return VoxelShapes.fullCube();
			case SOUTH:
				return VoxelShapes.fullCube();
			case EAST:
				return VoxelShapes.fullCube();
			case WEST:
				return VoxelShapes.fullCube();
			default:
				return VoxelShapes.fullCube();
		}
	}


    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}
}
