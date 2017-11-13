/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.ui;

import com.danimaetrix.library.userIO.ColorIO;
import com.danimaetrix.library.userIO.UserIo;
import com.dm.vendingmashine.dto.Money;
import com.dm.vendingmashine.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Danimaetrix
 */
public class TextViewImpl implements View {

    ColorIO c = new ColorIO();

    UserIo io;

    public TextViewImpl(UserIo io) {
        this.io = io;
    }

    @Override
    public void showChange(Money userMoney) {
        io.print("");
        io.print("Total change: $" + userMoney.getTotalmoney().toString());
        io.print("Quarters: " + userMoney.getQuarters());
        io.print("Dimes: " + userMoney.getDimes());
        io.print("Nickels: " + userMoney.getNickels());
        io.print("Pennies: " + userMoney.getPennies());
        waitOnUser();
    }

    @Override
    public void generateMenu(List<String[]> pricing, String realVersion) {

        io.print("");
        io.print("WELCOME TO THE\n"
                + "REALISTIC\n"
                + "VENDING MACHINE SIMULATOR");
        io.printx("Realism: ", ColorIO.WHITE);
        io.print(realVersion, ColorIO.YELLOW);
        io.print("------------------------------------");
        for (int i = 0; i < pricing.size(); i++) {
            io.print(stShort(Integer.toString(i + 4) + ". ", 4) + stShort(pricing.get(i)[0], 15)
                    + "$" + stShort(pricing.get(i)[1], 6)
                    + pricing.get(i)[2], ColorIO.CYAN);
        }
        io.print("------------------------------------");
        io.print("0. Get change and leave");
        io.print("1. Add money");
        io.print("2. View jammed items");
        io.print("3. Shake the machine");
        io.print("");

    }

    @Override
    public void showJammedItems(Map<String, List<Product>> itemMap) {
        cls();
        jammedItemsBanner();
        itemMap.forEach((k, v) -> {
            io.print(k + ": " + v.size());
        });
        waitOnUser();
    }

    // Short method to shorten and extend string to length l, using "..." to truncate 
    // and whitespace to extend.  Used to format fields for output to user in printAllTitles
    @Override
    public String stShort(String string, int l) {
        //int l = 18;
        char[] newstr = new char[l];

        if (string.length() > l) {
            char[] str = string.toCharArray();
            System.arraycopy(str, 0, newstr, 0, l - 3);

            for (int i = 0; i < 3; i++) {
                newstr[(l - 4) + i] = '.';
            }

            String fstring = new String(newstr);
            return fstring;

        } else {
            char[] str = string.toCharArray();
            System.arraycopy(str, 0, newstr, 0, str.length);

            for (int i = str.length + 1; i < l; i++) {
                newstr[i] = ' ';

            }
            String fstring = new String(newstr);
            return fstring;
        }
    }

    @Override
    public String moneyToString(Money userMoney) {
        BigDecimal m = userMoney.getTotalmoney();

        if (m.scale() > 2) {
            return m.toString();

        } else {
            return m.setScale(2, RoundingMode.HALF_UP).toString();
        }
    }

    @Override
    public void showTheProduct(List<Product> productList) {
        io.print("");
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            io.printx("Congrats on your brand new ", ColorIO.RESET);
            io.print(p.getProductName(), c.getRandomColor());
            io.print("Please enjoy by: "
                    + p.getBestBy().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    + " for maximum freshness!");
            io.print(p.getMessage());
            io.print("");
        }
    }

    @Override
    public Money userAddMoney(Money userMoney) {
        userAddMoneyBanner();
        io.printx("You currently have: $", ColorIO.RESET);
        io.print("$"+moneyToString(userMoney), ColorIO.GREEN);
        BigDecimal added = io.readBigDecimal("How much would you like to add ($)? ", "0", "1e100");

        userMoney.setTotalmoney(userMoney.getTotalmoney().add(added));
        userMoney.breakMoney();

        return userMoney;
    }

    @Override
    public int getUserDrinkSelection(Money userMoney, int range) {
        io.printx("You currently have: ", ColorIO.RESET);
        io.print("$"+moneyToString(userMoney), ColorIO.GREEN);
        return io.readInt("Please select an option: ", 0, range + 4);
    }

    @Override
    public void machineJamMessage() {
        io.print("");
        io.print("Oh no! The machine jammed.  You can "
                + "\ntry to shake it loose, but beware.");
        io.print("Sometimes accidents happen.... ", ColorIO.PURPLE);
    }

    @Override
    public void shakeMachineResults(List<Product> products) {
        io.print("");
        if (!products.isEmpty()) {
            io.print("Congratulations! Your shake retrieved items!");
            showTheProduct(products);
        } else {
            io.print("Too bad! Maybe next time...");
        }
    }

    @Override
    public void deathMessage(String e) {
        io.print("");
        io.print("YOU  ARE  DEAD", ColorIO.RED);
        io.print("\nMaybe you should have read the sticker"
                + "\nthat says not the shake the machine...");
        waitOnUser();
        io.print("Oh, and your money is ours now! :) :) :)", ColorIO.GREEN);
    }

    public void jammedItemsBanner() {
        io.print("\n");
        io.print(" *** Jammed Items ***");
    }

    @Override
    public void userAddMoneyBanner() {
        io.print("\n");
        io.print(" *** Add Money ***");
    }

    @Override
    public void insufficientFundsBanner() {
        io.print(" *** Insufficient Funds... add money ***");
    }

    @Override
    public void soldOutBanner() {
        io.print(" *** Sorry, sold out! Please try another... ***");

    }

    @Override
    public void drinkSelectionBanner() {
        io.print("\n");
        io.print(" *** Drink Selection ***");
    }

    // Additional user messages
    @Override
    public void showExitMessage() {
        io.print("\n");
        io.print("GOODBYE!!");
        io.print("");
    }

    // Pause for user
    @Override
    public void waitOnUser() {
        io.readLine("\nPress enter to continue...");
    }

    @Override
    public void cls() {
        for (int i = 0; i < 1000; i++) {
            io.print("\n");
        }
    }

    @Override
    public void displayExceptionMessage(String msg) {
        io.print("");
        io.print(msg);
    }

}
