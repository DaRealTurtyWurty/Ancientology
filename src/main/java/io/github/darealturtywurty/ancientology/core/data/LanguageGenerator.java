package io.github.darealturtywurty.ancientology.core.data;

import net.minecraft.data.DataGenerator;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import io.github.darealturtywurty.ancientology.mixins.accessors.LanguageProviderAccessor;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(DataGenerator gen, String locale) {
        super(gen, Ancientology.MODID, locale);
    }

    @Override
    protected void addTranslations() {
    }
    
    public static class BuilderAddedKeys extends LanguageProvider {

        public BuilderAddedKeys(DataGenerator gen, String locale) {
            super(gen, Ancientology.MODID, locale);
        }

        @Override
        protected void addTranslations() {
            final var locale = ((LanguageProviderAccessor) this).getLocale();
            ItemInit.ITEMS.getLangEntries(MinecraftLocale.byName(locale)).forEach(this::addItem);
        }
        
    }
}
