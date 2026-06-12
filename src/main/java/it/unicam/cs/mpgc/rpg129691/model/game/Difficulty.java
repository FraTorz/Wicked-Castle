package it.unicam.cs.mpgc.rpg129691.model.game;

/**
 * Definisce i livelli di difficoltà disponibili nel gioco.
 *
 * Ogni livello configura:
 * <ul>
 *     <li>dimensione della mappa</li>
 *     <li>probabilità di generazione delle stanze</li>
 *     <li>quantità di cura ottenibile</li>
 *     <li>danni inflitti dalle trappole</li>
 *     <li>distanza minima dell'uscita dalla posizione iniziale</li>
 * </ul>
 */
public enum Difficulty {

    EASY(
            5,
            20,
            20,
            20,
            20,
            20,
            20,
            10,
            2
    ),

    MEDIUM(
            7,
            15,
            20,
            20,
            20,
            25,
            20,
            15,
            3
    ),

    HARD(
            9,
            10,
            20,
            20,
            20,
            30,
            20,
            20,
            4
    );

    private final int mapSize;
    private final int emptyProbability;
    private final int healingProbability;
    private final int trapProbability;
    private final int treasureProbability;
    private final int enemyProbability;
    private final int healingAmount;
    private final int trapDamage;
    private final int minExitDistance;

    /**
     * Crea una configurazione di difficoltà.
     *
     * Le probabilità delle varie tipologie di stanza devono
     * sempre sommare a 100.
     *
     * @throws IllegalArgumentException se la somma delle probabilità è diversa da 100
     */
    Difficulty(int mapSize, int emptyProbability, int healingProbability,
               int trapProbability, int treasureProbability, int enemyProbability,
               int healingAmount, int trapDamage, int minExitDistance) {
        this.mapSize = mapSize;
        this.emptyProbability = emptyProbability;
        this.healingProbability = healingProbability;
        this.trapProbability = trapProbability;
        this.treasureProbability = treasureProbability;
        this.enemyProbability = enemyProbability;
        this.healingAmount = healingAmount;
        this.trapDamage = trapDamage;
        this.minExitDistance = minExitDistance;
        validateProbabilities(emptyProbability, healingProbability, trapProbability,
                treasureProbability, enemyProbability);
    }

    private static void validateProbabilities(int empty, int healing, int trap, int treasure, int enemy) {
        int total = empty + healing + trap + treasure + enemy;
        if (total != 100) throw new IllegalArgumentException("The probabilities must sum to 100");
    }

    public int getMapSize(){
        return mapSize;
    }
    public int getEmptyProbability(){
        return emptyProbability;
    }
    public int getHealingProbability(){
        return healingProbability;
    }
    public int getTrapProbability(){
        return trapProbability;
    }
    public int getTreasureProbability(){
        return treasureProbability;
    }
    public int getEnemyProbability(){
        return enemyProbability;
    }
    public int getHealingAmount(){
        return healingAmount;
    }
    public int getTrapDamage(){
        return trapDamage;
    }
    public int getMinExitDistance(){
        return minExitDistance;
    }
}