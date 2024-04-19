package net.ltxprogrammer.changed.entity.beast;


import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.HairStyle;
import net.ltxprogrammer.changed.entity.LatexType;
import net.ltxprogrammer.changed.entity.TransfurMode;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class LatexMedusaCat extends ChangedEntity {
    public LatexMedusaCat(EntityType<? extends LatexMedusaCat> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }


    @Override
    public Color3 getDripColor() {
        return Color3.getColor(this.random.nextInt(4) < 3 ? "#ffdb4f" : "#f0f0f0");
    }

    @Override
    public HairStyle getDefaultHairStyle() {
        return HairStyle.LONG_MESSY.get();
    }

    public @Nullable List<HairStyle> getValidHairStyles() {
        return HairStyle.Collection.FEMALE.getStyles();
    }

    @Override
    public Color3 getHairColor(int layer) {
        return Color3.getColor("#719b6f");
    }

    @Override
    public LatexType getLatexType() {
        return LatexType.NEUTRAL;
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.ABSORPTION;
    }
}