import java.util.*;

public class NPC {
    private String name;
    private int heartPoints;
    private RelationshipStatus relationshipStatus;
    private List<String> lovedItems;
    private List<String> likedItems;
    private List<String> hatedItems;
    private int proposalTime;
    private int marryTime;

    // konstruktor
    public NPC(String name) {
        this.name = name;
        heartPoints = 0;
        relationshipStatus = RelationshipStatus.SINGLE;
        this.lovedItems = new ArrayList<>();
        this.likedItems = new ArrayList<>();
        this.hatedItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addLovedItems(String itemName) {
        lovedItems.add(itemName);
    }

    // public void addLovedItemsByList(List<String> itemNameList) {
    //     hatedItems.addAll(itemNameList);  
    // }

    public void addLikedItems(String itemName) {
        likedItems.add(itemName);
    }

    public void addHatedItems(String itemName) {
        hatedItems.add(itemName);
    }

    // public void addHatedItemsByList(List<String> itemNameList) {
    //     hatedItems.addAll(itemNameList);  
    // }

    public RelationshipStatus getRelationshipStatus() {
        return relationshipStatus;
    }

    public void addHeartPoints(int points) {
        heartPoints += points;
        if (heartPoints > 150) {
            heartPoints = 150;
        }
    }

    public void reduceHearthPoints(int points) {
        heartPoints -= points;
        if (heartPoints < 0) {
            heartPoints = 0;
        }
    }

    public void receiveGift(Player player, Item item) {
        // pastiin udah dicek dulu player ada di dalem rumah atau engga (ini dipastiinnya di player bukan ya?)
        
        if (!player.getInventory().checkItem(item)) {
            System.out.println("Kamu tidak memiliki item yang ingin diberikan.");
            return;
        }

        String itemName = item.getName().toLowerCase();
 
        // tambahin heart point
        if (lovedItems.contains(itemName)) {
            addHeartPoints(25);
            System.out.println("Terima kasih ya! Aku suka banget " + itemName);
        } else if (likedItems.contains(itemName)) {
            addHeartPoints(20);
            System.out.println("Terima kasih ya! Aku suka " + itemName);
        } else if (hatedItems.contains(itemName)) {
            reduceHearthPoints(25);
            System.out.println("Maaf, aku ga suka " + itemName);
        } else {
            System.out.println("Okay thanks");
        }
        
        // hapus item dari inventory player
        player.getInventory().removeItem(item, 1);
        // tambah 10 menit mau di player aja
    }

    // method buat nerima propose
    public void receiveProposal(Player player) {
        if (!player.getInventory().checkItemByName("Proposal Ring")) {
            System.out.println("Kamu tidak memiliki proposal ring.");
            return;
        }

        if (heartPoints < 150) {
            System.out.println("Maaf, lamaranmu ditolak karena heartpointsmu belum cukup");
            player.consumeEnergy(20);
            // tambah waktu 1 jam udah di player
            Time now = Farm.getTime();
            now.addTime(60);
            return;
        }

        this.relationshipStatus = RelationshipStatus.FIANCE;
        player.consumeEnergy(10);
        // tambah waktu 1 jam
        System.out.println("Selamat! Lamaranmu diterima.");
        proposalTime = farm.getDayCount();
    }

    public void marryPlayer(Player player) {
        marryTime = farm.getDayCount();
        if (relationshipStatus != RelationshipStatus.FIANCE) {
            System.out.println("Kamu belum bertunangan dengan " + name);
        }
        else if (marryTime- proposalTime < 1) {
            System.out.println("Tunggu minimal 1 hari setelah tunangan untuk menikah ya.");
            return;
        } else {
            this.relationshipStatus = RelationshipStatus.SPOUSE;
            player.consumeEnergy(80);
            LocalTime.of(22, 00); // time skip ke 22.00
        }
    }

    public void chatWith(Player player) {
        // pastiin udah dicek belum player ada di rumah atau engga

        if (player.getEnergy() < (-10)) {
            System.out.println("Energi kamu tidak cukup untuk mengobrol.");
            return;
        }

        player.consumeEnergy(10);
        Time now = Farm.getTime();
        now.addTime(10); // tambah waktu 10 menit
        heartPoints += 10;

        System.out.println(player.getName() + " : ....");
        System.out.println(name + " : ....");
    }

    // ini ntar dipanggil di game(?)
    public void nextDay() {
        if (relationshipStatus == RelationshipStatus.FIANCE) {
            proposalTime++;
            proposalTimarry     }
    }
}
