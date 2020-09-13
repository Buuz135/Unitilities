package com.buuz135.unitilities.generators;

import com.buuz135.unitilities.decorative.vertical.ResourceVerticalSlabBlock;
import com.hrznstudio.titanium.material.ResourceRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Collection;

public class UnitilitiesBlockstateGenerator extends BlockStateProvider {

    private ExistingFileHelper helper;

    public UnitilitiesBlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, "unitilities", exFileHelper);
        this.helper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceRegistry.getMaterials().stream().map(material -> material.getGenerated().values()).flatMap(Collection::stream).filter(entry -> entry instanceof ResourceVerticalSlabBlock)
                .map(entry -> (ResourceVerticalSlabBlock) entry).forEach(resourceVerticalSlabBlock -> {
            getVariantBuilder(resourceVerticalSlabBlock)
                    .partialState().with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.DOUBLE).addModels(new ConfiguredModel(new ModelFile.UncheckedModelFile(resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.Z).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.TOP).addModels(new ConfiguredModel(new BlockModelBuilder(new ResourceLocation(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_north"), helper).parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_north"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.Z).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.BOTTOM).addModels(new ConfiguredModel(new BlockModelBuilder(new ResourceLocation(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_south"), helper).parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_south"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.X).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.BOTTOM).addModels(new ConfiguredModel(new BlockModelBuilder(new ResourceLocation(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_east"), helper).parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_east"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.AXIS, ResourceVerticalSlabBlock.Axis.X).with(ResourceVerticalSlabBlock.TYPE, ResourceVerticalSlabBlock.VerticalHalf.TOP).addModels(new ConfiguredModel(new BlockModelBuilder(new ResourceLocation(resourceVerticalSlabBlock.getRegistryName().getPath().toString() + "_west"), helper).parent(new ModelFile.UncheckedModelFile("unitilities:block/vertical_slab_west"))
                    .texture("particle", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("top", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())
                    .texture("bottom", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
            ;
        });
    }
}
