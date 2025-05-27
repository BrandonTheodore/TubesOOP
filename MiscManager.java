import java.util.ArrayList;
import java.util.List;

public class MiscManager {
    private List<Misc> allMiscs;

    // harga jual harus lebih murah dari harga beli
    public MiscManager(){
        allMiscs = new ArrayList<>();
        allMiscs.add((new Misc("coal", 100, 50, MiscType.COAL)));
        allMiscs.add((new Misc("firewood", 100, 50, MiscType.FIREWOOD)));
        allMiscs.add((new Misc("proposal ring", 100, 50, MiscType.PROPOSAL_RING)));
    }

    public List<Misc> getAllMiscs() {
        return new ArrayList<>(allMiscs); // return copy dari array allMiscs
    }
}
