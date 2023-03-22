package Game;

public class Aircraft extends Item {
    private int bullets = 3;
    private int cureKits = 3;
    private int chickenCount;
    public Aircraft(){
        super(200);
        chickenCount = 0;
    }

    public void useCureKit(){
        this.setHp(this.getHp() + 50);
        decreaseCureKits(1);
    }


    //getter setters
    public int getBullets(){return bullets;}
    public void setBullets(int bullets) {this.bullets = bullets;}
    public void increaseBullets(int bullet) {
        this.bullets += bullet;
    }
    public void decreaseBullets(int bullet) {
        this.bullets -= bullet;
    }

    public int getCureKits() {return cureKits;}
    public void setCureKits(int cureKits) {this.cureKits = cureKits;}
    public void increaseCureKits(int cureKits) {
        this.cureKits += cureKits;
    }
    public void decreaseCureKits(int cureKits) {this.cureKits -= cureKits;}

    public int getChickenCount() {return chickenCount;}
    public void setChickenCount(int chickenCount) {this.chickenCount = chickenCount;}

}
