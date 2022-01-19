package io.github.darealturtywurty.ancientology.core.data;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(DataGenerator gen, String locale) {
        super(gen, Ancientology.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        // add("ancientology.blah", "Blah");
        // add(item, "Item Name");
    }
}
