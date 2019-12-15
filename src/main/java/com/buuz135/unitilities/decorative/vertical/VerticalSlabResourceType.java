package com.buuz135.unitilities.decorative.vertical;

import com.hrznstudio.titanium.api.IFactory;
import com.hrznstudio.titanium.api.material.IResourceType;
import com.hrznstudio.titanium.material.ResourceMaterial;
import com.hrznstudio.titanium.material.ResourceTypeProperties;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class VerticalSlabResourceType implements IResourceType {
    @Override
    public String getTag() {
        return "vertical_slab";
    }

    @Override
    public IFactory<ForgeRegistryEntry> getInstanceFactory(ResourceMaterial resourceMaterial, @Nullable ResourceTypeProperties resourceTypeProperties) {
        return () -> new ResourceVerticalSlabBlock(resourceMaterial, this, resourceTypeProperties);
    }

    @Override
    public String getName() {
        return "vertical_slab";
    }

    public static class Properties extends ResourceTypeProperties<Block.Properties> {

        private final Block parent;

        public Properties(Block parent) {
            super(Block.Properties.from(parent));
            this.parent = parent;
        }

        public Block getParent() {
            return parent;
        }
    }
}
