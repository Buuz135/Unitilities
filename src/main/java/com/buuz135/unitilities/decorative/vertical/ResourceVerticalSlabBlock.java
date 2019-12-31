package com.buuz135.unitilities.decorative.vertical;

import com.hrznstudio.titanium.api.material.IResourceHolder;
import com.hrznstudio.titanium.api.material.IResourceType;
import com.hrznstudio.titanium.block.BlockBase;
import com.hrznstudio.titanium.material.ResourceMaterial;
import com.hrznstudio.titanium.material.ResourceTypeProperties;
import com.hrznstudio.titanium.util.RayTraceUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ResourceVerticalSlabBlock extends BlockBase implements IResourceHolder, IWaterLoggable {

    public static final EnumProperty<VerticalHalf> TYPE = EnumProperty.create("type", VerticalHalf.class);
    public static final EnumProperty<Axis> AXIS = EnumProperty.create("axis", Axis.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static VoxelShape NORTH_SHAPE = VoxelShapes.create(0, 0, 0, 1, 1, 0.5);
    public static VoxelShape SOUTH_SHAPE = VoxelShapes.create(0, 0, 0.5, 1, 1, 1);
    public static VoxelShape EAST_SHAPE = VoxelShapes.create(0.5, 0, 0, 1, 1, 1);
    public static VoxelShape WEST_SHAPE = VoxelShapes.create(0, 0, 0, 0.5, 1, 1);

    private final ResourceMaterial resourceMaterial;
    private final IResourceType resourceType;
    private final Block parent;

    public ResourceVerticalSlabBlock(ResourceMaterial resourceMaterial, IResourceType resourceType, ResourceTypeProperties<Properties> properties) {
        super("unitilities:" + resourceMaterial.getMaterialType() + "_" + resourceType.getName(), properties == null ? (Properties) ((ResourceTypeProperties) ResourceTypeProperties.DEFAULTS.get(Block.class)).get() : (Properties) properties.get());
        this.resourceMaterial = resourceMaterial;
        this.resourceType = resourceType;
        this.parent = properties instanceof VerticalSlabResourceType.Properties ? ((VerticalSlabResourceType.Properties) properties).getParent() : Blocks.STONE;
        this.setDefaultState(this.getDefaultState().with(TYPE, VerticalHalf.BOTTOM).with(AXIS, Axis.X).with(WATERLOGGED, false));
    }

    @Override
    public ResourceMaterial getMaterial() {
        return resourceMaterial;
    }

    @Override
    public IResourceType getType() {
        return resourceType;
    }

    public static VerticalHalf getFacingHit(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (player.isCrouching()) return VerticalHalf.DOUBLE;
        RayTraceResult result = RayTraceUtils.rayTraceSimple(worldIn, player, 32, 0);
        if (result instanceof BlockRayTraceResult) {
            VoxelShape hit = RayTraceUtils.rayTraceVoxelShape((BlockRayTraceResult) result, worldIn, player, 32, 0);
            if (hit != null) {
                if (VoxelShapes.compare(NORTH_SHAPE, hit, IBooleanFunction.AND)) return VerticalHalf.TOP;
                if (VoxelShapes.compare(SOUTH_SHAPE, hit, IBooleanFunction.AND)) return VerticalHalf.BOTTOM;
                if (VoxelShapes.compare(EAST_SHAPE, hit, IBooleanFunction.AND)) return VerticalHalf.BOTTOM;
                if (VoxelShapes.compare(WEST_SHAPE, hit, IBooleanFunction.AND)) return VerticalHalf.TOP;
            }
        }
        return VerticalHalf.DOUBLE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        BlockState blockstate = context.getWorld().getBlockState(blockpos);
        IFluidState ifluidstate = context.getWorld().getFluidState(blockpos);
        if (blockstate.getBlock() == this && blockstate.get(TYPE) != VerticalHalf.DOUBLE) {
            return blockstate.with(TYPE, VerticalHalf.DOUBLE).with(WATERLOGGED, false);
        }
        if (context.getFace().getAxis().isHorizontal()) {
            Direction[] adirection = context.getNearestLookingDirections();
            if (adirection.length > 0) {
                return this.getDefaultState().with(TYPE, adirection[0].getAxisDirection() == Direction.AxisDirection.POSITIVE ? VerticalHalf.BOTTOM : VerticalHalf.TOP).with(AXIS, adirection[0].getAxis() == Direction.Axis.X ? Axis.X : Axis.Z).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
            }
        }
        return this.getDefaultState().with(TYPE, context.getPlacementHorizontalFacing().getAxisDirection() == Direction.AxisDirection.POSITIVE ? VerticalHalf.BOTTOM : VerticalHalf.TOP).with(AXIS, context.getPlacementHorizontalFacing().getAxis() == Direction.Axis.X ? Axis.X : Axis.Z).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItem();
        if (state.get(TYPE) != VerticalHalf.DOUBLE && itemstack.getItem() == this.asItem()) {
            if (useContext.replacingClickedOnBlock()) {
                Direction direction = useContext.getFace();
                if (state.get(AXIS) == Axis.X) {
                    if (state.get(TYPE) == VerticalHalf.BOTTOM && direction == Direction.WEST) {
                        return true;
                    } else if (state.get(TYPE) == VerticalHalf.TOP && direction == Direction.EAST) {
                        return true;
                    }
                } else {
                    if (state.get(TYPE) == VerticalHalf.BOTTOM && direction == Direction.NORTH) {
                        return true;
                    } else if (state.get(TYPE) == VerticalHalf.TOP && direction == Direction.SOUTH) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<VoxelShape> getBoundingBoxes(BlockState state, IBlockReader source, BlockPos pos) {
        List<VoxelShape> shapes = new ArrayList<>();
        if (state.get(AXIS) == Axis.X) {
            if (state.get(TYPE) == VerticalHalf.BOTTOM || state.get(TYPE) == VerticalHalf.DOUBLE) {
                shapes.add(EAST_SHAPE);
            }
            if (state.get(TYPE) == VerticalHalf.TOP || state.get(TYPE) == VerticalHalf.DOUBLE) {
                shapes.add(WEST_SHAPE);
            }
        } else {
            if (state.get(TYPE) == VerticalHalf.BOTTOM || state.get(TYPE) == VerticalHalf.DOUBLE) {
                shapes.add(SOUTH_SHAPE);
            }
            if (state.get(TYPE) == VerticalHalf.TOP || state.get(TYPE) == VerticalHalf.DOUBLE) {
                shapes.add(NORTH_SHAPE);
            }
        }
        return shapes;
    }

    @Override
    public boolean hasCustomBoxes(BlockState state, IBlockReader source, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shapes = VoxelShapes.empty();
        for (VoxelShape boundingBox : getBoundingBoxes(state, worldIn, pos)) {
            shapes = VoxelShapes.combine(shapes, boundingBox, IBooleanFunction.OR);
        }
        return shapes;
    }

    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidStateIn) {
        return state.get(TYPE) != VerticalHalf.DOUBLE ? IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn) : false;
    }

    @Override
    public boolean hasIndividualRenderVoxelShape() {
        return true;
    }

    public Block getParent() {
        return parent;
    }

    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.get(TYPE) != VerticalHalf.DOUBLE ? IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn) : false;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            InventoryHelper.dropItems(worldIn, pos, getDynamicDrops(state, worldIn, pos, newState, isMoving));
            worldIn.updateComparatorOutputLevel(pos, this);
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    public enum VerticalHalf implements IStringSerializable {
        TOP, BOTTOM, DOUBLE;

        @Override
        public String getName() {
            return this.toString();
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public enum Axis implements IStringSerializable {
        X, Z;

        @Override
        public String getName() {
            return this.toString().toLowerCase();
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
}
