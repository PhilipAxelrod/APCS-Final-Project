package architecture.characters;

public class Stats {
    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public void setACC(int ACC) {
        this.ACC = ACC;
    }

    public void setAVO(int AVO) {
        this.AVO = AVO;
    }

    public void setCRIT(int CRIT) {
        this.CRIT = CRIT;
    }

    public void setCRITAVO(int CRITAVO) {
        this.CRITAVO = CRITAVO;
    }

    int HP;
    int mana;
    int ATK;
    int DEF;
    int ACC;
    int AVO;
    int CRIT;
    int CRITAVO;

    public int getHP() {
        return HP;
    }

    public int getMana() {
        return mana;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public int getACC() {
        return ACC;
    }

    public int getAVO() {
        return AVO;
    }

    public int getCRIT() {
        return CRIT;
    }

    public int getCRITAVO() {
        return CRITAVO;
    }

    public Stats(int HP, int mana, int ATK, int DEF, int ACC, int AVO, int CRIT, int CRITAVO) {
        this.HP = HP;
        this.mana = mana;
        this.ATK = ATK;
        this.DEF = DEF;
        this.ACC = ACC;
        this.AVO = AVO;
        this.CRIT = CRIT;
        this.CRITAVO = CRITAVO;
    }

    public Stats() {
        this.HP = 0;
        this.mana = 0;
        this.ATK = 0;
        this.DEF = 0;
        this.ACC = 0;
        this.AVO = 0;
        this.CRIT = 0;
        this.CRITAVO = 0;
    }

    public void incrementAll() {
        this.HP += 1;
        this.mana += 1;
        this.ATK += 1;
        this.DEF += 1;
        this.ACC += 1;
        this.AVO += 1;
        this.CRIT += 1;
        this.CRITAVO += 1;
    }

    public void setAll(int toSet) {
        this.HP = toSet;
        this.mana = toSet;
        this.ATK = toSet;
        this.DEF = toSet;
        this.ACC = toSet;
        this.AVO = toSet;
        this.CRIT = toSet;
        this.CRITAVO = toSet;
    }

    //    public Stats(
//            int HP,
//            int mana,
//            int ATK,
//            int DEF,
//            int ACC,
//            int AVO,
//            int CRIT,
//            int CRITAVO) {
//
//    }
}
