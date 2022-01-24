package io.github.darealturtywurty.ancientology.core.data;

import net.minecraft.data.DataGenerator;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import io.github.darealturtywurty.ancientology.core.util.MinecraftLocale;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {

    public LanguageGenerator(DataGenerator gen) {
        super(gen, Ancientology.MODID, MinecraftLocale.EN_US.getLocaleName());
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.ancientology", "Ancientology");
        ItemInit.ITEMS.getLangEntries(MinecraftLocale.EN_US).forEach(this::addItem);

        add("msg.ancientology.fruit_give_item", "You disobeyed God. You were not disappointed.");
        add("msg.ancientology.fruit_give_good_effect", "You disobeyed God. You were not disappointed.");
        add("msg.ancientology.fruit_nether", "God has banished you to Hell.");
        add("msg.ancientology.fruit_lightning", "You've invoked the wrath of God.");
        add("msg.ancientology.fruit_hunger", "God has stricken you with irony.");
        add("msg.ancientology.fruit_give_bad_effect", "Sickness has befallen upon you. Don't disobey God.");
        add("msg.ancientology.fruit_arrow_rain", "Heavenly arrows have come from above.");
        add("msg.ancientology.fruit_chance", "God has given you a second chance. Do not waste it.");
    }
}
