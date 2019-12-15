package com.buuz135.unitilities.generators;

import com.buuz135.unitilities.decorative.vertical.ResourceVerticalSlabBlock;
import com.hrznstudio.titanium.material.ResourceRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.Collection;

public class UnitilitiesBlockstateGenerator extends BlockStateProvider {

    public UnitilitiesBlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, "unitilities", exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceRegistry.getMaterials().stream().map(material -> material.getGenerated().values()).flatMap(Collection::stream).filter(entry -> entry instanceof ResourceVerticalSlabBlock)
                .map(entry -> (ResourceVerticalSlabBlock) entry).forEach(resourceVerticalSlabBlock -> {
            getVariantBuilder(resourceVerticalSlabBlock)
                    .partialState().with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.DOUBLE).addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.Z).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.TOP).addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_north").parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_north"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.Z).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.BOTTOM).addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_south").parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_south"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.X).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.BOTTOM).addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_east").parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_east"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.X).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.TOP).addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_west").parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_west"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
            ;
        });
    }
}
