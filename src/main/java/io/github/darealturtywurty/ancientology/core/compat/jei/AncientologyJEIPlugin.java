package io.github.darealturtywurty.ancientology.core.compat.jei;

import net.minecraft.resources.ResourceLocation;

import io.github.darealturtywurty.ancientology.Ancientology;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;

// TODO jumprasher JEI category
@JeiPlugin
public class AncientologyJEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = Ancientology.rl("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

}
