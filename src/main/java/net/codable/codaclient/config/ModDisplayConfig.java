package net.codable.codaclient.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "CodaClient")
public class ModDisplayConfig implements ConfigData {



    //default is 8
    @ConfigEntry.BoundedDiscrete(min = 0L,max = 100L)
    public final int FPS_MULTIPLIER = 8;

    //false by default
    @ConfigEntry.Gui.Tooltip()
    public final boolean DISPLAY_FPS = false;
}
