package net.ltxprogrammer.changed.item;

import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.TransfurCause;
import net.ltxprogrammer.changed.entity.TransfurContext;
import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.init.ChangedEntities;
import net.ltxprogrammer.changed.init.ChangedSounds;
import net.ltxprogrammer.changed.init.ChangedTabs;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class PinkTop implements ArmorMaterial {
    public static PinkTop INSTANCE = new PinkTop();

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return 5;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return ChangedSounds.EQUIP3;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of();
    }

    @Override
    public String getName() {
        return Changed.modResourceStr("pink");
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

    public static class Top extends ArmorItem implements LatexFusingItem, Shorts {

        public Top() {
            super(INSTANCE, EquipmentSlot.CHEST, new Properties().tab(ChangedTabs.TAB_CHANGED_ITEMS));
        }

        @Nullable
        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return Changed.modResourceStr("textures/models/pink_top.png");
        }

        @Override
        public TransfurVariant<?> getFusionVariant(TransfurVariant<?> currentVariant, LivingEntity livingEntity, ItemStack itemStack) {
            if (livingEntity.level.isClientSide)
                return currentVariant;

            if (currentVariant.is(TransfurVariant.LATEX_DEER))
                return TransfurVariant.LATEX_PINK_DEER;
            else if (currentVariant.is(TransfurVariant.LATEX_YUIN))
                return TransfurVariant.LATEX_PINK_YUIN_DRAGON;
            else {
                if (livingEntity.getRandom().nextBoolean()) {
                    var newEntity = currentVariant.getEntityType().create(livingEntity.level);
                    newEntity.moveTo(livingEntity.position());
                    livingEntity.level.addFreshEntity(newEntity);
                    return TransfurVariant.LATEX_PINK_WYVERN;
                } else {
                    var wyvern = ChangedEntities.LATEX_PINK_WYVERN.get().create(livingEntity.level);
                    wyvern.moveTo(livingEntity.position());
                    livingEntity.level.addFreshEntity(wyvern);
                    return currentVariant; // Return current to consume pants (Yummy)
                }
            }
        }

        @Override
        public void wearTick(LivingEntity entity, ItemStack itemStack) {
            var tag = itemStack.getOrCreateTag();
            var age = (tag.contains("age") ? tag.getInt("age") : 0) + 1;
            tag.putInt("age", age);
            if (age < 12000) // Half a minecraft day
                return;
            if (ProcessTransfur.progressTransfur(entity, 3.0f, TransfurVariant.LATEX_PINK_WYVERN, TransfurContext.hazard(TransfurCause.PINK_PANTS)))
                itemStack.shrink(1);
        }

        @Override
        public boolean customWearRenderer() {
            return false;
        }

        @Override
        public boolean allowedToKeepWearing(LivingEntity entity) {
            return true;
        }
    }
}
