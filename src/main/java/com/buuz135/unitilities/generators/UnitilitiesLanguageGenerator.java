package com.buuz135.unitilities.generators;

import com.buuz135.unitilities.decorative.vertical.ResourceVerticalSlabBlock;
import com.hrznstudio.titanium.material.ResourceRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Collection;

public class UnitilitiesLanguageGenerator extends LanguageProvider {

    public UnitilitiesLanguageGenerator(DataGenerator gen) {
        super(gen, "unitilities", "en_us");
    }

    @Override
    protected void addTranslations() {
        ResourceRegistry.getMaterials().stream().map(material -> material.getGenerated().values()).flatMap(Collection::stream).filter(entry -> entry instanceof ResourceVerticalSlabBlock)
                .map(entry -> (ResourceVerticalSlabBlock) entry).forEach(resourceVerticalSlabBlock -> {
            addBlock(() -> resourceVerticalSlabBlock, WordUtils.capitalize(resourceVerticalSlabBlock.getMaterial().getMaterialType().replace("_", " ")) + " Vertical Slab");
        });
    }
}
