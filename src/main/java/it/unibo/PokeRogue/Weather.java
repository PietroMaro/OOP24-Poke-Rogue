public enum Weather{
	SUNLIGHT("sunlight"),
	RAIN("rain"),
	SANDSTORM("sandstorm"),
	HAIL("hail");
	private final String weatherName;

    Weather(String weatherName) {
        this.weatherName = weatherName;
    }
}
