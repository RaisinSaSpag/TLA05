/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.enola_turnbaseprogram;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.InputMismatchException;
public class Enola_turnBaseProgram {
    
    private Stack<Integer> lastBotHP = new Stack<>();
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    private int playerHP = 100;
    private int botHP = 100;
    private int playerMinDmg = 5;
    private int playerMaxDmg = 10;
    private int botMinDmg = 2;
    private int botMaxDmg = 5;
    private int turnCount = 1;
    private boolean isBotStunned = false;
    
    public static void main(String[] args) {
        Enola_turnBaseProgram game = new Enola_turnBaseProgram();
        game.startGame();
    }

    public void startGame() {
        System.out.println("=== Turn Based RPG ===");
        System.out.println("Player and Bot start with 100 HP");
        System.out.println("Bot's Passive: 25% chance to avoid damage and restore HP\n");

        while (playerHP > 0 && botHP > 0) {
            printTurnStatus();
            
            if (isPlayerTurn()) {
                handlePlayerTurn();
            } else {
                handleBotTurn();
            }

            turnCount++;
            System.out.println();
        }

        printGameResult();
    }

    private void printTurnStatus() {
        System.out.println("------ Turn " + turnCount + " ------");
        System.out.println("Player HP: " + playerHP);
        System.out.println("Bot HP: " + botHP);
    }

    private boolean isPlayerTurn() {
        return turnCount % 2 != 0; // Player goes on odd turns
    }

    private void handlePlayerTurn() {
        System.out.println("\nYour turn! Choose an action:");
        System.out.println("1. Attack");
        System.out.println("2. Stun (skip bot's next turn)");
        System.out.println("3. Skip turn");
        System.out.print("Enter choice: ");

        int choice = getValidChoice(1, 3);

        switch (choice) {
            case 1 -> attackBot();
            case 2 -> stunBot();
            case 3 -> skipTurn();
        }
    }

    private void handleBotTurn() {
        if (isBotStunned) {
            System.out.println("The bot is stunned and skips its turn!");
            isBotStunned = false;
            return;
        }

        lastBotHP.push(botHP); // Save HP before potential restoration

        // 25% chance to activate passive
        if (random.nextInt(4) == 0) {
            activateBotPassive();
        } else {
            attackPlayer();
        }
    }

    private void attackBot() {
        int damage = random.nextInt(playerMaxDmg - playerMinDmg + 1) + playerMinDmg;
        botHP = Math.max(0, botHP - damage);
        System.out.println("You attack the bot for " + damage + " damage!");
    }

    private void stunBot() {
        isBotStunned = true;
        System.out.println("You stun the bot! It will skip its next turn.");
    }

    private void skipTurn() {
        System.out.println("You choose to skip your turn.");
    }

    private void attackPlayer() {
        int damage = random.nextInt(botMaxDmg - botMinDmg + 1) + botMinDmg;
        playerHP = Math.max(0, playerHP - damage);
        System.out.println("The bot attacks you for " + damage + " damage!");
    }

    private void activateBotPassive() {
        int previousHP = lastBotHP.pop();
        System.out.println("\n=== BOT'S PASSIVE ACTIVATED ===");
        System.out.println("The bot avoided your attack!");
        System.out.println("HP restored from " + botHP + " to " + previousHP);
        botHP = previousHP;
    }

    private int getValidChoice(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.print("Invalid choice. Enter " + min + "-" + max + ": ");
            } catch (InputMismatchException e) {
                System.out.print("Please enter a number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private void printGameResult() {
        if (playerHP <= 0 && botHP <= 0) {
            System.out.println("It's a draw!");
        } else if (playerHP <= 0) {
            System.out.println("You lost! The bot wins!");
        } else {
            System.out.println("You won! The bot is defeated!");
        }
    }
}

    

