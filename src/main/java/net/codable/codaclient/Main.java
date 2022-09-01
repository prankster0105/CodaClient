package net.codable.codaclient;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.codable.codaclient.config.ModDisplayConfig;
import net.codable.codaclient.mixin.MinecraftClientMixin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class Main implements ModInitializer {

	public static ModDisplayConfig CONFIG;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		MinecraftClient client = MinecraftClient.getInstance();
		AutoConfig.register(ModDisplayConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(ModDisplayConfig.class).getConfig();
		ClientTickEvents.END_WORLD_TICK.register(MinecraftClient -> {
			// if multiplier is not 0, keep as-is, otherwise change it to 1
			int multiplier = (CONFIG.FPS_MULTIPLIER != 0 ? CONFIG.FPS_MULTIPLIER : 1);
			int fps = ((MinecraftClientMixin) client).getCurrentFps() * multiplier;
			client.fpsDebugString = fps + " fps T: inffast B: 0";
		});



		System.out.println("Hello Fabric world!!");
	}
}