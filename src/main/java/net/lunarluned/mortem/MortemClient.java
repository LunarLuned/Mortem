package net.lunarluned.mortem;

import net.fabricmc.api.ClientModInitializer;
import net.lunarluned.mortem.client.CompassActionbar;

public class MortemClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		CompassActionbar.register();
		// BlockRenderLayerMap.putBlock(ModBlocks.COPPER_RAIL, ChunkSectionLayer.CUTOUT);
	}
}