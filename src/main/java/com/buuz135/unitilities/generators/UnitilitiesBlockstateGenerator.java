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
        super(gen, "titanium", exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceRegistry.getMaterials().stream().map(material -> material.getGenerated().values()).flatMap(Collection::stream).filter(entry -> entry instanceof ResourceVerticalSlabBlock)
                .map(entry -> (ResourceVerticalSlabBlock) entry).forEach(resourceVerticalSlabBlock -> {
            getVariantBuilder(resourceVerticalSlabBlock)
                    .forAllStatesExcept(state -> {
                        ConfiguredModel.Builder builder = ConfiguredModel.builder();

                        return builder.build();
                    }, ResourceVerticalSlabBlock.WATERLOGGED)
                    .partialState().with(ResourceVerticalSlabBlock.NORTH, true)
                    .addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("titanium:block/vertical_slab_north"))
                            .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.SOUTH, true)
                    .addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("titanium:block/vertical_slab_south"))
                            .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.EAST, true)
                    .addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("titanium:block/vertical_slab_east"))
                            .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
                    .partialState().with(ResourceVerticalSlabBlock.WEST, true)
                    .addModels(new ConfiguredModel(getBuilder(resourceVerticalSlabBlock.getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile("titanium:block/vertical_slab_west"))
                            .texture("side", resourceVerticalSlabBlock.getParent().getRegistryName().getNamespace() + ":block/" + resourceVerticalSlabBlock.getParent().getRegistryName().getPath())))
            ;
        });
    }
}
