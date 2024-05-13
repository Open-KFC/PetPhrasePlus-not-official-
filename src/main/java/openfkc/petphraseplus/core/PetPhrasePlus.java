package openfkc.petphraseplus.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import openfkc.petphraseplus.handler.ChatEventHandler;

@Mod(
        modid = PetPhrasePlus.MODID,
        name = PetPhrasePlus.NAME,
        version = PetPhrasePlus.VERSION,
        clientSideOnly = true
)
public class PetPhrasePlus {
    public static final String MODID = "petphraseplus";
    public static final String NAME = "PetPhrasePlus(not-official)";
    public static final String VERSION = "0.1.0";

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event){
        ChatEventHandler.syncWithUpdatedConfig();
    }

}
