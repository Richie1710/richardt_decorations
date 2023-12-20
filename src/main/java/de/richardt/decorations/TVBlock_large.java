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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class TVBlock_large extends HorizontalFacingBlock {
	public static final BooleanProperty POWER = BooleanProperty.of("power");
    public TVBlock_large(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
		setDefaultState(getDefaultState().with(POWER, false));
    }

	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING);
		builder.add(POWER);
    }


    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 1, 1);
		if(!world.isClient() && hand == Hand.MAIN_HAND) {
			world.setBlockState(pos, state.cycle(POWER));
		}
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        VoxelShape baseShape = createBaseShape();
        //VoxelShape mirroredShape = mirrorShape(baseShape);
        VoxelShape mirroredShape = baseShape;

        return rotateShape(mirroredShape, direction);
    }

    private VoxelShape createBaseShape() {
        return VoxelShapes.union(
            VoxelShapes.cuboid(27/16f, 2/16f, 14/16f, 29/16f, 14/16f, 16/16f),
            VoxelShapes.cuboid(2/16f, 0/16f, 14/16f, 29/16f, 2/16f, 16/16f),
            VoxelShapes.cuboid(2/16f, 14/16f, 14/16f, 29/16f, 16/16f, 16/16f),
            VoxelShapes.cuboid(2/16f, 2/16f, 14/16f, 4/16f, 14/16f, 16/16f),
            VoxelShapes.cuboid(4/16f, 2/16f, 15/16f, 27/16f, 14/16f, 16/16f)
        );
    }

    private VoxelShape mirrorShape(VoxelShape shape) {
        VoxelShape mirroredShape = VoxelShapes.empty();

        for (Box box : shape.getBoundingBoxes()) {
            // Spiegeln entlang der Z-Achse
            mirroredShape = VoxelShapes.union(mirroredShape, VoxelShapes.cuboid(
                box.minX, box.minY, 1 - box.maxZ, box.maxX, box.maxY, 1 - box.minZ));
        }

        return mirroredShape;
    }


    private VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        VoxelShape rotatedShape = VoxelShapes.empty();

        for (Box box : shape.getBoundingBoxes()) {
            double minX = box.minX;
            double minY = box.minY;
            double minZ = box.minZ;
            double maxX = box.maxX;
            double maxY = box.maxY;
            double maxZ = box.maxZ;

            if (direction == Direction.SOUTH) {
                rotatedShape = VoxelShapes.union(rotatedShape, VoxelShapes.cuboid(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ));
            } else if (direction == Direction.WEST) {
                rotatedShape = VoxelShapes.union(rotatedShape, VoxelShapes.cuboid(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX));
            } else if (direction == Direction.EAST) {
                rotatedShape = VoxelShapes.union(rotatedShape, VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX));
            } else {
                rotatedShape = VoxelShapes.union(rotatedShape, VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ));
            }
        }

        return rotatedShape;
    }


}
