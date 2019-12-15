package com.buuz135.unitilities.generators;

import com.buuz135.unitilities.decorative.vertical.ResourceVerticalSlabBlock;
import com.hrznstudio.titanium.material.ResourceRegistry;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

import java.util.Collection;
import java.util.function.Consumer;

public class UnitilitiesRecipeGenerator extends RecipeProvider {

    public UnitilitiesRecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ResourceRegistry.getMaterials().stream().map(material -> material.getGenerated().values()).flatMap(Collection::stream).filter(entry -> entry instanceof ResourceVerticalSlabBlock)
                .map(entry -> (ResourceVerticalSlabBlock) entry).forEach(resourceVerticalSlabBlock -> TitaniumShapedRecipeBuilder.shapedRecipe(resourceVerticalSlabBlock, 6).patternLine(" X ").patternLine(" X ").patternLine(" X ").key('X', resourceVerticalSlabBlock.getParent()).build(consumer));

    }
}
