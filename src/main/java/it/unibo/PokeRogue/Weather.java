package it.unibo.PokeRogue;

/**
* The weather that can be applied to a 
* pokemon battle.
*/
public enum Weather {
	SUNLIGHT("sunlight"),
	RAIN("rain"),
	SANDSTORM("sandstorm"),
	HAIL("hail");
	private final String weatherName;

    Weather(String weatherName) {
        this.weatherName = weatherName;
    }
	/**
	* Gives the name of the Weather in string form.
	* @return the name as a String 
	*/
	public String weatherName() {
        return weatherName;
    }

	/**
	* Gives the weather given his String name.
	* @param weather 
	* @return the Weather
	*/
    public static Weather fromString(String weather) {
        for (Weather t : Weather.values()) {
            if (t.weatherName().equalsIgnoreCase(weather)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown weather: " + weather);
    }
}
