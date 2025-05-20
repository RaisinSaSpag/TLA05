/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.enola_turnbaseprogram;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
public class Enola_turnBaseProgram {

    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Stack<Integer> lastHP = new Stack<>();

        int playerHP = 100;
        int playerMinDmg = 5;
        int playerMaxDmg = 10;

        int botHP = 100;
        int botMinDmg = 5;
        int botMaxDmg = 10;

        boolean botStunned = false;

        int turnCount = 1;

        System.out.println("=== Turn Based Game ===");
        System.out.println("The Bot and The Player will start with 100HP");
        System.out.println("Bot's Passive: 25% chance to completely avoid damage and restore previous HP");
        System.out.println("Player Skill: Stun (25% chance to skip bot's next turn)\n");

        while (playerHP > 0 && botHP > 0) {
            System.out.println("------ Turn " + turnCount + " ------");
            System.out.println("Your HP: " + playerHP + " | Bot HP: " + botHP);

            if (oddOrEven(turnCount)) {
                // Bot's turn
                if (botStunned) {
                    System.out.println("Bot is stunned and skips its turn!");
                    botStunned = false;
                } else {
                    int botDmg = random.nextInt(botMaxDmg - botMinDmg + 1) + botMinDmg;
                    playerHP -= botDmg;
                    System.out.println("Bot attacks you for " + botDmg + " damage!");
                }
            } else {
                // Player's turn
                System.out.println("Your turn! Choose an action:");
                System.out.println("1. Attack");
                System.out.println("2. Stun");
                System.out.println("3. Skip turn");
                System.out.println("4. Exit game");

                int choice = scanner.nextInt();
                if (choice == 1) {
                    int playerDmg = random.nextInt(playerMaxDmg - playerMinDmg + 1) + playerMinDmg;

                    lastHP.push(botHP);  // Save current HP before attacking

                    int chance = random.nextInt(100); // 0 to 99
                    if (chance < 25) {
                        int previousHP = lastHP.pop();
                        System.out.println("\n====================================");
                        System.out.println("|| BOT'S PASSIVE ABILITY ACTIVATED ||");
                        System.out.println("====================================");
                        System.out.println("The bot shimmered with energy as it avoided your attack!");
                        System.out.println("HP restored from " + botHP + " to " + previousHP + "!");
                        System.out.println("Your " + playerDmg + " damage was completely nullified!");
                        System.out.println("====================================\n");
                        botHP = previousHP;
                    } else {
                        botHP -= playerDmg;
                        lastHP.pop(); // Discard the saved HP since it wasn't used
                        System.out.println("You attack the bot for " + playerDmg + " damage!");
                    }

                } else if (choice == 2) {
                    int stunChance = random.nextInt(100);
                    if (stunChance < 25) {
                        botStunned = true;
                        System.out.println("You successfully stunned the bot! It will skip its next turn.");
                    } else {
                        System.out.println("Stun failed! Bot resists the effect.");
                    }
                } else if (choice == 3) {
                    System.out.println("You chose to skip your turn.");
                } else if (choice == 4) {
                    System.out.println("You exited the game.");
                    return;
                } else {
                    System.out.println("Invalid choice! You lose your turn.");
                }
            }

            turnCount++;
            System.out.println();
        }

        // Game over message
        if (playerHP <= 0 && botHP <= 0) {
            System.out.println("It's a draw!");
        } else if (playerHP <= 0) {
            System.out.println("You lost! Bot wins!");
        } else {
            System.out.println("You won! Bot is defeated!");
        }
    }

    // Returns true if turn is even (bot's turn)
    static boolean oddOrEven(int i) {
        return i % 2 == 0;
    }
}