public class Idle {
    private IdleAction idle;

    public Idle(IdleAction idle){
        this.idle = idle;
    }

    public void setIdle(IdleAction idle){
        this.idle = idle;
    }

    public void idling(){
        idle.idle();
    }
}
