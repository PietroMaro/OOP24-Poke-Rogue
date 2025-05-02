package it.unibo.PokeRogue.pokemon;

import java.util.Random;

public enum Nature {
		ADAMANT("attack", "specialAttack"),
		BASHFUL("specialAttack", "specialAttack"),
		BOLD("defense", "attack"),
		BRAVE("attack", "speed"),
		CALM("specialDefense", "attack"),
		CAREFUL("specialDefense", "specialAttack"),
		DOCILE("defense", "defense"),
		GENTLE("specialDefense", "defense"),
		HARDY("attack", "attack"),
		HASTY("speed", "defense"),
		IMPISH("defense", "specialAttack"),
		JOLLY("speed", "specialAttack"),
		LAX("defense", "specialDefense"),
		LONELY("attack", "defense"),
		MILD("specialAttack", "defense"),
		MODEST("specialAttack", "attack"),
		NAIVE("speed", "specialDefense"),
		NAUGHTY("attack", "specialDefense"),
		QUIET("specialAttack", "speed"),
		QUIRKY("specialDefense", "specialDefense"),
		RASH("specialAttack", "specialDefense"),
		RELAXED("defense", "speed"),
		SASSY("specialDefense", "speed"),
		SERIOUS("speed", "speed"),
		TIMID("speed", "attack");

		private final String statIncrease; // Statistica che aumenta
		private final String statDecrease; // Statistica che diminuisce

		// Costruttore per inizializzare i campi
		Nature(String statIncrease, String statDecrease) {
			this.statIncrease = statIncrease;
			this.statDecrease = statDecrease;
		}

		public String statIncrease(){
			return this.statIncrease;
		}
		public String statDecrease(){
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
