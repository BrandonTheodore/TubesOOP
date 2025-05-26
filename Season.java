public enum Season {
    SUMMER,
    WINTER,
    SPRING,
    FALL;

    public Season nextSeason() {
        int musim = (this.ordinal() + 1) % values().length;
        return values()[musim];
    }
}
