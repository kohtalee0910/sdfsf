package net.ltxprogrammer.changed.init;

import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.blockentity.LatexContainerRenderer;
import net.ltxprogrammer.changed.client.renderer.blockentity.PillowRenderer;
import net.ltxprogrammer.changed.client.renderer.layers.CustomEyesLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TransfurCapeLayer;
import net.ltxprogrammer.changed.client.renderer.model.*;
import net.ltxprogrammer.changed.client.renderer.model.armor.*;
import net.ltxprogrammer.changed.client.renderer.model.hair.HairRemodel;
import net.ltxprogrammer.changed.client.tfanimations.TransfurHelper;
import net.ltxprogrammer.changed.data.DeferredModelLayerLocation;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChangedLayerDefinitions {
    public static final ModelLayerLocation LATEX_COAT = new ModelLayerLocation(Changed.modResource("player"), "latex_coat");
    public static final ModelLayerLocation LATEX_COAT_SLIM = new ModelLayerLocation(Changed.modResource("player_slim"), "latex_coat");

    private static void registerLayerDefinition(@Nullable DeferredModelLayerLocation location, Supplier<LayerDefinition> supplier) {
        if (location != null)
            ForgeHooksClient.registerLayerDefinition(location.get(), supplier);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(FMLCommonSetupEvent event) {
        final boolean useNewModels = Changed.config.client.useNewModels.get();

        ForgeHooksClient.registerLayerDefinition(LATEX_COAT, () ->
                LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.01F), false), 64, 64));
        ForgeHooksClient.registerLayerDefinition(LATEX_COAT_SLIM, () ->
                LayerDefinition.create(PlayerModel.createMesh(new CubeDeformation(0.01F), false), 64, 64));
        ForgeHooksClient.registerLayerDefinition(CustomEyesLayer.HEAD, CustomEyesLayer::createHead);
        ForgeHooksClient.registerLayerDefinition(DarkLatexMaskModel.LAYER_LOCATION, DarkLatexMaskModel::createMask);
        ForgeHooksClient.registerLayerDefinition(TransfurCapeLayer.LAYER_LOCATION, TransfurCapeLayer::createCape);
        ForgeHooksClient.registerLayerDefinition(TransfurCapeLayer.LAYER_LOCATION_SHORT, TransfurCapeLayer::createShortCape);
        ForgeHooksClient.registerLayerDefinition(GasMaskModel.LAYER_LOCATION, GasMaskModel::createMask);
        ForgeHooksClient.registerLayerDefinition(GasMaskModel.LAYER_LOCATION_SNOUTED, GasMaskModel::createMaskSnouted);
        ForgeHooksClient.registerLayerDefinition(GasMaskModel.LAYER_LOCATION_LARGE_SNOUTED, GasMaskModel::createMaskLargeSnouted);
        ForgeHooksClient.registerLayerDefinition(DuctPlayerModel.LAYER_LOCATION, DuctPlayerModel::createRoot);
        ForgeHooksClient.registerLayerDefinition(TaurChestPackModel.LAYER_LOCATION, TaurChestPackModel::createPack);

        ForgeHooksClient.registerLayerDefinition(TransfurHelper.TRANSFUR_HELPER, TransfurHelper::createBodyLayer);

        ForgeHooksClient.registerLayerDefinition(BehemothHeadModel.LAYER_LOCATION, BehemothHeadModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(BehemothHandLeftModel.LAYER_LOCATION, BehemothHandLeftModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(BehemothHandRightModel.LAYER_LOCATION, BehemothHandRightModel::createBodyLayer);

        ForgeHooksClient.registerLayerDefinition(AerosolLatexWolfModel.LAYER_LOCATION, AerosolLatexWolfModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(DarkLatexDragonModel.LAYER_LOCATION, DarkLatexDragonModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(DarkLatexWolfFemaleModel.LAYER_LOCATION, DarkLatexWolfFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(DarkLatexWolfMaleModel.LAYER_LOCATION, DarkLatexWolfMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(DarkLatexWolfPartialModel.LAYER_LOCATION_HUMAN, () -> DarkLatexWolfPartialModel.createHumanLayer(false));
        ForgeHooksClient.registerLayerDefinition(DarkLatexWolfPartialModel.LAYER_LOCATION_LATEX, () -> DarkLatexWolfPartialModel.createLatexLayer(false));
        ForgeHooksClient.registerLayerDefinition(DarkLatexWolfPartialModel.LAYER_LOCATION_HUMAN_SLIM, () -> DarkLatexWolfPartialModel.createHumanLayer(true));
        ForgeHooksClient.registerLayerDefinition(DarkLatexWolfPartialModel.LAYER_LOCATION_LATEX_SLIM, () -> DarkLatexWolfPartialModel.createLatexLayer(true));
        ForgeHooksClient.registerLayerDefinition(DarkLatexPupModel.LAYER_LOCATION, DarkLatexPupModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(DarkLatexYufengModel.LAYER_LOCATION, DarkLatexYufengModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(PhageLatexWolfFemaleModel.LAYER_LOCATION, PhageLatexWolfFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(PhageLatexWolfMaleModel.LAYER_LOCATION, PhageLatexWolfMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(HeadlessKnightModel.LAYER_LOCATION, HeadlessKnightModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexAlienModel.LAYER_LOCATION, LatexAlienModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexBeeModel.LAYER_LOCATION, LatexBeeModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexBeifengModel.LAYER_LOCATION, LatexBeifengModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexBenignWolfModel.LAYER_LOCATION, LatexBenignWolfModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexBlueDragonModel.LAYER_LOCATION, LatexBlueDragonModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexBlueWolfModel.LAYER_LOCATION, LatexBlueWolfModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexCrocodileModel.LAYER_LOCATION, LatexCrocodileModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexCrystalWolfModel.LAYER_LOCATION, LatexCrystalWolfModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexCrystalWolfHornedModel.LAYER_LOCATION, LatexCrystalWolfHornedModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexDeerModel.LAYER_LOCATION, LatexDeerModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexFennecFoxModel.LAYER_LOCATION, LatexFennecFoxModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(GreenLizardModel.LAYER_LOCATION, GreenLizardModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexHumanModel.LAYER_LOCATION, () -> LatexHumanModel.createBodyLayer(false));
        ForgeHooksClient.registerLayerDefinition(LatexHumanModel.LAYER_LOCATION_SLIM, () -> LatexHumanModel.createBodyLayer(true));
        ForgeHooksClient.registerLayerDefinition(LatexHypnoCatModel.LAYER_LOCATION, LatexHypnoCatModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexKeonWolfModel.LAYER_LOCATION, LatexKeonWolfModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexLeafModel.LAYER_LOCATION, LatexLeafModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMantaRayFemaleModel.LAYER_LOCATION,
                useNewModels ? LatexMantaRayFemaleModel.Remodel::createBodyLayer : LatexMantaRayFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMantaRayMaleModel.LAYER_LOCATION,
                useNewModels ? LatexMantaRayMaleModel.Remodel::createBodyLayer : LatexMantaRayMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMedusaCatModel.LAYER_LOCATION, LatexMedusaCatModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMermaidSharkModel.LAYER_LOCATION, LatexMermaidSharkModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMimicPlantModel.LAYER_LOCATION, LatexMimicPlantModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMingCatModel.LAYER_LOCATION, LatexMingCatModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexMothModel.LAYER_LOCATION, LatexMothModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexOrcaModel.LAYER_LOCATION, LatexOrcaModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexOtterModel.LAYER_LOCATION, LatexOtterModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexPinkDeerModel.LAYER_LOCATION, LatexPinkDeerModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexPinkWyvernModel.LAYER_LOCATION, LatexPinkWyvernModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexPinkYuinDragonModel.LAYER_LOCATION, LatexPinkYuinDragonModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexPurpleFoxModel.LAYER_LOCATION, LatexPurpleFoxModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexRaccoonModel.LAYER_LOCATION, LatexRaccoonModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexRedDragonModel.LAYER_LOCATION, LatexRedDragonModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexRedPandaModel.LAYER_LOCATION, LatexRedPandaModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSharkModel.LAYER_LOCATION, LatexSharkModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSharkFemaleModel.LAYER_LOCATION,
                /*useNewModels ? LatexSharkFemaleModel.Remodel::createBodyLayer :*/ LatexSharkFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSharkMaleModel.LAYER_LOCATION,
                /*useNewModels ? LatexSharkMaleModel.Remodel::createBodyLayer :*/ LatexSharkMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSirenModel.LAYER_LOCATION, LatexSirenModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSnakeModel.LAYER_LOCATION, LatexSnakeModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSniperDogModel.LAYER_LOCATION, LatexSniperDogModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSnowLeopardFemaleModel.LAYER_LOCATION, LatexSnowLeopardFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSnowLeopardMaleModel.LAYER_LOCATION, LatexSnowLeopardMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSquidDogFemaleModel.LAYER_LOCATION, LatexSquidDogFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSquidDogMaleModel.LAYER_LOCATION, LatexSquidDogMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexSquirrelModel.LAYER_LOCATION, LatexSquirrelModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexStigerModel.LAYER_LOCATION, LatexStigerModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexTigerSharkModel.LAYER_LOCATION, LatexTigerSharkModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexTrafficConeDragonModel.LAYER_LOCATION, LatexTrafficConeDragonModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexTranslucentLizardModel.LAYER_LOCATION, LatexTranslucentLizardModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexWatermelonCatModel.LAYER_LOCATION, LatexWatermelonCatModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexWhiteTigerModel.LAYER_LOCATION, LatexWhiteTigerModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LatexYuinModel.LAYER_LOCATION, LatexYuinModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LightLatexCentaurModel.LAYER_LOCATION, LightLatexCentaurModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LightLatexKnightModel.LAYER_LOCATION, LightLatexKnightModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LightLatexKnightFusionModel.LAYER_LOCATION, LightLatexKnightFusionModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LightLatexWolfFemaleModel.LAYER_LOCATION, LightLatexWolfFemaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(LightLatexWolfMaleModel.LAYER_LOCATION, LightLatexWolfMaleModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(MilkPuddingModel.LAYER_LOCATION, MilkPuddingModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(SharkModel.LAYER_LOCATION, SharkModel::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(WhiteLatexWolfModel.LAYER_LOCATION, WhiteLatexWolfModel::createBodyLayer);

        ForgeHooksClient.registerLayerDefinition(ArmorNoneModel.INNER_ARMOR, () -> ArmorNoneModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorNoneModel.OUTER_ARMOR, () -> ArmorNoneModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorNoTailModel.INNER_ARMOR, () -> ArmorNoTailModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorNoTailModel.OUTER_ARMOR, () -> ArmorNoTailModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorUpperBodyModel.INNER_ARMOR, () -> ArmorUpperBodyModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorUpperBodyModel.OUTER_ARMOR, () -> ArmorUpperBodyModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorAbdomenModel.INNER_ARMOR, () -> ArmorAbdomenModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorAbdomenModel.OUTER_ARMOR, () -> ArmorAbdomenModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorSnakeAbdomenModel.INNER_ARMOR, () -> ArmorSnakeAbdomenModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorSnakeAbdomenModel.OUTER_ARMOR, () -> ArmorSnakeAbdomenModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorHeadlessKnightModel.INNER_ARMOR, () -> ArmorHeadlessKnightModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorHeadlessKnightModel.OUTER_ARMOR, () -> ArmorHeadlessKnightModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexAlienModel.INNER_ARMOR, () -> ArmorLatexAlienModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexAlienModel.OUTER_ARMOR, () -> ArmorLatexAlienModel.createArmorLayer(ArmorModel.OUTER));
        if (useNewModels) {
            ForgeHooksClient.registerLayerDefinition(ArmorLatexWolfModel.RemodelFemale.INNER_ARMOR, () -> ArmorLatexWolfModel.RemodelFemale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexWolfModel.RemodelFemale.OUTER_ARMOR, () -> ArmorLatexWolfModel.RemodelFemale.createArmorLayer(ArmorModel.OUTER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexWolfModel.RemodelMale.INNER_ARMOR, () -> ArmorLatexWolfModel.RemodelMale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexWolfModel.RemodelMale.OUTER_ARMOR, () -> ArmorLatexWolfModel.RemodelMale.createArmorLayer(ArmorModel.OUTER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSharkModel.RemodelFemale.INNER_ARMOR, () -> ArmorLatexSharkModel.RemodelFemale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSharkModel.RemodelFemale.OUTER_ARMOR, () -> ArmorLatexSharkModel.RemodelFemale.createArmorLayer(ArmorModel.OUTER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSharkModel.RemodelMale.INNER_ARMOR, () -> ArmorLatexSharkModel.RemodelMale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSharkModel.RemodelMale.OUTER_ARMOR, () -> ArmorLatexSharkModel.RemodelMale.createArmorLayer(ArmorModel.OUTER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSnowLeopardModel.RemodelFemale.INNER_ARMOR, () -> ArmorLatexSnowLeopardModel.RemodelFemale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSnowLeopardModel.RemodelFemale.OUTER_ARMOR, () -> ArmorLatexSnowLeopardModel.RemodelFemale.createArmorLayer(ArmorModel.OUTER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSnowLeopardModel.RemodelMale.INNER_ARMOR, () -> ArmorLatexSnowLeopardModel.RemodelMale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorLatexSnowLeopardModel.RemodelMale.OUTER_ARMOR, () -> ArmorLatexSnowLeopardModel.RemodelMale.createArmorLayer(ArmorModel.OUTER));

            ForgeHooksClient.registerLayerDefinition(ArmorUpperBodyModel.RemodelFemale.INNER_ARMOR, () -> ArmorUpperBodyModel.RemodelFemale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorUpperBodyModel.RemodelFemale.OUTER_ARMOR, () -> ArmorUpperBodyModel.RemodelFemale.createArmorLayer(ArmorModel.OUTER));
            ForgeHooksClient.registerLayerDefinition(ArmorUpperBodyModel.RemodelMale.INNER_ARMOR, () -> ArmorUpperBodyModel.RemodelMale.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorUpperBodyModel.RemodelMale.OUTER_ARMOR, () -> ArmorUpperBodyModel.RemodelMale.createArmorLayer(ArmorModel.OUTER));

            ForgeHooksClient.registerLayerDefinition(ArmorAbdomenModel.Remodel.INNER_ARMOR, () -> ArmorAbdomenModel.Remodel.createArmorLayer(ArmorModel.INNER));
            ForgeHooksClient.registerLayerDefinition(ArmorAbdomenModel.Remodel.OUTER_ARMOR, () -> ArmorAbdomenModel.Remodel.createArmorLayer(ArmorModel.OUTER));
        }

        else { // Old model exclusive

        }

        // Compatibility
        ForgeHooksClient.registerLayerDefinition(ArmorLatexWolfModel.INNER_ARMOR, () -> ArmorLatexWolfModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexWolfModel.OUTER_ARMOR, () -> ArmorLatexWolfModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleWolfModel.INNER_ARMOR, () -> ArmorLatexMaleWolfModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleWolfModel.OUTER_ARMOR, () -> ArmorLatexMaleWolfModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleWolfModel.INNER_ARMOR, () -> ArmorLatexFemaleWolfModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleWolfModel.OUTER_ARMOR, () -> ArmorLatexFemaleWolfModel.createArmorLayer(ArmorModel.OUTER));

        ForgeHooksClient.registerLayerDefinition(ArmorMermaidSharkAbdomenModel.INNER_ARMOR, () -> ArmorMermaidSharkAbdomenModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorMermaidSharkAbdomenModel.OUTER_ARMOR, () -> ArmorMermaidSharkAbdomenModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorSirenAbdomenModel.INNER_ARMOR, () -> ArmorSirenAbdomenModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorSirenAbdomenModel.OUTER_ARMOR, () -> ArmorSirenAbdomenModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorMermaidSharkMaleModel.INNER_ARMOR, () -> ArmorMermaidSharkMaleModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorMermaidSharkMaleModel.OUTER_ARMOR, () -> ArmorMermaidSharkMaleModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorMermaidSharkFemaleModel.INNER_ARMOR, () -> ArmorMermaidSharkFemaleModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorMermaidSharkFemaleModel.OUTER_ARMOR, () -> ArmorMermaidSharkFemaleModel.createArmorLayer(ArmorModel.OUTER));

        ForgeHooksClient.registerLayerDefinition(ArmorLatexBeeModel.INNER_ARMOR, () -> ArmorLatexBeeModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexBeeModel.OUTER_ARMOR, () -> ArmorLatexBeeModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexDeerModel.INNER_ARMOR, () -> ArmorLatexDeerModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexDeerModel.OUTER_ARMOR, () -> ArmorLatexDeerModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleCatModel.INNER_ARMOR, () -> ArmorLatexMaleCatModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleCatModel.OUTER_ARMOR, () -> ArmorLatexMaleCatModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleCatModel.INNER_ARMOR, () -> ArmorLatexFemaleCatModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleCatModel.OUTER_ARMOR, () -> ArmorLatexFemaleCatModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleDragonModel.INNER_ARMOR, () -> ArmorLatexMaleDragonModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleDragonModel.OUTER_ARMOR, () -> ArmorLatexMaleDragonModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleDragonModel.INNER_ARMOR, () -> ArmorLatexFemaleDragonModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleDragonModel.OUTER_ARMOR, () -> ArmorLatexFemaleDragonModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexBigTailDragonModel.INNER_ARMOR, () -> ArmorLatexBigTailDragonModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexBigTailDragonModel.OUTER_ARMOR, () -> ArmorLatexBigTailDragonModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleWingedDragonModel.INNER_ARMOR, () -> ArmorLatexMaleWingedDragonModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleWingedDragonModel.OUTER_ARMOR, () -> ArmorLatexMaleWingedDragonModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleWingedDragonModel.INNER_ARMOR, () -> ArmorLatexFemaleWingedDragonModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleWingedDragonModel.OUTER_ARMOR, () -> ArmorLatexFemaleWingedDragonModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexSharkModel.INNER_ARMOR, () -> ArmorLatexSharkModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexSharkModel.OUTER_ARMOR, () -> ArmorLatexSharkModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleSharkModel.INNER_ARMOR, () -> ArmorLatexMaleSharkModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleSharkModel.OUTER_ARMOR, () -> ArmorLatexMaleSharkModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleSharkModel.INNER_ARMOR, () -> ArmorLatexFemaleSharkModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleSharkModel.OUTER_ARMOR, () -> ArmorLatexFemaleSharkModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleSquidDogModel.INNER_ARMOR, () -> ArmorLatexMaleSquidDogModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexMaleSquidDogModel.OUTER_ARMOR, () -> ArmorLatexMaleSquidDogModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleSquidDogModel.INNER_ARMOR, () -> ArmorLatexFemaleSquidDogModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexFemaleSquidDogModel.OUTER_ARMOR, () -> ArmorLatexFemaleSquidDogModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexWhiteKnightModel.INNER_ARMOR, () -> ArmorLatexWhiteKnightModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexWhiteKnightModel.OUTER_ARMOR, () -> ArmorLatexWhiteKnightModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexCentaurUpperModel.INNER_ARMOR, () -> ArmorLatexCentaurUpperModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexCentaurUpperModel.OUTER_ARMOR, () -> ArmorLatexCentaurUpperModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexCentaurLowerModel.INNER_ARMOR, () -> ArmorLatexCentaurLowerModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexCentaurLowerModel.OUTER_ARMOR, () -> ArmorLatexCentaurLowerModel.createArmorLayer(ArmorModel.OUTER));

        ForgeHooksClient.registerLayerDefinition(ArmorLatexOtterModel.INNER_ARMOR, () -> ArmorLatexOtterModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexOtterModel.OUTER_ARMOR, () -> ArmorLatexOtterModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexStigerModel.INNER_ARMOR, () -> ArmorLatexStigerModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexStigerModel.OUTER_ARMOR, () -> ArmorLatexStigerModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexCrocodileModel.INNER_ARMOR, () -> ArmorLatexCrocodileModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexCrocodileModel.OUTER_ARMOR, () -> ArmorLatexCrocodileModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexDragonModel.INNER_ARMOR, () -> ArmorLatexDragonModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexDragonModel.OUTER_ARMOR, () -> ArmorLatexDragonModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexSnowLeopardModel.INNER_ARMOR, () -> ArmorLatexSnowLeopardModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexSnowLeopardModel.OUTER_ARMOR, () -> ArmorLatexSnowLeopardModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexBuffSharkModel.INNER_ARMOR, () -> ArmorLatexBuffSharkModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLatexBuffSharkModel.OUTER_ARMOR, () -> ArmorLatexBuffSharkModel.createArmorLayer(ArmorModel.OUTER));
        ForgeHooksClient.registerLayerDefinition(ArmorLightLatexCentaurModel.INNER_ARMOR, () -> ArmorLightLatexCentaurModel.createArmorLayer(ArmorModel.INNER));
        ForgeHooksClient.registerLayerDefinition(ArmorLightLatexCentaurModel.OUTER_ARMOR, () -> ArmorLightLatexCentaurModel.createArmorLayer(ArmorModel.OUTER));

        if (useNewModels) {
            ForgeHooksClient.registerLayerDefinition(HairRemodel.RIG_UPPER_LOCATION, HairRemodel::createUpperHair);
            ForgeHooksClient.registerLayerDefinition(HairRemodel.RIG_LOWER_LOCATION, HairRemodel::createLowerHair);
        }

        ForgeHooksClient.registerLayerDefinition(LatexContainerRenderer.LAYER_LOCATION, LatexContainerRenderer::createLatexFill);
        ForgeHooksClient.registerLayerDefinition(PillowRenderer.LAYER_LOCATION, PillowRenderer::createBodyLayer);
    }
}
