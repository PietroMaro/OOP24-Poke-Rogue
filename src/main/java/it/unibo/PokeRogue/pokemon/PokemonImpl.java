package it.unibo.PokeRogue.pokemon;

import it.unibo.PokeRogue.move.Move;
import it.unibo.PokeRogue.move.MoveFactoryImpl;
import it.unibo.PokeRogue.utilities.Range;
import it.unibo.PokeRogue.utilities.RangeImpl;
import java.util.Optional;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.ToString;
import java.awt.Image;

@Getter
@Setter
@ToString
public final class PokemonImpl implements Pokemon {
	@Getter(AccessLevel.NONE)
	private MoveFactoryImpl moveFactoryInstance = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
	@Getter(AccessLevel.NONE)
	final private Random random = new Random();
	@Getter(AccessLevel.NONE)
	final private List<String> statNames = new ArrayList<>(
			Arrays.asList("hp", "attack", "defense", "specialAttack", "specialDefense", "speed"));
	private int totalUsedEV = 0;
	private Map<String, Integer> baseStats;
	private Nature nature;
	private Map<String, Integer> IV; // 0-31 random when spawned
	private Map<String, Range<Integer>> EV; // 0-255 the pokemon can have a total of 510
	private Range<Integer> level;
	private Map<String, Range<Integer>> actualStats;
	private Map<String, Range<Integer>> tempStatsBonus;
	private Map<Integer, String> levelMovesLearn;
	private List<Move> actualMoves = new ArrayList<Move>();
	private String levelUpCurve; // https://m.bulbapedia.bulbagarden.net/wiki/Experience
	private Map<String, Integer> givesEV;
	private Range<Integer> exp;
	private int pokedexNumber;
	private int weight;
	private String name;
	@Getter(AccessLevel.NONE)
	private Type type1;
	@Getter(AccessLevel.NONE)
	private Optional<Type> type2;
	private int captureRate;
	private String gender;
	private Optional<String> holdingObject;
	private String abilityName;
	private Optional<StatusCondition> statusCondition;

	private boolean hasToLearnMove = false;
	private Optional<Move> newMoveToLearn = Optional.empty();

	private Image spriteFront;
	private Image spriteBack;

	public PokemonImpl(final PokemonBlueprint pokemonBlueprint) {
		this.baseStats = pokemonBlueprint.stats();
		generateIVs();
		generateEVs();
		this.nature = Nature.getRandomNature();
		this.level = new RangeImpl<Integer>(1, 100, 1);
		calculateActualStats();
		initTempStatsBonus();
		initLevelMovesLearn(pokemonBlueprint.learnableMoves());
		initActualMoves();
		this.levelUpCurve = pokemonBlueprint.growthRate();
		this.givesEV = pokemonBlueprint.givesEV();
		calculateNewExpRange();
		this.pokedexNumber = pokemonBlueprint.pokedexNumber();
		this.weight = pokemonBlueprint.weight();
		this.name = pokemonBlueprint.name();
		initTypes(pokemonBlueprint.types());
		this.captureRate = pokemonBlueprint.captureRate();
		initGender();
		this.holdingObject = Optional.empty();
		initAbility(pokemonBlueprint.possibleAbilities());
		this.statusCondition = Optional.empty();
		this.spriteFront = pokemonBlueprint.spriteFront();
		this.spriteBack = pokemonBlueprint.spriteBack();
	}

	private void generateIVs() {
		this.IV = new HashMap<String, Integer>();
		for (String stat : statNames) {
			this.IV.put(stat, random.nextInt(32)); // IV tra 0 e 31
		}
	}

	private void generateEVs() {
		this.EV = new HashMap<String, Range<Integer>>();
		for (String stat : statNames) {
			this.EV.put(stat, new RangeImpl<>(0, 252, 0));
		}
	}

	private void initGender() {
		if (random.nextInt(100) % 2 == 1) {
			this.gender = "male";
		} else {
			this.gender = "female";
		}
	}

	private void initAbility(List<String> possibleAbilities) {
		this.abilityName = possibleAbilities.get(random.nextInt(possibleAbilities.size()));
	}

	private void initTempStatsBonus() {
		this.tempStatsBonus = new HashMap<String, Range<Integer>>();
		for (String stat : statNames) {
			this.tempStatsBonus.put(stat, new RangeImpl<>(-6, 6, 0));
		}
		this.tempStatsBonus.put("critRate", new RangeImpl<>(-6, 6, 0));
		this.tempStatsBonus.put("accuracy", new RangeImpl<>(-6, 6, 0));
	}

	private void initTypes(List<String> types) {
		this.type1 = Type.fromString(types.get(0));
		this.type2 = Optional.empty();
		if (types.size() > 1) {
			this.type2 = Optional.of(Type.fromString(types.get(1)));
		}
	}

	private void initLevelMovesLearn(Map<String, String> learnableMoves) {
		this.levelMovesLearn = new HashMap<Integer, String>();
		for (String key : learnableMoves.keySet()) {
			this.levelMovesLearn.put(Integer.parseInt(key), learnableMoves.get(key));
		}
	}

	private void initActualMoves() {
		for (int key : this.levelMovesLearn.keySet()) {
			if (key == 1) {
				this.actualMoves.add(moveFactoryInstance.moveFromName(this.levelMovesLearn.get(key)));
			}
		}
	}

	private void calculateActualStats() {
		actualStats = new HashMap<String, Range<Integer>>();

		int maxLife = (int) Math.floor(((2 * this.baseStats.get("hp") + this.IV.get("hp")
				+ (this.EV.get("hp").getCurrentValue() / 4)) * this.level.getCurrentValue()) / 100)
				+ this.level.getCurrentValue() + 10;

		Range<Integer> rangeHp = new RangeImpl<Integer>(0, maxLife, maxLife);
		actualStats.put("hp", rangeHp);
		for (String stat : statNames.subList(1, statNames.size())) {

			int statValue = (int) Math.round(
					Math.floor(
							((2 * baseStats.get(stat) + IV.get(stat) + (double) EV.get(stat).getCurrentValue() / 4)
									* level.getCurrentValue()) / 100.0))
					+ 5;

			if (Nature.checkStatIncrease(this.nature, stat)) {
				statValue *= 1.1;
			} else if (Nature.checkStatDecrease(this.nature, stat)) {
				statValue *= 0.9;
			}

			Range<Integer> rangeStat = new RangeImpl<Integer>(0, 255, statValue);
			actualStats.put(stat, rangeStat);
		}

	}

	private void calculateNewExpRange() {
		int newRequiredExp = 0;
		int currentLevel = this.level.getCurrentValue();
		if (this.levelUpCurve == "fast") {
			newRequiredExp = (int) ((4 * Math.pow(currentLevel, 3)) / 5);
		} else if (this.levelUpCurve == "medium") {
			newRequiredExp = (int) Math.pow(currentLevel, 3);
		} else if (this.levelUpCurve == "medium-slow") {
			newRequiredExp = (int) ((6 / 5 * Math.pow(currentLevel, 3)) - 15 * Math.pow(currentLevel, 2)
					+ 100 * currentLevel - 140);
		} else if (this.levelUpCurve == "slow") {
			newRequiredExp = (int) ((5 * Math.pow(currentLevel, 3)) / 4);
		}
		this.exp = new RangeImpl<Integer>(0, newRequiredExp, 0);
	}

	private void levelUpStats() {
		for (String stat : statNames) {
			int base = baseStats.get(stat);
			int iv = IV.get(stat);
			int ev = EV.get(stat).getCurrentValue();

			int increase = (int) Math.floor(base / 50.0 + (iv + ev) / 100.0);

			Range<Integer> actualStat = actualStats.get(stat);
			actualStat.increment(increase);
		}
	}

	@Override
	public void levelUp(boolean isPlayerPokemon) {
		this.level.increment(1);
		levelUpStats();
		calculateNewExpRange();

		if (this.levelMovesLearn.keySet().contains(this.level.getCurrentValue())) {
			String moveToLearn = this.levelMovesLearn.get(this.level.getCurrentValue());
			if (this.actualMoves.size() < 4) {
				this.actualMoves.add(moveFactoryInstance.moveFromName(moveToLearn));
			} else {
				if (!isPlayerPokemon) {
					this.actualMoves.set(random.nextInt(4), moveFactoryInstance.moveFromName(moveToLearn));
				} else {
					this.hasToLearnMove = true;
					this.newMoveToLearn = Optional.of(moveFactoryInstance.moveFromName(moveToLearn));
				}
			}
		}
	}

	@Override
	public void learnNewMove(Optional<Integer> indexMoveToReplace) {
		if (!(this.hasToLearnMove && !this.newMoveToLearn.isEmpty())) {
			throw new UnsupportedOperationException("The pokemon " + this.name + " doesn't have to learn a move");
		}
		if (!indexMoveToReplace.isEmpty()) {
			this.actualMoves.set(indexMoveToReplace.get(), this.newMoveToLearn.get());
		}
		this.hasToLearnMove = false;
		this.newMoveToLearn = Optional.empty();
	}

	@Override
	public void inflictDamage(int amount) {
		this.actualStats.get("hp").decrement(amount);
	}

	@Override
	public void increaseExp(int amount, boolean isPlayerPokemon) {
		this.exp.increment(amount);
		if (this.exp.getCurrentValue() == this.exp.getCurrentMax()) {
			levelUp(isPlayerPokemon);
		}
	}

	@Override
	public void increaseEV(Map<String, Integer> increaseEV) {
		for (String key : increaseEV.keySet()) {
			if (this.totalUsedEV + increaseEV.get(key) < 510) {
				this.EV.get(key).increment(increaseEV.get(key));
			}
		}
	}

	@Override
	public List<Type> getTypes() {
		List<Type> res = new ArrayList<>();
		res.add(this.type1);
		if (this.type2.isPresent()) {
			res.add(this.type2.get());
		}
		return res;
	}
}