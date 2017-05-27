package architecture.augmentations.weapons;

public class Sword extends Weapon{
    public Sword() {
        this(1);
    }

    public Sword(int level) {
        super(level);

        initializeBoosts();

        for (int i = 0; i < totalBoosts.length; i++) {
            totalBoosts[i] = 10;
        }
    }
}
