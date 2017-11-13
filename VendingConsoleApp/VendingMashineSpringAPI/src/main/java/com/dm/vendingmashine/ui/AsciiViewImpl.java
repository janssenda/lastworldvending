/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.vendingmashine.ui;

import com.danimaetrix.library.userIO.UserIo;
import com.danimaetrix.library.userIO.UserIoConsoleImpl;
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
public class AsciiViewImpl implements View {

    UserIo io;

    public AsciiViewImpl(UserIo io) {
        this.io = new UserIoConsoleImpl();
    }

    @Override
    public void showChange(Money userMoney) {

        System.out.println("");
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
        io.print(" /xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\\");
        io.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        io.print("xxxx" + stShort("      Coke - Sprite - Dasani ", 34) + "xxxxxxxx");
        io.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        io.print("xxxx/" + stShort("", 32) + "\\xxxxxxxx");
        io.print("xxxx" + stShort("            -WELCOME-", 33) + " x[####]x");
        io.print("xxxx" + stShort("        TO THE REALISTIC", 33) + " xxxxxxxx");
        io.print("xxxx" + stShort("    VENDING MACHINE SIMULATOR", 33) + " xxxxxxxx");
        io.print("xxxx" + stShort("", 33) + " xxxxxxxx");

        for (int i = 0; i < pricing.size(); i++) {
            io.print(stShort("xxxx", 8) 
                    + stShort(Integer.toString(i + 2)+".",5) 
                    + stShort(pricing.get(i)[0], 15)
                    + "$" + stShort(pricing.get(i)[1], 6)
                    + stShort(pricing.get(i)[2], 7) + "[ oooo ]");
        }
        io.print(stShort("xxxx", 8) + "-----------------------" + stShort("", 8) + "xxxxxxxx");
        io.print(stShort("xxxx", 8) + "0.  Get change and leave" + stShort("", 7) + "xxxxxxxx");
        io.print(stShort("xxxx", 8) + "1.  Add money" + stShort("", 18) + "x[    ]x");

        io.print("xxxx\\" + stShort("", 32) + "/xxxxxxxx");
        io.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        io.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n"
                + "xxx=========xxx|\\	    /|xxx=========xxx\n"
                + "xxx=========xxx|" + stShort("  < PUSH >", 14) + "|xxx=========xxx\n"
                + "xxx=========xxx|/	    \\|xxx=========xxx\n"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n"
                + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n"
                + "xxxx					 xxxx\n"
                + " xx		   			  xx");

        io.print("---------------------------------------------");

        io.print("");

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

    
    public void showTheProduct(Product p) {
        io.print("");
        displayCocaColaBanner();
        io.print("Congrats " + p.getMessage() + " on your brand new " + p.getProductName()+"!!!");
        io.print("Please enjoy by: "
                + p.getBestBy().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                + " for maximum freshness!");
    }

    @Override
    public Money userAddMoney(Money userMoney) {
        userAddMoneyBanner();
        io.print("You currently have: $" + moneyToString(userMoney));
        BigDecimal added = io.readBigDecimal("How much would you like to add ($)? ");

        userMoney.setTotalmoney(userMoney.getTotalmoney().add(added));
        userMoney.breakMoney();

        return userMoney;
    }

    @Override
    public int getUserDrinkSelection(Money userMoney, int range) {
        io.print("You currently have: $" + moneyToString(userMoney));
        return io.readInt("Please select an option: ", 0, range + 2);
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
        io.print("\n");

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
        io.print("\n");
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
        io.print("\n");
        io.print(msg);
        waitOnUser();
    }
    
    public void displayCocaColaBanner(){
        io.print("                                               .,.            k\n" +
"   aAn                  ,       aAn ,dMMMAn,  an.   JM\n" +
"  dMMMk  aAn   aAn   an,A  jMF dMMMk^\"  \"^YMMVP\"P.aAV\"\n" +
" jMT \"M jM^Mk jM^Mk jM^MM ,MF jM^d\"M       \"jMMMMMP^\n" +
" MM   ' MF AP MF YP MF AP     MM \"^\"        MM ~~~\n" +
" MM     M| M| M| ' .M| M|     MM            MM       ,\n" +
" MM     Mk Vk Mk .aMMk Ml ,   MM       aAn  MM    an,A\n" +
" MM     YMUMP YMUMP YMUMk,N   MM  ,An jM^MM MM   jMMMM\n" +
" MM      ^W^   ^W^   YP\"VP\"   MM  P\"M MF MP MM   MF AP\n" +
" MMl         .,oammmo,.       MMl ` M M| M| MM   M| M|\n" +
" TMMk.  .,aAMMMMMMMMMMMMo,    TMMk.jM Mk Mk MM   Mk Ml ,\n" +
"  YMMMMMMMMP^\"~     \"~^VMMt    YMMMMP YMMMP VYk jMMMMk,M\n" +
"   ^YMV^~\"              VP      ^YMP   ^W^   ^MMP YP\"VP\"\n\n");
    }

    @Override
    public void showJammedItems(Map<String, List<Product>> itemMap) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showTheProduct(List<Product> productList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shakeMachineResults(List<Product> products) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deathMessage(String e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void machineJamMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
