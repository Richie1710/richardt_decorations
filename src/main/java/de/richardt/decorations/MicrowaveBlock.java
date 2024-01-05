package de.richardt.decorations;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


//public class MicrowaveBlock extends HorizontalFacingBlock implements BlockEntityProvider {
public class MicrowaveBlock extends BlockWithEntity  {
    public static final BooleanProperty POWER = BooleanProperty.of("power");
    private static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public MicrowaveBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(POWER, false));
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(world, type, Richardts_decorations.MICROWAVE_BLOCK_ENTITY);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MicrowaveBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(Properties.HORIZONTAL_FACING);
		builder.add(POWER);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(POWER)) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY();
            double z = (double) pos.getZ() + 0.5D;

            if (random.nextDouble() < 0.1D) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction_1 = state.get(FACING);
            Direction.Axis direction$Axis_1 = direction_1.getAxis();

            double double_5 = random.nextDouble() * 0.6D - 0.3D;
            double double_6 = direction$Axis_1 == Direction.Axis.X ? (double) direction_1.getOffsetX() * 0.52D : double_5;
            double double_7 = random.nextDouble() * 6.0D / 16.0D;
            double double_8 = direction$Axis_1 == Direction.Axis.Z ? (double) direction_1.getOffsetZ() * 0.52D : double_5;

            world.addParticle(ParticleTypes.SMOKE, x + double_6, y + double_7, z + double_8, 0.0D, 0.0D, 0.0D);
        }
    }
    @Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	// @Override
    // public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    //     player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 1, 1);
	// 	if(!world.isClient() && hand == Hand.MAIN_HAND) {
	// 		world.setBlockState(pos, state.cycle(POWER));
	// 	}
    //     return ActionResult.SUCCESS;
    // }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.playSound(SoundEvents.BLOCK_LEVER_CLICK, 1, 1);
            world.setBlockState(pos, state.cycle(POWER));
            this.openContainer(world, pos, player);
        }

        return ActionResult.SUCCESS;
    }

    private void openContainer(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);

        if (blockEntity instanceof MicrowaveBlockEntity) {
            playerEntity.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
            playerEntity.increaseStat(Stats.INTERACT_WITH_FURNACE, 1);
        }
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(Properties.HORIZONTAL_FACING);
        VoxelShape baseShape = createBaseShape();
        //VoxelShape mirroredShape = mirrorShape(baseShape);
        //VoxelShape mirroredShape = baseShape;

        return rotateShape(baseShape, direction);
    }

    private VoxelShape createBaseShape() {
        return VoxelShapes.union(
            VoxelShapes.cuboid(3 / 16f, 0 / 16f, 4 / 16f, 14 / 16f, 7 / 16f, 11 / 16f),
            VoxelShapes.cuboid(7 / 16f, 1 / 16f, 3 / 16f, 8 / 16f, 6 / 16f, 4 / 16f)
        );
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
    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> checkType(World world, BlockEntityType<T> givenType, BlockEntityType<? extends MicrowaveBlockEntity> expectedType) {
        return world.isClient ? null : checkType(givenType, expectedType, MicrowaveBlockEntity::tick);
    }
}
