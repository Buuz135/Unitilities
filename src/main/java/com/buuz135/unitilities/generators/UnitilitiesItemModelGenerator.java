package com.buuz135.unitilities.generators;

import com.buuz135.unitilities.decorative.vertical.ResourceVerticalSlabBlock;
import com.hrznstudio.titanium.material.ResourceRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.Collection;

public class UnitilitiesItemModelGenerator extends ItemModelProvider {

    public UnitilitiesItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, "unitilities", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ResourceRegistry.getMaterials().stream().map(material -> material.getGenerated().values()).flatMap(Collection::stream).filter(entry -> entry instanceof ResourceVerticalSlabBlock)
                .map(entry -> (ResourceVerticalSlabBlock) entry).forEach(resourceVerticalSlabBlock -> {
            getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(modLoc("block/") + resourceVerticalSlabBlock.getRegistryName().getPath() + "_south"));
        });
    }

    @Override
    public String getName() {
        return "Item Models";
    }
}
