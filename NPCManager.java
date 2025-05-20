import java.util.*;

public class NPCManager {
    private static List<NPC> allNPC = new ArrayList<>();

    public static void initNPC() {
        // Mayor Tadi
        NPC mayorTadi = new NPC("Mayor Tadi");
        mayorTadi.addLovedItems("Legend");
        mayorTadi.addLikedItems("Angler");
        mayorTadi.addLikedItems("Crimsonfish");
        mayorTadi.addLikedItems("Glacierfish");

        NPC caroline = new NPC("Caroline");
        caroline.addLovedItems("Firewood");
        caroline.addLovedItems("Coal");
        caroline.addLikedItems("Potato");
        caroline.addLikedItems("Wheat");
        caroline.addHatedItems("Hot Pepper");

        NPC perry = new NPC("Perry");
        perry.addLovedItems("Cranberry");
        perry.addLovedItems("Blueberry");
        perry.addLikedItems("Wine");
        perry.addHatedItems("null"); // semua item fish

        NPC dasco = new NPC("Dasco");
        dasco.addLovedItems("The Legends of Spakbor");
        dasco.addLovedItems("Cooked Pig's Head");
        dasco.addLovedItems("Wine");
        dasco.addLovedItems("Fugu");
        dasco.addLovedItems("Spakbor Salad");
        dasco.addLikedItems("Fish Sandwich");
        dasco.addLikedItems("Fish Stew");
        dasco.addLikedItems("Baguette");
        dasco.addLikedItems("Fish n’ Chips");
        dasco.addHatedItems("Legend");
        dasco.addHatedItems("Grape");
        dasco.addHatedItems("Cauliflower");
        dasco.addHatedItems("Wheat");
        dasco.addHatedItems("Pufferfish");
        dasco.addHatedItems("Salmon");

        NPC emily = new NPC("Emily");
        emily.addLovedItems(""); // tambahin semua item seed
        emily.addLikedItems("Catfish");
        emily.addLikedItems("Salmon");
        emily.addLikedItems("Sardine");
        emily.addHatedItems("Coal");
        emily.addHatedItems("Wood");

        NPC abigail = new NPC("Abigail");
        abigail.addLovedItems("Blueberry");
        abigail.addLovedItems("Melon");
        abigail.addLovedItems("Pumpkin");
        abigail.addLovedItems("Grape");
        abigail.addLovedItems("Cranberry");
        abigail.addLikedItems("Beguette");
        abigail.addLikedItems("Pumpkin Pie");
        abigail.addLikedItems("Wine");
        abigail.addHatedItems("Hot Pepper");
        abigail.addHatedItems("Cauliflower");
        abigail.addHatedItems("Parsnip");
        abigail.addHatedItems("Wheat");

        allNPC.add(mayorTadi);
        allNPC.add(caroline);
        allNPC.add(perry);
        allNPC.add(dasco);
        allNPC.add(emily);
        allNPC.add(abigail);
    }

    public static List<NPC> getAllNPC() {
        return allNPC;
    }

    public static NPC getNPCByName(String name) {
        for (NPC npc : allNPC) {
            if (npc.getName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }
}