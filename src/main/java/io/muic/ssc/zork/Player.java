package io.muic.ssc.zork;

import io.muic.ssc.zork.item.ItemFactory;
import io.muic.ssc.zork.item.ItemType;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
    private static final Random RANDOM = new Random();
    private static final int MAX_HEALTH = 100;
    private static final int MAX_STRENGTH = 100;
    private final String name;
    private int health, strength;
    private boolean alive;
    private Set<String> inventory;

    public Player(String name){
        this.name = name;
        this.health = 50;
        this.strength = 10;
        this.alive = true;
        this.inventory = new HashSet<>();

        System.out.printf("Welcome %s !\n", name);
    }

    public Player(Player player){
        this.name = player.name;
        this.health = player.health;
        this.strength = player.strength;
        this.alive = player.alive;
        this.inventory = new HashSet<>();
        this.inventory.addAll(player.inventory);
    }

    public void printStat(){
        System.out.printf("Player: %s\n", name);
        System.out.printf("HP: %d/%d\t\tATK: %d/%d\n", health, MAX_HEALTH, strength, MAX_STRENGTH);
        System.out.println("Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("\t[ ]");
            return;
        }
        for(String itemName: inventory) {
            System.out.printf("\t[%s]", itemName);
        }
        System.out.println();
    }

    public boolean isAlive() {
        return alive;
    }

    public String getName() {
        return name;
    }

    public void printHealth() {
        System.out.printf("Player %s's HP: %d/%d\n", name, health, MAX_HEALTH);
    }

    public int attack() {
        return RANDOM.nextInt(strength);
    }

    public Set<String> getInventory() {
        return inventory;
    }

    public void gainEnergy(int energy){
        health += energy;
        if (health >= MAX_HEALTH) {
            health = MAX_HEALTH;
        }
    }

    public void receiveDamage(int damage){
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public void gainPower(int power){
        strength += power;
    }

    public void gainItem(String itemName) {
        inventory.add(itemName);
        ItemType itemType = ItemFactory.getItemType(itemName);
        if(itemType.isWeapon()) {
            health += itemType.getHealth();
            strength += itemType.getStrength();
        }
        System.out.printf("Player %s gains [%s]\n", name, itemName);
    }

    public void dropItem(String itemName) {
        inventory.remove(itemName);
        ItemType itemType = ItemFactory.getItemType(itemName);
        if(itemType.isWeapon()) {
            health -= itemType.getHealth();
            strength -= itemType.getStrength();
            System.out.printf("Player %s drops [%s]\n", name, itemName);
        } else if(itemType.isFood()) {
            System.out.printf("Player %s eats [%s]\n", name, itemName);
        }
    }
}
