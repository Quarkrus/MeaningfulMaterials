package com.chromanyan.meaningfulmaterials;

import com.chromanyan.meaningfulmaterials.datagen.tags.MMBlockTags;
import com.chromanyan.meaningfulmaterials.datagen.tags.MMItemTags;
import com.chromanyan.meaningfulmaterials.init.MMItems;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MeaningfulMaterials.MODID)
public class MeaningfulMaterials {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "meaningfulmaterials";
    // Directly reference a slf4j logger
    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();

    public MeaningfulMaterials() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MMItems.ITEMS_REGISTRY.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        MMBlockTags blockTags = new MMBlockTags(gen, efh);
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new MMItemTags(gen, blockTags, efh));
    }
}
