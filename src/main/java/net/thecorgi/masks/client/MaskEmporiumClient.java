package net.thecorgi.masks.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.thecorgi.masks.MaskEmporium;
import net.thecorgi.masks.init.blocks.HeadDisplayBlockRenderer;

public class MaskEmporiumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(MaskEmporium.HEAD_DISPLAY_BLOCK_ENTITY, HeadDisplayBlockRenderer::new);
    }
}
