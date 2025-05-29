package it.unibo.PokeRogue.pokemon;

import java.util.Random;


/**
 * Pokemon nature enumerator.
 */
public enum Nature {
		/**
		 * + attack | - specialAttack.
		 */
		ADAMANT("attack", "specialAttack"),
		/**
		 * neutral.
		 */
		BASHFUL("specialAttack", "specialAttack"),
		/**
		 * + defense | - attack.
		 */
		BOLD("defense", "attack"),
		/**
		 * + attack | - speed.
		 */
		BRAVE("attack", "speed"),
		/**
		 * + specialDefense | - attack.
		 */
		CALM("specialDefense", "attack"),
		/**
		 * + specialDefense | - specialAttack.
		 */
		CAREFUL("specialDefense", "specialAttack"),
		/**
		 * neutral.
		 */
		DOCILE("defense", "defense"),
		/**
		 * + specialDefense | - defense.
		 */
		GENTLE("specialDefense", "defense"),
		/**
		 * neutral.
		 */
		HARDY("attack", "attack"),
		/**
		 * + speed | - defense.
		 */
		HASTY("speed", "defense"),
		/**
		 * + defense | - specialAttack.
		 */
		IMPISH("defense", "specialAttack"),
		/**
		 * + speed | - specialAttack.
		 */
		JOLLY("speed", "specialAttack"),
		/**
		 * + defense | - specialDefense.
		 */
		LAX("defense", "specialDefense"),
		/**
		 * + attack  | - defense.
		 */
		LONELY("attack", "defense"),
		/**
		 * + specialAttack | - defense.
		 */
		MILD("specialAttack", "defense"),
		/**
		 * + specialAttack | - attack.
		 */
		MODEST("specialAttack", "attack"),
		/**
		 * + speed | - specialDefense.
		 */
		NAIVE("speed", "specialDefense"),
		/**
		 * + attack | - specialDefense.
		 */
		NAUGHTY("attack", "specialDefense"),
		/**
		 * + specialAttack | - speed.
		 */
		QUIET("specialAttack", "speed"),
		/**
		 * neutral.
		 */
		QUIRKY("specialDefense", "specialDefense"),
		/**
		 * + specialAttack | - specialDefense.
		 */
		RASH("specialAttack", "specialDefense"),
		/**
		 * + defense  | - speed.
		 */
		RELAXED("defense", "speed"),
		/**
		 * + specialDefense | - speed.
		 */
		SASSY("specialDefense", "speed"),
		/**
		 * neutral.
		 */
		SERIOUS("speed", "speed"),
		/**
		 * + speed | - attack.
		 */
		TIMID("speed", "attack");

		private final String statIncrease; // Statistica che aumenta.
		private final String statDecrease; // Statistica che diminuisce

		// Costruttore per inizializzare i campi
		Nature(final String statIncrease, final String statDecrease) {
			this.statIncrease = statIncrease;
			this.statDecrease = statDecrease;
		}


		/**
		 * Simple getter.
		 * @return The stat increase
		 */
		public String statIncrease() {
			return this.statIncrease;
		}

		/**
		 * Simple getter.
		 * @return The stat decrease
		 */
		public String statDecrease() {
			return this.statDecrease;
		}

		/**
		 * Returns a random Nature.
		 * @return a random nature 
		 */
		public static Nature getRandomNature() {
       		final Random random = new Random();
       		final int index = random.nextInt(values().length);
       		return values()[index]; 
    	}

		/**
		 * Check if the nature given increases the stat.
		 * @param nature The nature you want to check
		 * @param stat The statistic you want to check
		 * @return true if the stat is increased by the nature 
		 */
		public static boolean checkStatIncrease(final Nature nature, final String stat) {
    		return nature.statIncrease.equals(stat);
        }

		/**
		 * Check if the nature given decrease the stat.
		 * @param nature The nature you want to check
		 * @param stat The statistic you want to check
		 * @return true if the stat is decreased by the nature 
		 */
		public static boolean checkStatDecrease(final Nature nature, final String stat) {
    		return nature.statDecrease.equals(stat);
        }
	}
