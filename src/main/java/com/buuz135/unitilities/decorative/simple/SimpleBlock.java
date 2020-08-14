package com.buuz135.unitilities.decorative.simple;

import com.buuz135.unitilities.Unitilities;
import com.hrznstudio.titanium.block.BasicBlock;

public class SimpleBlock extends BasicBlock {

    public SimpleBlock(String name, Properties properties) {
        super(properties);
        setItemGroup(Unitilities.TAB);
        setRegistryName(name);
    }
}
