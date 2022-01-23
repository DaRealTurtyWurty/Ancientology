package io.github.darealturtywurty.ancientology.core.data;

import com.matyrobbrt.lib.datagen.patchouli.PatchouliProvider;
import com.matyrobbrt.lib.datagen.patchouli.type.PatchouliBook;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import io.github.darealturtywurty.ancientology.Ancientology;

public class MythicalEncyclopediaGenerator extends PatchouliProvider {

    public MythicalEncyclopediaGenerator(DataGenerator generator) {
        super(generator, Ancientology.MODID, "en_us", "mythical_encyclopedia");
    }

    // TODO a book texture and a proper icon
    @PatchouliBookGen
    private static final PatchouliBook MYTHICAL_ENCYCLOPEDIA = new PatchouliBook("Mythical Encyclopedia",
            "An encyclopedia about mythical stuffs.").setTab(Ancientology.MODID)
                    .setBookTexture(new ResourceLocation("patchouli", "textures/gui/book_blue.png"))
                    .setCraftingTexture(new ResourceLocation("patchouli", "textures/gui/crafting.png"))
                    .setIndexIcon(Items.BEE_NEST).setModel(new ResourceLocation("patchouli", "book_blue"))
                    .addDefaultMacros();
}
