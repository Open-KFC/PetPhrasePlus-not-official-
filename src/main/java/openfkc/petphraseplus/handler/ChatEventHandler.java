package openfkc.petphraseplus.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import openfkc.petphraseplus.config.ForgeConfigHandler;

import java.util.*;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ChatEventHandler {

    public static boolean ignoreEvent = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void chatHandler(ClientChatEvent event){
        if(ignoreEvent || event.getMessage().startsWith("/") || !ForgeConfigHandler.general.enablePetPhrase){return;}
        ignoreEvent = true;
        event.setCanceled(true);
        /*我的目的是让玩家在呼出输入框后按上下键可以切换回其原本写的句子而不是加过口癖或去过标识符的*/
        Objects.requireNonNull(Minecraft.getMinecraft().currentScreen).sendChatMessage(addPetPhrase(event.getMessage()),false);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
        ignoreEvent = false;
    }

    public static final List<Character> punctuations = new ArrayList<>();

    public static final Map<String,String> replaceMap = new HashMap<>();

    /*call during preInit phase or on config changed*/
    public static void syncWithUpdatedConfig(){
        replaceMap.clear();
        for(String str : ForgeConfigHandler.general.replaces){
            String[] spl = str.split("--");
            if(!str.contains("--") || spl.length == 0){continue;}
            replaceMap.put(spl[0], spl.length >= 2 ? spl[1] : "");
        }

        punctuations.clear();
        for(int i = 0; i < ForgeConfigHandler.general.punctuations.length(); i++){
            punctuations.add(ForgeConfigHandler.general.punctuations.charAt(i));
        }

    }

    public static String addPetPhrase(String stringIn){
        /*identifier*/
        if(stringIn.contains(ForgeConfigHandler.general.mark)){
            return stringIn.replace(ForgeConfigHandler.general.mark, "");
        }

        String result = stringIn;
        /*replace*/
        for(Map.Entry<String,String> entry : replaceMap.entrySet()){
            result = result.replace(entry.getKey(), entry.getValue());
        }
        /*tailInner*/
        for(int i = 0; i < result.length(); i++){
            if(!punctuations.contains(result.charAt(result.length() - i - 1))){
                StringBuilder sb = new StringBuilder(result);
                sb.insert(result.length() - i,ForgeConfigHandler.general.tailInner);
                result = sb.toString();
                break;
            }
        }
        /*head & tailOuter*/
        result = ForgeConfigHandler.general.head + result + ForgeConfigHandler.general.tailOuter;

        return result;
    }

}
