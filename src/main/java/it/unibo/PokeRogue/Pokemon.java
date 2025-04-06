package it.unibo.PokeRogue.PokemonUtilities;

import java.util.Map;
import java.util.Random;

public class Pokemon implements PokemonInterface {
	private Random random = new Random();
	String[] statNames = { "HP", "Attack", "Defense", "SpAttack", "SpDefense", "Speed" };
	private Map<String, Range<Integer>> BaseStats;
	private Map<String, Range<Integer>> ActualStats;
	private int totalUsedEV = 0;
	private Map<String, Range<Integer>> EV; // generato a caso
	private Map<String, Integer> IV; // generato a caso
	private Map<String, Move> actualMoves;
	private Map<Integer, Move> levelMovesLearn;
	private String levelUpcurve; // nome della curva di lvlup,
									// https://m.bulbapedia.bulbagarden.net/wiki/Experience#Medium_Fast
	private String evIncreaseType; // il nome dell'ev che aumento all'aversario quando questo pokemon viene ucciso
	private Range<Integer> exp;
	private int pokedexNumber;
	private int totEV;
	private int weight; // specifico
	private String name; // specifico

	public enum Nature {
		ADAMANT("Attack", "SpecialAttack"),
		BASHFUL("SpecialAttack", "SpecialAttack"),
		BOLD("Defense", "Attack"),
		BRAVE("Attack", "Speed"),
		CALM("SpecialDefense", "Attack"),
		CAREFUL("SpecialDefense", "SpecialAttack"),
		DOCILE("Defense", "Defense"),
		GENTLE("SpecialDefense", "Defense"),
		HARDY("Attack", "Attack"),
		HASTY("Speed", "Defense"),
		IMPISH("Defense", "SpecialAttack"),
		JOLLY("Speed", "SpecialAttack"),
		LAX("Defense", "SpecialDefense"),
		LONELY("Attack", "Defense"),
		MILD("SpecialAttack", "Defense"),
		MODEST("SpecialAttack", "Attack"),
		NAIVE("Speed", "SpecialDefense"),
		NAUGHTY("Attack", "SpecialDefense"),
		QUIET("SpecialAttack", "Speed"),
		QUIRKY("SpecialDefense", "SpecialDefense"),
		RASH("SpecialAttack", "SpecialDefense"),
		RELAXED("Defense", "Speed"),
		SASSY("SpecialDefense", "Speed"),
		SERIOUS("Speed", "Speed"),
		TIMID("Speed", "Attack");

		private final String statIncrease; // Statistica che aumenta
		private final String statDecrease; // Statistica che diminuisce

		// Costruttore per inizializzare i campi
		Nature(String statIncrease, String statDecrease) {
			this.statIncrease = statIncrease;
			this.statDecrease = statDecrease;

		}
	}

	public enum Type {
		BUG, DARK, DRAGON, ELECTRIC, FAIRY, FIGHT, FIRE, FLYING, GHOST, GRASS, GROUND, ICE, NORMAL, POISON, PSYCHC,
		ROCK, STELL, WATER;

		// Metodo che ritorna una stringa che descrive il tipo (utilizzo opzionale)
		@Override
		public String toString() {
			switch (this) {
				case BUG:
					return "Bug";
				case DARK:
					return "Dark";
				case DRAGON:
					return "Dragon";
				case ELECTRIC:
					return "Electric";
				case FAIRY:
					return "Fairy";
				case FIGHT:
					return "Fight";
				case FIRE:
					return "Fire";
				case FLYING:
					return "Flying";
				case GHOST:
					return "Ghost";
				case GRASS:
					return "Grass";
				case GROUND:
					return "Ground";
				case ICE:
					return "Ice";
				case NORMAL:
					return "Normal";
				case POISON:
					return "Poison";
				case PSYCHC:
					return "Psychc";
				case ROCK:
					return "Rock";
				case STELL:
					return "Steel";
				case WATER:
					return "Water";
				default:
					return super.toString();
			}
		}
	}

	private Nature nature;
	private Type type1; // specifico
	private Type type2; // specifico
	private int captureRate; // specifico
	private String gender; // generato a caso
	private String holdingObject; // riferimento allogetto PokeObject nel json
	private Range<Integer> level;
	private String abilityName; // da scegliere dal json

	public Pokemon(Map<String, Range<Integer>> EV, Map<String, Integer> IV, Map<String, Range<Integer>> BaseStats,
			Map<String, Range<Integer>> ActualStats,
			String abilityName, Map<String, Move> actualMoves, int captureRate, String evIncreaseType,
			Range<Integer> exp, String gender, String holdingObject, Range<Integer> level,
			Map<Integer, Move> levelMovesLearn, String levelUpcurve, String name, String nature, int pokedexNumber,
			String type1, String type2, int weight) {
		this.EV = generateEVs();
		this.IV = generateIVs();
		this.BaseStats = BaseStats;
		this.ActualStats = ActualStats;
		this.abilityName = abilityName;
		this.actualMoves = actualMoves;
		this.captureRate = captureRate;
		this.evIncreaseType = evIncreaseType;
		this.exp = exp;
		this.gender = gender;
		this.holdingObject = holdingObject;
		this.level = level;
		this.levelMovesLearn = levelMovesLearn;
		this.levelUpcurve = levelUpcurve;
		this.name = name;
		this.nature = Nature.valueOf(nature);
		this.pokedexNumber = pokedexNumber;
		this.type1 = Type.valueOf(type1);
		this.type2 = Type.valueOf(type2);
		this.weight = weight;
	}

	private Map<String, Integer> generateIVs() {
		for (String stat : statNames) {
			IV.put(stat, random.nextInt(32)); // IV tra 0 e 31

		}
		return IV;
	}

	private Map<String, Range<Integer>> generateEVs() {
		for (String stat : statNames) {
			EV.put(stat, new Range<>(0, 252, 0, (x, y) -> x + y, (x, y) -> x - y));

		}

		return EV;
	}

	private void evIncrease(String evName) {
		if (totalUsedEV < 510) {

			totalUsedEV += 1;
			EV.get(evName).increment(1);

		}

	}

	private void calculateRealStats() {
		
		int maxLife = (int) Math.floor(((2 * this.BaseStats.get("HP").getCurrentMax()
		+ this.IV.get("HP") 
		+ (this.EV.get("HP").getCurrentValue() / 4)) * this.level.getCurrentValue()) / 100)
		+ this.level.getCurrentValue() + 10;

		Range<Integer> rangeHP = new Range<Integer>(0,maxLife,maxLife,(x, y) -> x + y, (x, y) -> x - y);
		
		ActualStats.put("HP",rangeHP);
	
		
		for (String stat : statNames) {
			double statValue = (Math.floor(((2 * BaseStats.get(stat).getCurrentValue() + IV.get(stat) + (EV.get(stat).getCurrentValue() / 4)) * level.getCurrentValue()) / 100)) + 5;
	
			
			switch (nature) {
				case ADAMANT:
					if (stat.equals("Attack")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialAttack")) {
						statValue *= 0.90; 
					}
					break;
				case BOLD:
					if (stat.equals("Defense")) {
						statValue *= 1.10;
					} else if (stat.equals("Attack")) {
						statValue *= 0.90; 
					}
					break;
				case BRAVE:
					if (stat.equals("Attack")) {
						statValue *= 1.10; 
					} else if (stat.equals("Speed")) {
						statValue *= 0.90; 
					}
					break;
				case CALM:
					if (stat.equals("SpecialDefense")) {
						statValue *= 1.10; 
					} else if (stat.equals("Attack")) {
						statValue *= 0.90; 
					}
					break;
				case CAREFUL:
					if (stat.equals("SpecialDefense")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialAttack")) {
						statValue *= 0.90; 
					}
					break;
				case GENTLE:
					if (stat.equals("SpecialDefense")) {
						statValue *= 1.10; 
					} else if (stat.equals("Defense")) {
						statValue *= 0.90;
					}
					break;
				case HASTY:
					if (stat.equals("Speed")) {
						statValue *= 1.10;
					} else if (stat.equals("Defense")) {
						statValue *= 0.90; 
					}
					break;
				case IMPISH:
					if (stat.equals("Defense")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialAttack")) {
						statValue *= 0.90; 
					}
					break;
				case JOLLY:
					if (stat.equals("Speed")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialAttack")) {
						statValue *= 0.90; 
					}
					break;
				case LAX:
					if (stat.equals("Defense")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialDefense")) {
						statValue *= 0.90;
					}
					break;
				case LONELY:
					if (stat.equals("Attack")) {
						statValue *= 1.10; 
					} else if (stat.equals("Defense")) {
						statValue *= 0.90; 
					}
					break;
				case MILD:
					if (stat.equals("SpecialAttack")) {
						statValue *= 1.10; 
					} else if (stat.equals("Defense")) {
						statValue *= 0.90; 
					}
					break;
				case MODEST:
					if (stat.equals("SpecialAttack")) {
						statValue *= 1.10; 
					} else if (stat.equals("Attack")) {
						statValue *= 0.90; 
					}
					break;
				case NAIVE:
					if (stat.equals("Speed")) {
						statValue *= 1.10;
					} else if (stat.equals("SpecialDefense")) {
						statValue *= 0.90; 
					}
					break;
				case NAUGHTY:
					if (stat.equals("Attack")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialDefense")) {
						statValue *= 0.90; 
					}
					break;
				case QUIET:
					if (stat.equals("SpecialAttack")) {
						statValue *= 1.10; 
					} else if (stat.equals("Speed")) {
						statValue *= 0.90; 
					}
					break;
				case RASH:
					if (stat.equals("SpecialAttack")) {
						statValue *= 1.10; 
					} else if (stat.equals("SpecialDefense")) {
						statValue *= 0.90; 
					}
					break;
				case RELAXED:
					if (stat.equals("Defense")) {
						statValue *= 1.10; 
					} else if (stat.equals("Speed")) {
						statValue *= 0.90; 
					}
					break;
				case SASSY:
					if (stat.equals("SpecialDefense")) {
						statValue *= 1.10; 
					} else if (stat.equals("Speed")) {
						statValue *= 0.90; 
					}
					break;
				case TIMID:
					if (stat.equals("Speed")) {
						statValue *= 1.10; 
					} else if (stat.equals("Attack")) {
						statValue *= 0.90; 
					}
					break;
			}
	
			// Aggiorna la statistica attuale
			if (stat.equals("Attack")) {
				Range<Integer> range = ActualStats.get("Attack");
				range.setCurrentValue((int) statValue);
			} else if (stat.equals("SpAttack")) {
				Range<Integer> range = ActualStats.get("SpAttack");
				range.setCurrentValue((int) statValue);
			} else if (stat.equals("Defense")) {
				Range<Integer> range = ActualStats.get("Deffense");
				range.setCurrentValue((int) statValue);
			} else if (stat.equals("SpDefense")) {
				Range<Integer> range = ActualStats.get("SpDefense");
				range.setCurrentValue((int) statValue);
			} else if (stat.equals("Speed")) {
				Range<Integer> range = ActualStats.get("Speed");
				range.setCurrentValue((int) statValue);
			}
		}
	}


	//When a Pokémon grows a level, its stats will increase. For each level gained (ignoring Nature),
	//stats will increase by 1/50 the base stat value, and 1/100 the combined IV and EV

	//l'aumento di exp che serve usa queste formule https://bulbapedia.bulbagarden.net/wiki/Experience#Fluctuating
	public void levelUpStats() {
		//da implementare il reset di exp e il cambio di exp max
		int levelUp = level.getCurrentValue() + 1;
		level.setCurrentValue(levelUp);
		for (String stat : statNames) {
			int base = BaseStats.get(stat).getCurrentMax();
			int iv = IV.get(stat);
			int ev = EV.get(stat).getCurrentValue();
	
			int increase = (int) Math.floor(base / 50.0 + (iv + ev) / 100.0);
	
			Range<Integer> actualStat = ActualStats.get(stat);
			actualStat.increment(increase);  
		}
	}

}
