package net.lunarluned.mortem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.lunarluned.mortem.block.ModBlocks;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class MortemClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.putBlock(ModBlocks.COPPER_RAIL, ChunkSectionLayer.CUTOUT);
	}
}