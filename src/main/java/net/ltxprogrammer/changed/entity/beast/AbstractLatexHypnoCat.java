package net.ltxprogrammer.changed.entity.beast;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.GenderedEntity;
import net.ltxprogrammer.changed.entity.LatexType;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractLatexHypnoCat extends ChangedEntity implements GenderedEntity {
    public AbstractLatexHypnoCat(EntityType<? extends AbstractLatexHypnoCat> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    public boolean causeFallDamage(float p_148859_, float p_148860_, DamageSource p_148861_) { return false; }

    @Override
    public int getTicksRequiredToFreeze() { return 200; }

    @Override
    public LatexType getLatexType() {
        return LatexType.NEUTRAL;
    }

    public Color3 getDripColor() {
        return Color3.DARK;
    }
}
