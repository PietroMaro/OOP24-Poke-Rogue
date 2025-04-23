package it.unibo.PokeRogue.ability;

public enum AbilitySituationChecks{
		ATTACK("attack"),
		ATTACKED("attacked"),
		SWITCHIN("switchIn"),
		SWITCHOUT("switchOut"),
		NEUTRAL("neutral"),
		PASSIVE("passive");

	private final String abilitySituationChecksName;

    AbilitySituationChecks(String abilitySituationChecksName) {
        this.abilitySituationChecksName = abilitySituationChecksName;
    }

    public String abilitySituationChecksName() {
        return abilitySituationChecksName;
    }

    public static AbilitySituationChecks fromString(String abilitySituationChecks) {
        for (AbilitySituationChecks a : AbilitySituationChecks.values()) {
            if (a.abilitySituationChecksName().equalsIgnoreCase(abilitySituationChecks)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown ability situation checks: " + abilitySituationChecks);
    }

}
