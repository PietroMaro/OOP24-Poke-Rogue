package it.unibo.pokerogue.model.enums;

/** 
 * The situation in which we check if we should activate the ability.
 */
public enum AbilitySituationChecks {

		/** 
		 * When the pokemon get attacked.
		 */
		ATTACK("attack"),
		/** 
		 * When the pokemon attack.
		 */
		ATTACKED("attacked"),
		/** 
		 * When the pokemon enter the field.
		 */
		SWITCHIN("switchIn"),
		/** 
		 * When the pokemon exited the field.
		 */
		SWITCHOUT("switchOut"),
		/** 
		 * in a neutral moment. 
		 */
		NEUTRAL("neutral");

	private final String abilitySituationChecksName;

	/** 
	 * constructor. 
	 * @param abilitySituationChecksName the string value
	 */
    AbilitySituationChecks(final String abilitySituationChecksName) {
        this.abilitySituationChecksName = abilitySituationChecksName;
    }

	/** 
	 * simple getter. 
	 * @return the string value
	 */
    public String abilitySituationChecksName() {
        return abilitySituationChecksName;
    }

	/** 
	 * ability from string. 
	 * @param abilitySituationChecks the string value of the abilitySituation
	 * @return the ability
	 */
    public static AbilitySituationChecks fromString(final String abilitySituationChecks) {
        for (final AbilitySituationChecks a : values()) {
            if (a.abilitySituationChecksName().equalsIgnoreCase(abilitySituationChecks)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown ability situation checks: " 
				+ abilitySituationChecks);
    }

}
