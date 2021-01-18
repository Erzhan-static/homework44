package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int[] heroesHealth = {260, 250, 240, 200, 220, 300};
    public static int[] heroesDamage = {20, 25, 15,0, 10, 5};
    public static String[] heroesAttackType = {"physical", "magical", "kinetic", "Medic", "Berserk","Golem"};

    public static void main(String[] args) {
        // создание рпг игры
        printstatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length);//0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefence);
    }


    public static void round() {
        changeBossDefence();
        heroesHit();
        if (bossHealth > 0) {
            bossHits();
            if (bossHealth<0){
                bossHealth=bossHealth=0;
            }
        }

        if (heroesHealth[5]>0){
            golem();
        }
        medic();
        if (heroesHealth[4]>0){
            berserk();
        }
        printstatistics();
    }


    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random r = new Random();
                    int coeff = r.nextInt(6) + 2;
                    System.out.println("Critical Damage: " +
                            heroesDamage[i] * coeff);
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        Random r = new Random();
        int chance = r.nextInt(3);// 0,1,2
        double coeff = Math.random(); //какое то число от 0 до 1 например 0.378
        if (chance == 0) {
            System.out.println("Boss became weaker " + (int) (bossDamage * coeff));
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (chance == 0) {
                    if (heroesHealth[i] - (int) (bossDamage * coeff) < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - (int) (bossDamage * coeff);

                    }
                } else {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }

                }
            }
        }
    }


    public static void printstatistics() {
        System.out.println("____________________");
        System.out.println("Boss health " + bossHealth);
        for (int i = 0; i < heroesAttackType.length; i++) {

            System.out.println(heroesAttackType[i]
                    + " health:" + heroesHealth[i]);

        }
        System.out.println("____________________");
    }


    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void medic(){
        int health = 10;
        Random random = new Random();
        int chance = random.nextInt(heroesDamage.length);
        for (int i = 0; i <heroesDamage.length ; i++) {
            if (heroesHealth[chance]>0&&heroesHealth[chance]<100&&heroesHealth[i]>0){
                heroesHealth[chance]=heroesHealth[chance]+health;
            }
        }
        System.out.println("медик вылечил " +heroesAttackType[chance]);
    }

    public static void golem(){
        int однаПятаяОтУронаБосса=bossDamage*4/5;
        Random r = new Random();
        boolean golemSave = r.nextBoolean();
        if (golemSave){
            for (int i = 0; i <heroesHealth.length ; i++) {
                heroesHealth[i]=heroesHealth[i]+однаПятаяОтУронаБосса;
            }
        }
        System.out.println("голем работает");
    }

    public static void berserk(){
        int дамагБерсерка= bossDamage+heroesDamage[4];

        Random r = new Random();
        boolean berserkCrit = r.nextBoolean();
        if (berserkCrit){
            heroesHealth[4]=heroesHealth[4]+bossDamage;
            bossHealth=bossHealth-дамагБерсерка;
            System.out.println("Berserk works");
        }
    }
}