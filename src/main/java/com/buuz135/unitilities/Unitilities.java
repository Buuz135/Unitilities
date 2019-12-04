package com.buuz135.unitilities;

import com.buuz135.unitilities.decorative.vertical.VerticalSlabResourceType;
import com.buuz135.unitilities.generators.UnitilitiesBlockstateGenerator;
import com.hrznstudio.titanium.material.ResourceRegistry;
import com.hrznstudio.titanium.module.ModuleController;
import net.minecraft.block.Blocks;
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

    static {
        ResourceRegistry.getOrCreate("oak").withProperties(VERTICAL_SLAB, new VerticalSlabResourceType.Properties(Blocks.OAK_PLANKS)).add(VERTICAL_SLAB);
    }

    public Unitilities() {

    }

    @Override
    protected void initModules() {

    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        event.getGenerator().addProvider(new UnitilitiesBlockstateGenerator(event.getGenerator(), event.getExistingFileHelper()));
        super.addDataProvider(event);
    }
}
