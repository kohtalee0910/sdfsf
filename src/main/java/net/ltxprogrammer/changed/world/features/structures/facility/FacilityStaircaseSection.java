package net.ltxprogrammer.changed.world.features.structures.facility;

import net.ltxprogrammer.changed.world.features.structures.FacilityPieces;
import net.ltxprogrammer.changed.world.features.structures.LootTables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;

public class FacilityStaircaseSection extends FacilitySinglePiece {
    private static final WeightedRandomList<WeightedEntry.Wrapper<PieceType>> VALID_NEIGHBORS_MINIMUM = WeightedRandomList.create(
            WeightedEntry.wrap(PieceType.STAIRCASE_SECTION, 1));
    private static final WeightedRandomList<WeightedEntry.Wrapper<PieceType>> VALID_NEIGHBORS = WeightedRandomList.create(
            WeightedEntry.wrap(PieceType.STAIRCASE_SECTION, 5),
            WeightedEntry.wrap(PieceType.STAIRCASE_END, 1));
    private static final WeightedRandomList<WeightedEntry.Wrapper<PieceType>> VALID_NEIGHBORS_MAXIMUM = WeightedRandomList.create(
            WeightedEntry.wrap(PieceType.STAIRCASE_END, 1));

    public FacilityStaircaseSection(Zone zone, ResourceLocation templateName) {
        super(PieceType.STAIRCASE_SECTION, zone, templateName, LootTables.LOW_TIER_LAB);
    }

    @Override
    public WeightedRandomList<WeightedEntry.Wrapper<PieceType>> getValidNeighbors(FacilityGenerationStack stack) {
        int sections = stack.sequentialMatch(FacilityPieces.STAIRCASE_SECTIONS::contains);

        if (sections < 6)
            return VALID_NEIGHBORS_MINIMUM;
        if (sections > 15)
            return VALID_NEIGHBORS_MAXIMUM;

        return VALID_NEIGHBORS;
    }
}