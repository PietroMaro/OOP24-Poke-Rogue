package it.unibo.pokerogue.model.api.pokemon;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.enums.Nature;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.StatusCondition;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.impl.MoveFactoryImpl;
import it.unibo.pokerogue.model.impl.RangeImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.ToString;
import java.awt.Image;

import java.lang.reflect.InvocationTargetException;

/**
 * the Pokemon class.
 */
@Getter
@Setter
@ToString
public final class PokemonImpl implements Pokemon {
	@Getter(AccessLevel.NONE)
	private MoveFactoryImpl moveFactoryInstance;
	@Getter(AccessLevel.NONE)
	private final Random random = new Random();
	@Getter(AccessLevel.NONE)
	private int totalUsedEv;
	private Map<Stats, Integer> baseStats;
	private Nature nature;
	private Map<Stats, Integer> iv; // 0-31 random when spawned
	private Map<Stats, Range<Integer>> ev; // 0-255 the pokemon can have a total of 510
	private Range<Integer> level;
	private Map<Stats, Range<Integer>> actualStats;
	private Map<Stats, Range<Integer>> tempStatsBonus;
	private Map<Integer, String> levelMovesLearn;
	private List<Move> actualMoves = new ArrayList<>();
	private String levelUpCurve; // https://m.bulbapedia.bulbagarden.net/wiki/Experience
	private Map<Stats, Integer> givesEv;
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
    private Map<StatusCondition, Integer> statusDuration;
	private boolean hasToLearnMove;
	private Optional<Move> newMoveToLearn = Optional.empty();

	private Image spriteFront;
	private Image spriteBack;

	/**
	 * The construct takes the blueprint and adds the random values.
	 * @param pokemonBlueprint the blueprint of the pokemon you want to create
	 */
	public PokemonImpl(final PokemonBlueprint pokemonBlueprint) throws 
		InstantiationException,
		IllegalAccessException,
		NoSuchMethodException,
		InvocationTargetException {
		this.moveFactoryInstance = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
		this.baseStats = pokemonBlueprint.stats();
		generateivs();
		generateEvs();
		this.nature = Nature.getRandomNature();
		this.level = new RangeImpl<>(1, 100, 1);
		calculateActualStats();
		initTempStatsBonus();
		initLevelMovesLearn(pokemonBlueprint.learnableMoves());
		initActualMoves();
		this.levelUpCurve = pokemonBlueprint.growthRate();
		this.givesEv = pokemonBlueprint.givesEv();
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
		this.statusDuration = new HashMap<>();
	}

	private void generateivs() {
		final int maxIv = 32;
		this.iv = new HashMap<>();
		for (final Stats stat : Stats.values()) {
			this.iv.put(stat, random.nextInt(maxIv)); // iv tra 0 e 31
		}
	}

	private void generateEvs() {
		final int maxEv = 252;
		final int minEv = 0;
		this.ev = new HashMap<>();
		for (final Stats stat : Stats.values()) {
			this.ev.put(stat, new RangeImpl<>(minEv, maxEv, minEv));
		}
	}

	private void initGender() {
		if (random.nextInt(2) == 1) {
			this.gender = "male";
		} else {
			this.gender = "female";
		}
	}

	private void initAbility(final List<String> possibleAbilities) {
		this.abilityName = possibleAbilities.get(random.nextInt(possibleAbilities.size()));
	}

	private void initTempStatsBonus() {
		final int minTempStat = -6;
		final int maxTempStat = 6;
		final int defaultTempStat = 6;
		this.tempStatsBonus = new HashMap<>();
		for (final Stats stat : Stats.values()) {
			this.tempStatsBonus.put(stat, new RangeImpl<>(minTempStat, maxTempStat, defaultTempStat));
		}
		this.tempStatsBonus.put(Stats.CRIT_RATE, new RangeImpl<>(minTempStat, maxTempStat, defaultTempStat));
		this.tempStatsBonus.put(Stats.ACCURACY, new RangeImpl<>(minTempStat, maxTempStat, defaultTempStat));
	}

	private void initTypes(final List<String> types) {
		this.type1 = Type.fromString(types.get(0));
		this.type2 = Optional.empty();
		if (types.size() > 1) {
			this.type2 = Optional.of(Type.fromString(types.get(1)));
		}
	}

	private void initLevelMovesLearn(final Map<String, String> learnableMoves) {
		this.levelMovesLearn = new HashMap<>();
		for (final String key : learnableMoves.keySet()) {
			this.levelMovesLearn.put(Integer.parseInt(key), learnableMoves.get(key));
		}
	}

	private void initActualMoves() {
		final int firstMoveKey = 1;
		for (final int key : this.levelMovesLearn.keySet()) {
			if (key == firstMoveKey) {
				this.actualMoves.add(moveFactoryInstance.moveFromName(this.levelMovesLearn.get(key)));
			}
		}
	}

	private void calculateActualStats() {
		final int constAdder = 5;
		final double positiveMultiplier = 1.1;
		final double negativeMultiplier  = 0.9;
		final int maxStat = 252;
		actualStats = new HashMap<>();

		final int maxLife = (int) Math.floor((2 * this.baseStats.get(Stats.HP) + this.iv.get(Stats.HP)
				+ this.ev.get(Stats.HP).getCurrentValue() / 4) * this.level.getCurrentValue() / 100)
				+ this.level.getCurrentValue() + 10;

		final Range<Integer> rangeHp = new RangeImpl<>(0, maxLife, maxLife);
		actualStats.put(Stats.HP, rangeHp);
		for (final Stats stat : Stats.values()) {
			if (stat == Stats.HP
				|| stat == Stats.CRIT_RATE
				|| stat == Stats.ACCURACY) {
				continue;
			}

			int statValue = (int) Math.round(
					Math.floor(
							(2 * baseStats.get(stat) 
							  + iv.get(stat) 
							  + (double) ev.get(stat).getCurrentValue() / 4
									* level.getCurrentValue()) / 100.0))
					+ constAdder;

			if (Nature.checkStatIncrease(this.nature, stat)) {
				statValue *= positiveMultiplier;
			} else if (Nature.checkStatDecrease(this.nature, stat)) {
				statValue *= negativeMultiplier;
			}

			final Range<Integer> rangeStat = new RangeImpl<>(0, maxStat, statValue);
			actualStats.put(stat, rangeStat);
		}

	}

	private void calculateNewExpRange() {
		int newRequiredExp = 0;
		final int constDivider = 5;
		final int constAdder = 15;
		final int constAdder2 = 140;
		final double constMultiplier = 6 / 5;
		final int currentLevel = this.level.getCurrentValue() + 1;
		if ("fast".equals(this.levelUpCurve)) {
			newRequiredExp = (int) (4 * Math.pow(currentLevel, 3) / constDivider);
		} else if ("medium".equals(this.levelUpCurve)) {
			newRequiredExp = (int) Math.pow(currentLevel, 3);
		} else if ("medium-slow".equals(this.levelUpCurve)) {
			newRequiredExp = (int) ((constMultiplier  * Math.pow(currentLevel, 3)) 
					- constAdder
					* Math.pow(currentLevel, 2)
					+ 100 * currentLevel - constAdder2);
		} else if ("slow".equals(this.levelUpCurve)) {
			newRequiredExp = (int) (constDivider * Math.pow(currentLevel, 3) / 4);
		}
		this.exp = new RangeImpl<>(0, newRequiredExp, 0);
	}


	@Override
	public void levelUp(final boolean isPlayerPokemon) {
		this.level.increment(1);
		this.calculateActualStats();
		calculateNewExpRange();

		if (this.levelMovesLearn.keySet().contains(this.level.getCurrentValue())) {
			final String moveToLearn = this.levelMovesLearn.get(this.level.getCurrentValue());
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
	public void learnNewMove(final Optional<Integer> indexMoveToReplace) {
		if (!(this.hasToLearnMove && !this.newMoveToLearn.isEmpty())) {
			throw new UnsupportedOperationException("The pokemon " + this.name + " doesn't have to learn a move");
		}
		if (!indexMoveToReplace.isEmpty() && indexMoveToReplace.get() < this.actualMoves.size()) {
			this.actualMoves.set(indexMoveToReplace.get(), this.newMoveToLearn.get());
		}
		this.hasToLearnMove = false;
		this.newMoveToLearn = Optional.empty();
	}

	@Override
	public void inflictDamage(final int amount) {
		this.actualStats.get(Stats.HP).decrement(amount);
	}

	@Override
	public void increaseExp(final int amount, final boolean isPlayerPokemon) {
		this.exp.increment(amount);
		if (this.exp.getCurrentValue().equals(this.exp.getCurrentMax())) {
			levelUp(isPlayerPokemon);
		}
	}

	@Override
	public void increaseEv(final Map<Stats, Integer> increaseEv) {
		final int maxTotalEv = 510;
		for (final Stats key : increaseEv.keySet()) {
			if (this.totalUsedEv + increaseEv.get(key) < maxTotalEv) {
				this.ev.get(key).increment(increaseEv.get(key));
			}
		}
	}

	@Override
	public List<Type> getTypes() {
		final List<Type> res = new ArrayList<>();
		res.add(this.type1);
		if (this.type2.isPresent()) {
			res.add(this.type2.get());
		}
		return res;
	}
}
