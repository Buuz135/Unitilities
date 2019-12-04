package com.buuz135.unitilities.decorative.vertical;

import com.hrznstudio.titanium.api.material.IResourceHolder;
import com.hrznstudio.titanium.api.material.IResourceType;
import com.hrznstudio.titanium.block.BlockBase;
import com.hrznstudio.titanium.material.ResourceMaterial;
import com.hrznstudio.titanium.material.ResourceTypeProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ResourceVerticalSlabBlock extends BlockBase implements IResourceHolder, IWaterLoggable {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static VoxelShape NORTH_SHAPE = VoxelShapes.create(0, 0, 0, 1, 1, 0.5);
    public static VoxelShape SOUTH_SHAPE = VoxelShapes.create(0, 0, 0.5, 1, 1, 1);
    public static VoxelShape EAST_SHAPE = VoxelShapes.create(0.5, 0, 0, 1, 1, 1);
    public static VoxelShape WEST_SHAPE = VoxelShapes.create(0, 0, 0, 0.5, 1, 1);

    private final ResourceMaterial resourceMaterial;
    private final IResourceType resourceType;
    private final Block parent;

    public ResourceVerticalSlabBlock(ResourceMaterial resourceMaterial, IResourceType resourceType, ResourceTypeProperties<Properties> properties) {
        super(resourceMaterial.getMaterialType() + "_" + resourceType.getName(), properties == null ? (Properties) ((ResourceTypeProperties) ResourceTypeProperties.DEFAULTS.get(Block.class)).get() : (Properties) properties.get());
        this.resourceMaterial = resourceMaterial;
        this.resourceType = resourceType;
        this.parent = properties instanceof VerticalSlabResourceType.Properties ? ((VerticalSlabResourceType.Properties) properties).getParent() : Blocks.STONE;
        this.setDefaultState(this.getDefaultState().with(NORTH, true).with(SOUTH, false).with(EAST, false).with(WEST, false).with(WATERLOGGED, false));
    }

    private static BooleanProperty getFirstEnabled(BlockState state) {
        for (BooleanProperty property : new BooleanProperty[]{NORTH, SOUTH, EAST, WEST}) {
            if (state.get(property)) return property;
        }
        return null;
    }

    private static BooleanProperty getPropertyFromDirection(Direction direction) {
        if (direction == Direction.NORTH) return NORTH;
        if (direction == Direction.SOUTH) return SOUTH;
        if (direction == Direction.EAST) return EAST;
        return WEST;
    }

    private static BooleanProperty getOpposite(BooleanProperty property) {
        if (property == NORTH) return SOUTH;
        if (property == SOUTH) return NORTH;
        if (property == WEST) return EAST;
        return WEST;
    }

    @Override
    public ResourceMaterial getMaterial() {
        return resourceMaterial;
    }

    @Override
    public IResourceType getType() {
        return resourceType;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        BlockState blockstate = context.getWorld().getBlockState(blockpos);
        if (blockstate.getBlock() == this) {
            for (BooleanProperty property : new BooleanProperty[]{NORTH, SOUTH, EAST, WEST}) {
                if (blockstate.get(property)) {
                    return blockstate.with(getOpposite(property), true);
                }
            }
        }
        return this.getDefaultState().with(NORTH, false).with(getPropertyFromDirection(context.getPlacementHorizontalFacing()), true);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack itemstack = useContext.getItem();
        Direction direction = useContext.getFace();
        BooleanProperty property = getFirstEnabled(state);
        BooleanProperty clicked = getPropertyFromDirection(direction);
        if (property != null && areCompatible(clicked, property) && !state.get(clicked) && itemstack.getItem() == this.asItem()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<VoxelShape> getBoundingBoxes(BlockState state, IBlockReader source, BlockPos pos) {
        List<VoxelShape> shapes = new ArrayList<>();
        if (state.get(NORTH)) shapes.add(NORTH_SHAPE);
        if (state.get(SOUTH)) shapes.add(SOUTH_SHAPE);
        if (state.get(EAST)) shapes.add(EAST_SHAPE);
        if (state.get(WEST)) shapes.add(WEST_SHAPE);
        shapes.add(VoxelShapes.create(0, 0, 0, 0, 0, 0));
        return shapes;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shapes = VoxelShapes.empty();
        if (state.get(NORTH)) shapes = VoxelShapes.combine(shapes, NORTH_SHAPE, IBooleanFunction.OR);
        if (state.get(SOUTH)) shapes = VoxelShapes.combine(shapes, SOUTH_SHAPE, IBooleanFunction.OR);
        if (state.get(EAST)) shapes = VoxelShapes.combine(shapes, EAST_SHAPE, IBooleanFunction.OR);
        if (state.get(WEST)) shapes = VoxelShapes.combine(shapes, WEST_SHAPE, IBooleanFunction.OR);
        return shapes;
    }

    @Override
    public boolean hasCustomBoxes(BlockState state, IBlockReader source, BlockPos pos) {
        return true;
    }

    @Override
    public boolean hasIndividualRenderVoxelShape() {
        return true;
    }

    private boolean areCompatible(BooleanProperty first, BooleanProperty second) {
        return getOpposite(first) == second;
    }

    public Block getParent() {
        return parent;
    }
}
