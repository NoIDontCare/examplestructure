package io.github.witherdoggie.examplestructure;

import io.github.witherdoggie.examplestructure.structure.ExampleStructure;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class BasicStructureExample implements ModInitializer {

    public static final String MODID = "example";

    public static final StructureFeature<DefaultFeatureConfig> EXAMPLE = new ExampleStructure(DefaultFeatureConfig.CODEC);
    public static final ConfiguredStructureFeature<?, ?> EXAMPLE_CONFIGURED = EXAMPLE.configure(DefaultFeatureConfig.DEFAULT);
    public static final StructurePieceType PIECE = StructurePieceType.register(ExampleGenerator.Piece::new, "example");

    @Override
    public void onInitialize() {

        FabricStructureBuilder.create(new Identifier(MODID, "example"), EXAMPLE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(20, 8, 1237986756)
                .adjustsSurface()
                .register();

        Registry<ConfiguredStructureFeature<?, ?>> myConfigured = (BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE);
        Registry.register(myConfigured, new Identifier(MODID, "example"), EXAMPLE_CONFIGURED);

        BiomeModifications.create(new Identifier(MODID, "example")).add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), context -> {
            context.getGenerationSettings().addBuiltInStructure(EXAMPLE_CONFIGURED);
        });
    }
}
