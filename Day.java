public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    public Day nextDay() {
        int besok = (this.ordinal() + 1) % values().length;
        return values()[besok];
    }
}

// Kalau mau pake, fungsinya var.nextDay()
// var merupakan Day