package io.github.darealturtywurty.ancientology.common.items;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SchimitarItem extends Item {
    public SchimitarItem(Properties pProperties) {
        super(pProperties);
    }
    
    @Override
    public void appendHoverText(ItemStack pstack, @Nullable Level plevel,
                                List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.ancientology.schimitar"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.ancientology.schimitar.shift"));
        }
    }
}