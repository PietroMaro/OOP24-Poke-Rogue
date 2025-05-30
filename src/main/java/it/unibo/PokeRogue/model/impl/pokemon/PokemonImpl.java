package it.unibo.pokerogue.model.impl.pokemon;

import java.util.Optional;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import it.unibo.pokerogue.model.api.Range;
import it.unibo.pokerogue.model.api.move.Move;
import it.unibo.pokerogue.model.api.pokemon.Pokemon;
import it.unibo.pokerogue.model.api.pokemon.PokemonBlueprint;
import it.unibo.pokerogue.model.enums.Nature;
import it.unibo.pokerogue.model.enums.Stats;
import it.unibo.pokerogue.model.enums.StatusCondition;
import it.unibo.pokerogue.model.enums.Type;
import it.unibo.pokerogue.model.impl.MoveFactoryImpl;
import it.unibo.pokerogue.model.impl.RangeImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.awt.Image;

import java.lang.reflect.InvocationTargetException;

/**
 * the Pokemon class.
 */
@ToString
public final class PokemonImpl implements Pokemon {
    private final MoveFactoryImpl moveFactoryInstance;
    private final Random random = new Random();
    @Getter
    @Setter
    private int totalUsedEv;
    private Map<Stats, Integer> baseStats;
    @Getter
    @Setter
    private Nature nature;
    private Map<Stats, Integer> iv; // 0-31 random when spawned
    private Map<Stats, Range<Integer>> ev; // 0-255 the pokemon can have a total of 510
    private Range<Integer> level;
    private Map<Stats, Range<Integer>> actualStats;
    private Map<Stats, Range<Integer>> tempStatsBonus;
    private Map<Integer, String> levelMovesLearn;
    private List<Move> actualMoves = new ArrayList<>();
    @Getter
    @Setter
    private String levelUpCurve; // https://m.bulbapedia.bulbagarden.net/wiki/Experience
    private Map<Stats, Integer> givesEv;
    private Range<Integer> exp;
    @Getter
    @Setter
    private int pokedexNumber;
    @Getter
    @Setter
    private int weight;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Type type1;
    @Setter
    private Optional<Type> type2;
    @Getter
    @Setter
    private int captureRate;
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private Optional<String> holdingObject;
    @Getter
    @Setter
    private String abilityName;
    @Getter
    @Setter
    private Optional<StatusCondition> statusCondition;
    private Map<StatusCondition, Integer> statusDuration;
    @Getter
    @Setter
    private boolean hasToLearnMove;
    @Getter
    @Setter
    private Optional<Move> newMoveToLearn = Optional.empty();

    @Getter
    @Setter
    private Optional<Image> spriteFront;
    @Getter
    @Setter
    private Optional<Image> spriteBack;

    /**
     * The construct takes the blueprint and adds the random values.
     * 
     * @param pokemonBlueprint the blueprint of the pokemon you want to create
     */
    public PokemonImpl(final PokemonBlueprint pokemonBlueprint) throws InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        this.moveFactoryInstance = MoveFactoryImpl.getInstance(MoveFactoryImpl.class);
        this.baseStats = new EnumMap<>(Stats.class);
        this.baseStats.putAll(pokemonBlueprint.stats());
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
        this.statusDuration = new EnumMap<>(StatusCondition.class);
    }

    private void generateivs() {
        final int maxIv = 32;
        this.iv = new EnumMap<>(Stats.class);
        for (final Stats stat : Stats.values()) {
            this.iv.put(stat, random.nextInt(maxIv)); // iv tra 0 e 31
        }
    }

    private void generateEvs() {
        final int maxEv = 252;
        final int minEv = 0;
        this.ev = new EnumMap<>(Stats.class);
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
        final int defaultTempStat = 0;
        this.tempStatsBonus = new EnumMap<>(Stats.class);
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
        for (final Map.Entry<String, String> entry : learnableMoves.entrySet()) {
            this.levelMovesLearn.put(Integer.parseInt(entry.getKey()), entry.getValue());
        }
    }

    private void initActualMoves() {
        final int firstMoveKey = 1;
        for (final Map.Entry<Integer, String> entry : this.levelMovesLearn.entrySet()) {
            if (entry.getKey() == firstMoveKey) {
                this.actualMoves.add(moveFactoryInstance.moveFromName(entry.getValue()));
            }
        }
    }

    private void calculateActualStats() {
        final int constAdder = 5;
        final double positiveMultiplier = 1.1;
        final double negativeMultiplier = 0.9;
        final int maxStat = 252;
        actualStats = new EnumMap<>(Stats.class);

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
                                    + ev.get(stat).getCurrentValue() / 4
                                            * level.getCurrentValue())
                                    / 100.0))
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
            newRequiredExp = (int) ((constMultiplier * Math.pow(currentLevel, 3))
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
        for (final Map.Entry<Stats, Integer> entry : increaseEv.entrySet()) {
            final Stats stat = entry.getKey();
            final int increment = entry.getValue();
            if (this.totalUsedEv + increment < maxTotalEv) {
                this.ev.get(stat).increment(increment);
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

    @Override
    public void setStatusDuration(final Map<StatusCondition, Integer> newVal) {
        this.statusDuration = new EnumMap<>(StatusCondition.class);
    }

    @Override
    public Map<StatusCondition, Integer> getStatusDuration() {
        return new EnumMap<>(this.statusDuration);
    }

    @Override
    public Map<Stats, Integer> getIv() {
        return iv == null ? null : new EnumMap<>(iv);
    }

    @Override
    public void setIv(final Map<Stats, Integer> iv) {
        this.iv = iv == null ? null : new EnumMap<>(iv);
    }

    @Override
    public Map<Stats, Range<Integer>> getEv() {
        return new EnumMap<>(this.ev);
    }

    @Override
    public void setEv(final Map<Stats, Range<Integer>> ev) {
        if (ev == null) {
            this.ev = null;
        } else {
            final Map<Stats, Range<Integer>> copy = new EnumMap<>(Stats.class);
            for (final Map.Entry<Stats, Range<Integer>> entry : ev.entrySet()) {
                copy.put(entry.getKey(), entry.getValue().copyOf());
            }
            this.ev = new EnumMap<>(copy);
        }
    }

    @Override
    public Range<Integer> getLevel() {
        return this.level == null ? null : this.level.copyOf();
    }

    @Override
    public void setLevel(final Range<Integer> level) {
        this.level = level == null ? null : level.copyOf();
    }

    @Override
    public Map<Stats, Range<Integer>> getActualStats() {
        return new EnumMap<>(this.actualStats);
    }

    @Override
    public void setActualStats(final Map<Stats, Range<Integer>> newVal) {
        this.actualStats = new EnumMap<>(newVal);
    }

    @Override
    public Map<Stats, Range<Integer>> getTempStatsBonus() {
        return new EnumMap<>(this.tempStatsBonus);
    }

    @Override
    public void setTempStatsBonus(final Map<Stats, Range<Integer>> tempStatsBonus) {
        this.tempStatsBonus = new EnumMap<>(tempStatsBonus);
    }

    @Override
    public Map<Integer, String> getLevelMovesLearn() {
        return levelMovesLearn == null ? null : new HashMap<>(levelMovesLearn);
    }

    @Override
    public void setLevelMovesLearn(final Map<Integer, String> levelMovesLearn) {
        this.levelMovesLearn = levelMovesLearn == null ? null : new HashMap<>(levelMovesLearn);
    }

    @Override
    public List<Move> getActualMoves() {
        return actualMoves == null ? null : new ArrayList<>(actualMoves);
    }

    @Override
    public void setActualMoves(final List<Move> actualMoves) {
        this.actualMoves = actualMoves == null ? null : new ArrayList<>(actualMoves);

    }

    @Override
    public Range<Integer> getExp() {
        return exp == null ? null : exp.copyOf();
    }

    @Override
    public void setExp(final Range<Integer> exp) {
        this.exp = exp == null ? null : exp.copyOf();
    }

    @Override
    public Map<Stats, Integer> getGivesEv() {
        return givesEv == null ? null : new EnumMap<>(givesEv);
    }

    @Override
    public void setGivesEv(final Map<Stats, Integer> givesEv) {
        this.givesEv = givesEv == null ? null : new EnumMap<>(givesEv);
    }

    @Override
    public Map<Stats, Integer> getBaseStats() {
        return baseStats == null ? null : new EnumMap<>(baseStats);
    }

    @Override
    public void setBaseStats(final Map<Stats, Integer> baseStats) {
        this.baseStats = baseStats == null ? null : new EnumMap<>(baseStats);
    }
}
