package it.unibo.PokeRogue;

public enum Weather{
	SUNLIGHT("sunlight"),
	RAIN("rain"),
	SANDSTORM("sandstorm"),
	HAIL("hail");
	private final String weatherName;

    Weather(String weatherName) {
        this.weatherName = weatherName;
    }
	public String weatherName() {
        return weatherName;
    }

    public static Weather fromString(String weather) {
        for (Weather t : Weather.values()) {
            if (t.weatherName().equalsIgnoreCase(weather)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown weather: " + weather);
    }
}
