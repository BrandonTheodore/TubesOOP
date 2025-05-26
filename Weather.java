public enum Weather {
    RAINY,
    SUNNY;

    public Weather nextWeather() {
        int change = (this.ordinal() + 1) % values().length;
        return values()[change];
    }
}

