package net.ltxprogrammer.changed.entity.beast;

import net.ltxprogrammer.changed.entity.*;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LatexCrocodile extends LatexEntity {
    public LatexCrocodile(EntityType<? extends LatexCrocodile> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public LatexType getLatexType() {
        return LatexType.NEUTRAL;
    }

    @Override
    public TransfurMode getTransfurMode() {
        return TransfurMode.REPLICATION;
    }

    @Override
    public HairStyle getDefaultHairStyle() {
        return HairStyle.BALD.get();
    }

    public @Nullable List<HairStyle> getValidHairStyles() {
        return HairStyle.Collection.MALE.getStyles();
    }

    @Override
    public Color3 getHairColor(int layer) {
        return Color3.getColor("#2b86a3");
    }

    @Override
    public Color3 getDripColor() {
        return Color3.getColor(this.random.nextInt(4) < 3 ? "#2b86a3" : "#29b297");
    }

    public Color3 getTransfurColor(TransfurCause cause) {
        return Color3.getColor("#2b86a3");
    }
}
