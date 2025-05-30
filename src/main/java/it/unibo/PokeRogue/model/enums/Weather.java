package it.unibo.pokerogue.model.enums;

/**
 * The weather that can be applied to a
 * pokemon battle.
 */
public enum Weather {
	/**
	 * Sunny weather condition, boosts fire-type moves.
	 */
	SUNLIGHT("sunlight"),

	/**
	 * Rainy weather condition, boosts water-type moves.
	 */
	RAIN("rain"),

	/**
	 * Sandstorm weather condition, damages non-rock/steel/ground types.
	 */
	SANDSTORM("sandstorm"),

	/**
	 * Hail weather condition, damages non-ice types.
	 */
	HAIL("hail");

	private final String weatherName;

	Weather(final String weatherName) {
		this.weatherName = weatherName;
	}

	/**
	 * Gives the name of the Weather in string form.
	 * 
	 * @return the name as a String
	 */
	public String weatherName() {
		return weatherName;
	}

	/**
	 * Gives the weather given his String name.
	 * 
	 * @param weather
	 * @return the Weather
	 */
	public static Weather fromString(final String weather) {
		for (final Weather t : values()) {
			if (t.weatherName().equalsIgnoreCase(weather)) {
				return t;
			}
		}
		throw new IllegalArgumentException("Unknown weather: " + weather);
	}
}
