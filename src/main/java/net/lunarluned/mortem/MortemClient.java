package net.lunarluned.mortem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.lunarluned.mortem.block.ModBlocks;
import net.lunarluned.mortem.client.CompassActionbar;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class MortemClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CompassActionbar.register();
		// BlockRenderLayerMap.putBlock(ModBlocks.COPPER_RAIL, ChunkSectionLayer.CUTOUT);

		BlockColorRegistry.register(List.of(new BlockTintSource() {
			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				return 0xFF000000 | BiomeColors.getAverageWaterColor(level, pos);
			}
			@Override
			public int color(BlockState state) {
				return 0xFF3F76E4;
			}
		}), ModBlocks.SIFTING_TABLE);
	}
}