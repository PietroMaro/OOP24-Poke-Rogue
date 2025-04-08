package it.unibo.PokeRogue.pokemon;

public enum StatusCondition{
	BURN("burn"),	
	FREEZE("freeze"),	
	PARALYSIS("paralysis"),	
	POISON("poison"),	
	SLEEP("sleep"),	
	BOUND("bound"),	
	CONFUSION("confusion"),	
	FLINCH("flinch"),	
	SEEDED("seeded");	

	private final String statusName;

    StatusCondition(String statusName) {
        this.statusName = statusName;
    }

    public String statusName() {
        return this.statusName;
    }

    public static StatusCondition fromString(String status) {
        for (StatusCondition s : StatusCondition.values()) {
            if (s.statusName().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status condition: " + status);
    }
}
