package it.unibo.PokeRogue.pokemon;

import java.util.Random;

public enum Nature {
		ADAMANT("attack", "special-attack"),
		BASHFUL("special-attack", "special-attack"),
		BOLD("defense", "attack"),
		BRAVE("attack", "speed"),
		CALM("special-defense", "attack"),
		CAREFUL("special-defense", "special-attack"),
		DOCILE("defense", "defense"),
		GENTLE("special-defense", "defense"),
		HARDY("attack", "attack"),
		HASTY("speed", "defense"),
		IMPISH("defense", "special-attack"),
		JOLLY("speed", "special-attack"),
		LAX("defense", "special-defense"),
		LONELY("attack", "defense"),
		MILD("special-attack", "defense"),
		MODEST("special-attack", "attack"),
		NAIVE("speed", "special-defense"),
		NAUGHTY("attack", "special-defense"),
		QUIET("special-attack", "speed"),
		QUIRKY("special-defense", "special-defense"),
		RASH("special-attack", "special-defense"),
		RELAXED("defense", "speed"),
		SASSY("special-defense", "speed"),
		SERIOUS("speed", "speed"),
		TIMID("speed", "attack");

		private final String statIncrease; // Statistica che aumenta
		private final String statDecrease; // Statistica che diminuisce

		// Costruttore per inizializzare i campi
		Nature(String statIncrease, String statDecrease) {
			this.statIncrease = statIncrease;
			this.statDecrease = statDecrease;
		}

		String statIncrease(){
			return this.statIncrease;
		}
		String statDecrease(){
			return this.statDecrease;
		}
		public static Nature getRandomNature() {
       		Random random = new Random();
       		int index = random.nextInt(Nature.values().length);
       		return Nature.values()[index]; 
    	}
		public static boolean checkStatIncrease(Nature nature, String stat) {
    		return nature.statIncrease.equals(stat);
        }
		public static boolean checkStatDecrease(Nature nature, String stat) {
    		return nature.statDecrease.equals(stat);
        }
	}
