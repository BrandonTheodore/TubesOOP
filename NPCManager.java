import java.util.*;

public class NPCManager {
    private static List<NPC> allNPC = new ArrayList<>();
    private static List<String> allItems = new ArrayList<>();
    private static CropsManager cropsm = new CropsManager();
    private static SeedsManager seedsm = new SeedsManager();
    private static FishManager fishm = new FishManager();
    private static FoodManager foodm = new FoodManager();

    public static void initNPC(Time time) {
        // Mayor Tadi
        NPC mayorTadi = new NPC("Mayor Tadi", time);
        mayorTadi.addLovedItems("Legend");
        mayorTadi.addLikedItems("Angler");
        mayorTadi.addLikedItems("Crimsonfish");
        mayorTadi.addLikedItems("Glacierfish");
        allItems.addAll(fishm.getAllFishNames());
        allItems.addAll(foodm.getAllFoodNames());
        allItems.addAll(seedsm.getAllSeedsNames());
        allItems.addAll(cropsm.getAllCropsNames());
        for (String item : allItems) {
            String lower = item.toLowerCase();
            if (!mayorTadi.getLovedItems().contains(lower) && !mayorTadi.getLikedItems().contains(lower)) {
                mayorTadi.addHatedItems(lower);
            }
        }

        NPC caroline = new NPC("Caroline", time);
        caroline.addLovedItems("firewood");
        caroline.addLovedItems("coal");
        caroline.addLikedItems("Potato");
        caroline.addLikedItems("Wheat");
        caroline.addHatedItems("Hot Pepper");

        NPC perry = new NPC("Perry", time);
        perry.addLovedItems("Cranberry");
        perry.addLovedItems("Blueberry");
        perry.addLikedItems("Wine");
        for (String fish : fishm.getAllFishNames()) {
            perry.addHatedItems(fish.toLowerCase());
        }

        NPC dasco = new NPC("Dasco", time);
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

        NPC emily = new NPC("Emily", time);
        for (String seed : seedsm.getAllSeedsNames()) {
            emily.addLovedItems(seed.toLowerCase());
        }
        emily.addLikedItems("Catfish");
        emily.addLikedItems("Salmon");
        emily.addLikedItems("Sardine");
        emily.addHatedItems("coal");
        emily.addHatedItems("Wood");

        NPC abigail = new NPC("Abigail", time);
        abigail.addLovedItems("Blueberry");
        abigail.addLovedItems("Melon");
        abigail.addLovedItems("Pumpkin");
        abigail.addLovedItems("Grape");
        abigail.addLovedItems("Cranberry");
        abigail.addLikedItems("Baguette");
        abigail.addLikedItems("Pumpkin Pie");
        abigail.addLikedItems("Wine");
        abigail.addHatedItems("Hot Pepper");
        abigail.addHatedItems("Cauliflower");
        abigail.addHatedItems("Parsnip");
        abigail.addHatedItems("Wheat");

        NPC vincent = new NPC("Vincent", time);
        vincent.addLovedItems("Grape");
        vincent.addLovedItems("firewood");
        vincent.addLovedItems("Baguette");
        vincent.addHatedItems("Wheat");
        vincent.addHatedItems("Hot Pepper");

        NPC abil = new NPC("Abil", time);
        abil.addLovedItems("Wheat");
        abil.addLovedItems("coal");
        abil.addLovedItems("Hot Pepper");
        abil.addHatedItems("Blueberry");
        abil.addHatedItems("Wine");

        NPC aul = new NPC("Aul", time);
        aul.addLovedItems("Melon");
        aul.addLovedItems("Pumpkin");
        aul.addLovedItems("Baguette");
        aul.addHatedItems("firewood");
        aul.addHatedItems("coal");

        NPC nazhif = new NPC("Nazhif", time);
        nazhif.addLikedItems("Catfish");
        nazhif.addLikedItems("Salmon");
        nazhif.addLikedItems("Sardine");
        nazhif.addHatedItems("Grape");
        nazhif.addHatedItems("Cauliflower");

        NPC syafiq = new NPC("Syafiq", time);
        syafiq.addLikedItems("Fish Stew");
        syafiq.addLikedItems("Baguette");
        caroline.addHatedItems("Potato");
        caroline.addHatedItems("Wheat");
        caroline.addHatedItems("Hot Pepper");

        NPC farhan = new NPC("Farhan", time);
        farhan.addLikedItems("Crimsonfish");
        farhan.addLikedItems("Glacierfish");
        farhan.addLikedItems("Pumpkin Pie");
        farhan.addHatedItems("Pufferfish");
        farhan.addHatedItems("Salmon");

        NPC bt = new NPC("BT", time);
        bt.addLovedItems("Pumpkin Pie");
        bt.addLikedItems("Blueberry");
        bt.addLikedItems("Wheat");
        bt.addHatedItems("Coal");
        bt.addHatedItems("Salmon");

        NPC arra = new NPC("Arra", time);
        arra.addLovedItems("Wheat");
        arra.addLikedItems("Salmon");
        arra.addLikedItems("Pumpkin Pie");
        arra.addHatedItems("Potato");
        arra.addHatedItems("Sardine");

        NPC fino = new NPC("Fino", time);
        fino.addLovedItems("Glacierfish");
        fino.addLikedItems("Pufferfish");
        fino.addLikedItems("Pumpkin Pie");
        fino.addHatedItems("Blueberry");
        fino.addHatedItems("Salmon");

        NPC mahesa = new NPC("Mahesa", time);
        mahesa.addLovedItems("Hot Papper");
        mahesa.addLikedItems("Cranberry");
        mahesa.addLikedItems("Baguette");
        mahesa.addHatedItems("Blueberry");
        mahesa.addHatedItems("Sardine");

        NPC ken = new NPC("Kenlyn", time);
        ken.addLovedItems("Fish Sandwich");
        ken.addLikedItems("Salmon");
        ken.addLikedItems("Cat Fish");
        ken.addHatedItems("Blueberry");
        ken.addHatedItems("Grape");
        

        allNPC.add(mayorTadi);
        allNPC.add(caroline);
        allNPC.add(perry);
        allNPC.add(dasco);
        allNPC.add(emily);
        allNPC.add(abigail);
        allNPC.add(vincent);
        allNPC.add(abil);
        allNPC.add(aul);
        allNPC.add(nazhif);
        allNPC.add(syafiq);
        allNPC.add(farhan);
        allNPC.add(bt);
        allNPC.add(arra);
        allNPC.add(fino);
        allNPC.add(mahesa);
        allNPC.add(ken);
    }

    public static List<NPC> getAllNPC() {
        return allNPC;
    }

    public NPC getNPCByName(String name) {
        for (NPC npc : allNPC) {
            if (npc.getName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }
}