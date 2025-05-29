package it.unibo.pokerogue.model.enums;

public enum StatusCondition{
	BURN("burn"),	
	FREEZE("freeze"),	
	PARALYSIS("paralysis"),	
	POISON("poison"),	
	SLEEP("sleep"),	
	BOUND("bound"),	
	CONFUSION("confusion"),	
	FLINCH("flinch"),	
	TRAPPED("trapped"),
	CHARMED("charmed"),
	SEEDED("seeded");	

	private final String statusName;

    StatusCondition(final String statusName) {
        this.statusName = statusName;
    }

    public String statusName() {
        return this.statusName;
    }

    public static StatusCondition fromString(final String status) {
        for (final StatusCondition s : values()) {
            if (s.statusName().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status condition: " + status);
    }
}
