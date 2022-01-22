package io.github.darealturtywurty.ancientology.mixins.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraftforge.common.data.LanguageProvider;

@Mixin(LanguageProvider.class)
public interface LanguageProviderAccessor {

    @Accessor
    String getLocale();

}
