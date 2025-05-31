public enum Season {
    SUMMER,
    FALL,
    WINTER,
    SPRING;

    public Season nextSeason() {
        int musim = (this.ordinal() + 1) % values().length;
        return values()[musim];
    }
}
