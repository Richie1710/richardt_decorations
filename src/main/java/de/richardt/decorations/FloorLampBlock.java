package de.richardt.decorations;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class FloorLampBlock extends HorizontalFacingBlock {
	public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");
    public FloorLampBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
		setDefaultState(getDefaultState().with(GLOWING, false));
    }

	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING);
		builder.add(GLOWING);
    }


    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 1, 1);
		if(!world.isClient() && hand == Hand.MAIN_HAND) {
			world.setBlockState(pos, state.cycle(GLOWING));
		}
        return ActionResult.SUCCESS;
    }

	@Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0f, 0f, 0f, 1f, 2.0f, 0.9f);
    }
}
