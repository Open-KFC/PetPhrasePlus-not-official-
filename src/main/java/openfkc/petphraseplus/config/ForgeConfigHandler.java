package openfkc.petphraseplus.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import openfkc.petphraseplus.core.PetPhrasePlus;
import openfkc.petphraseplus.handler.ChatEventHandler;

@Config(modid = PetPhrasePlus.MODID)
public class ForgeConfigHandler {

    @Config.LangKey("config.petphraseplus.general")
    public static final General general = new General();

    public static class General{

        @Config.LangKey("config.petphraseplus.general.enablePetPhrase")
        public boolean enablePetPhrase = true;

        @Config.LangKey("config.petphraseplus.general.head")
        public String head = "";

        @Config.LangKey("config.petphraseplus.general.tailInner")
        public String tailInner = "";

        @Config.LangKey("config.petphraseplus.general.tailOuter")
        public String tailOuter = "";

        @Config.LangKey("config.petphraseplus.general.mark")
        public String mark = "`";

        @Config.LangKey("config.petphraseplus.general.replaces")
        public String[] replaces = new String[]{
                "我--咱",
                "汪--喵"
        };

        @Config.LangKey("config.petphraseplus.general.punctuations")
        public String punctuations = "!?.;:()~\"'[]{}！？。；：（）～“”‘’【】｛｝¡¿「」『』…—";

    }

    @Mod.EventBusSubscriber(modid = PetPhrasePlus.MODID)
    private static class EventHandler{
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if(event.getModID().equals(PetPhrasePlus.MODID)){
                ConfigManager.sync(PetPhrasePlus.MODID, Config.Type.INSTANCE);
                ChatEventHandler.syncWithUpdatedConfig();
            }
        }
    }

}
