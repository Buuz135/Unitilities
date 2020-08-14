package com.buuz135.unitilities;

import com.buuz135.unitilities.decorative.simple.PillarBlock;
import com.buuz135.unitilities.decorative.simple.SimpleBlock;
import com.buuz135.unitilities.decorative.vertical.ResourceVerticalSlabBlock;
import com.buuz135.unitilities.decorative.vertical.VerticalSlabResourceType;
import com.buuz135.unitilities.generators.UnitilitiesBlockstateGenerator;
import com.buuz135.unitilities.generators.UnitilitiesItemModelGenerator;
import com.buuz135.unitilities.generators.UnitilitiesLanguageGenerator;
import com.buuz135.unitilities.generators.UnitilitiesRecipeGenerator;
import com.hrznstudio.titanium.event.handler.EventManager;
import com.hrznstudio.titanium.material.ResourceRegistry;
import com.hrznstudio.titanium.module.Feature;
import com.hrznstudio.titanium.module.Module;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.TitaniumTab;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("unitilities")
public class Unitilities extends ModuleController {

    private static final Logger LOGGER = LogManager.getLogger();
    public static VerticalSlabResourceType VERTICAL_SLAB = new VerticalSlabResourceType();

    public static TitaniumTab TAB = new TitaniumTab("unitilities", () -> new ItemStack(Blocks.STONE));

    static {
        for (Block block : new Block[]{Blocks.OAK_PLANKS, Blocks.ACACIA_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.BIRCH_PLANKS, Blocks.DARK_OAK_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.STONE, Blocks.SMOOTH_STONE, Blocks.SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.COBBLESTONE, Blocks.BRICKS,
                Blocks.BRICKS, Blocks.STONE_BRICKS, Blocks.NETHER_BRICKS, Blocks.QUARTZ_BLOCK, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.PURPUR_BLOCK, Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.DARK_PRISMARINE, Blocks.POLISHED_GRANITE, Blocks.SMOOTH_RED_SANDSTONE,
                Blocks.MOSSY_STONE_BRICKS, Blocks.POLISHED_DIORITE, Blocks.MOSSY_COBBLESTONE, Blocks.END_STONE_BRICKS, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_QUARTZ, Blocks.GRANITE, Blocks.ANDESITE, Blocks.RED_NETHER_BRICKS, Blocks.POLISHED_ANDESITE, Blocks.DIORITE}) {
            addVerticalSlab(block);
        }
    }

    public Unitilities() {
        EventManager.forge(BlockEvent.BreakEvent.class).filter(event -> event.getState().getBlock() instanceof ResourceVerticalSlabBlock).
                process(event -> {
                    if (event.getState().get(ResourceVerticalSlabBlock.TYPE) == ResourceVerticalSlabBlock.VerticalHalf.DOUBLE) {
                        ResourceVerticalSlabBlock.VerticalHalf half = ResourceVerticalSlabBlock.getFacingHit(event.getState(), event.getWorld().getWorld(), event.getPos(), event.getPlayer());
                        ItemStack stack = ItemStack.EMPTY;
                        if (half == ResourceVerticalSlabBlock.VerticalHalf.DOUBLE) {
                            stack = new ItemStack(event.getState().getBlock(), 2);
                        } else {
                            stack = new ItemStack(event.getState().getBlock(), 1);
                            event.setCanceled(true);
                            event.getWorld().getWorld().setBlockState(event.getPos(), event.getState().with(ResourceVerticalSlabBlock.TYPE, half == ResourceVerticalSlabBlock.VerticalHalf.TOP ? ResourceVerticalSlabBlock.VerticalHalf.BOTTOM : ResourceVerticalSlabBlock.VerticalHalf.TOP));
                        }
                        if (!event.getPlayer().isCreative())
                            InventoryHelper.spawnItemStack(event.getWorld().getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), stack);
                    } else if (!event.getPlayer().isCreative()) {
                        InventoryHelper.spawnItemStack(event.getWorld().getWorld(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(event.getState().getBlock(), 1));
                    }
                }).subscribe();
    }

    @Override
    protected void initModules() {
        Module.Builder module = Module.builder("decoration");
        Feature.Builder feature = Feature.builder("basic");
        for (int i = 0; i < 32; i++) {
            feature.content(Block.class, new SimpleBlock("decorative_" + i, Block.Properties.from(Blocks.STONE)));
        }
        for (int i = 0; i < 16; i++) {
            feature.content(Block.class, new PillarBlock("decorative_pillar_" + i, Block.Properties.from(Blocks.STONE)));
        }
        module.feature(feature);
        addModule(module);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    public static void addVerticalSlab(Block block) {
        ResourceRegistry.getOrCreate(block.getRegistryName().getPath()).withProperties(VERTICAL_SLAB, new VerticalSlabResourceType.Properties(block)).add(VERTICAL_SLAB);
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        event.getGenerator().addProvider(new UnitilitiesBlockstateGenerator(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new UnitilitiesLanguageGenerator(event.getGenerator()));
        event.getGenerator().addProvider(new UnitilitiesItemModelGenerator(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new UnitilitiesRecipeGenerator(event.getGenerator()));
        super.addDataProvider(event);
    }
}
