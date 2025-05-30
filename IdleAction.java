public interface IdleAction {
    void idle();
}

class Watering implements IdleAction{
    public void idle(){
        System.out.println("Watering the flower...");
    }
}

class Humming implements IdleAction{
    public void idle(){
        System.out.println("Humming beethoven melodies...");
    }
}
