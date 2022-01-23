package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import io.github.darealturtywurty.ancientology.mixins.accessors.LanguageProviderAccessor;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(DataGenerator gen) {
        super(gen, Ancientology.MODID, MinecraftLocale.EN_US.getLocaleName());
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.ancientology", "Ancientology");
        ItemInit.ITEMS.getLangEntries(MinecraftLocale.EN_US).forEach(this::addItem);
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
