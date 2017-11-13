/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danimaetrix.library.userIO;

import java.util.Scanner;
import java.io.Console;
import java.math.BigDecimal;

/**
 *
 * @author danimaetrix
 */
public class UserIoConsoleImpl implements UserIo {

    private static final String INVALIDINPUT = "Invalid input";
    private static final String TRYAGAINMSG = "Please try again: ";

    private void ExceptionResponse() {
        print(INVALIDINPUT);
        printx(TRYAGAINMSG);
    }

//
//  _____      _       _   
// |  __ \    (_)     | |  
// | |__) | __ _ _ __ | |_ 
// |  ___/ '__| | '_ \| __|
// | |   | |  | | | | | |_ 
// |_|   |_|  |_|_| |_|\__|
//                         
//                         
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public void print(String msg, String nocolor) {
        System.out.println(msg);
    }

    @Override
    public void printx(String msg) {
        System.out.print(msg);
    }

    @Override
    public void printx(String msg, String nocolor) {
        System.out.print(msg);
    }

//  _____                _            _      _            
// |  __ \              | |          | |    (_)           
// | |__) |___  __ _  __| |  ______  | |     _ _ __   ___ 
// |  _  // _ \/ _` |/ _` | |______| | |    | | '_ \ / _ \
// | | \ \  __/ (_| | (_| |          | |____| | | | |  __/
// |_|  \_\___|\__,_|\__,_|          |______|_|_| |_|\___|
//                                                        
//                                                        
    @Override
    public String readLine() {
        return lineReader();
    }

    @Override
    public String readLine(String msg) {
        printx(msg + "");
        return lineReader();
    }

    @Override
    public String readLine(String msg, String nocolor) {
        printx(msg + "");
        return lineReader();
    }

//  _____                _            _____                                    _ 
// |  __ \              | |          |  __ \                                  | |
// | |__) |___  __ _  __| |  ______  | |__) |_ _ ___ _____      _____  _ __ __| |
// |  _  // _ \/ _` |/ _` | |______| |  ___/ _` / __/ __\ \ /\ / / _ \| '__/ _` |
// | | \ \  __/ (_| | (_| |          | |  | (_| \__ \__ \\ V  V / (_) | | | (_| |
// |_|  \_\___|\__,_|\__,_|          |_|   \__,_|___/___/ \_/\_/ \___/|_|  \__,_|
//                                                                               
//                                                                               
    @Override
    public String readPasswordLn() {
        return passReader();
    }

    @Override
    public String readPasswordLn(String msg) {
        printx(msg + "");
        return passReader();
    }

    @Override
    public String readPasswordLn(String msg, String nocolor) {
        printx(msg + "");
        return passReader();
    }

//  _____                _            _____              _     _      
// |  __ \              | |          |  __ \            | |   | |     
// | |__) |___  __ _  __| |  ______  | |  | | ___  _   _| |__ | | ___ 
// |  _  // _ \/ _` |/ _` | |______| | |  | |/ _ \| | | | '_ \| |/ _ \
// | | \ \  __/ (_| | (_| |          | |__| | (_) | |_| | |_) | |  __/
// |_|  \_\___|\__,_|\__,_|          |_____/ \___/ \__,_|_.__/|_|\___|
//                                                                    
    @Override
    public double readDouble() {
        double max = Double.MAX_VALUE;
        double min = -max;
        return userDoubleRecieve(min, max);
    }

    @Override
    public double readDouble(String msg) {
        printx(msg + "");
        double max = Double.MAX_VALUE;
        double min = -max;
        return userDoubleRecieve(min, max);
    }

    @Override
    public double readDouble(String msg, String nocolor) {
        printx(msg + "");
        double max = Double.MAX_VALUE;
        double min = -max;
        return userDoubleRecieve(min, max);
    }

    @Override
    public double readDouble(double min, double max) {
        return userDoubleRecieve(min, max);
    }

    @Override
    public double readDouble(String msg, double min, double max) {
        printx(msg + "");
        return userDoubleRecieve(min, max);
    }

    @Override
    public double readDouble(String msg, double min, double max, String nocolor) {
        printx(msg + "");
        return userDoubleRecieve(min, max);
    }

//
//  _____                _            _____       _   
// |  __ \              | |          |_   _|     | |  
// | |__) |___  __ _  __| |  ______    | |  _ __ | |_ 
// |  _  // _ \/ _` |/ _` | |______|   | | | '_ \| __|
// | | \ \  __/ (_| | (_| |           _| |_| | | | |_ 
// |_|  \_\___|\__,_|\__,_|          |_____|_| |_|\__|
//                                                    
//                                                   
    @Override
    public int readInt() {
        int max = Integer.MAX_VALUE;
        int min = -max;
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(String msg) {
        printx(msg + "");
        int max = Integer.MAX_VALUE;
        int min = -max;
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(String msg, String nocolor) {
        printx(msg + "");
        int max = Integer.MAX_VALUE;
        int min = -max;
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(int min, int max) {
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(String msg, int min, int max) {
        printx(msg + "");
        return userIntRecieve(min, max);
    }

    @Override
    public int readInt(String msg, int min, int max, String nocolor) {
        printx(msg + "");
        return userIntRecieve(min, max);
    }

//
//  _____                _                                                 
// |  __ \              | |              /\                                
// | |__) |___  __ _  __| |  ______     /  \   _ __  _____      _____ _ __ 
// |  _  // _ \/ _` |/ _` | |______|   / /\ \ | '_ \/ __\ \ /\ / / _ \ '__|
// | | \ \  __/ (_| | (_| |           / ____ \| | | \__ \\ V  V /  __/ |   
// |_|  \_\___|\__,_|\__,_|          /_/    \_\_| |_|___/ \_/\_/ \___|_|   
//                                                                         
//                                                                      
    @Override
    public boolean readAnswer() {
        return readAnswerBody("", ColorIO.RESET);
    }

    @Override
    public boolean readAnswer(String msg) {
        return readAnswerBody(msg, ColorIO.RESET);
    }

    @Override
    public boolean readAnswer(String msg, String nocolor) {
        return readAnswerBody(msg, nocolor);
    }

//  _____                _            ____  _       _____            _                 _ 
// |  __ \              | |          |  _ \(_)     |  __ \          (_)               | |
// | |__) |___  __ _  __| |  ______  | |_) |_  __ _| |  | | ___  ___ _ _ __ ___   __ _| |
// |  _  // _ \/ _` |/ _` | |______| |  _ <| |/ _` | |  | |/ _ \/ __| | '_ ` _ \ / _` | |
// | | \ \  __/ (_| | (_| |          | |_) | | (_| | |__| |  __/ (__| | | | | | | (_| | |
// |_|  \_\___|\__,_|\__,_|          |____/|_|\__, |_____/ \___|\___|_|_| |_| |_|\__,_|_|
//                                             __/ |                                     
//                                            |___/                                      
    @Override
    public BigDecimal readBigDecimal() {
        return userBDRecieve("-1e100", "1e100");
    }

    @Override
    public BigDecimal readBigDecimal(String msg) {
        printx(msg + "");
        return userBDRecieve("-1e100", "1e100");
    }

    @Override
    public BigDecimal readBigDecimal(String msg, String nocolor) {
        printx(msg + "");
        return userBDRecieve("-1e100", "1e100");
    }

    @Override
    public BigDecimal readBigDecimal(String msg, String min, String max) {
        printx(msg + "");
        return userBDRecieve(min, max);
    }

    @Override
    public BigDecimal readBigDecimal(String msg, String min, String max, String nocolor) {
        printx(msg + "");
        return userBDRecieve(min, max);
    }

//  _____      _            _         __  __      _   _               _     
// |  __ \    (_)          | |       |  \/  |    | | | |             | |    
// | |__) | __ ___   ____ _| |_ ___  | \  / | ___| |_| |__   ___   __| |___ 
// |  ___/ '__| \ \ / / _` | __/ _ \ | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
// | |   | |  | |\ V / (_| | ||  __/ | |  | |  __/ |_| | | | (_) | (_| \__ \
// |_|   |_|  |_| \_/ \__,_|\__\___| |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
//                                                                          
//                  
    private boolean readAnswerBody(String msg, String nocolor) {
        Scanner sc = new Scanner(System.in);
        boolean valid = false, answer = true;
        String userinput;

        printx(msg + "");
        while (valid == false) {
            try {

                userinput = sc.nextLine();

                switch (userinput.toLowerCase()) {
                    case "y":
                    case "yes":
                        answer = true;
                        valid = true;
                        break;
                    case "n":
                    case "no":
                        answer = false;
                        valid = true;
                        break;
                    default:
                        ExceptionResponse();
                        break;
                }

            } catch (Exception e) {
                ExceptionResponse();
                sc.next();

            }

        }
        return answer;
    }

    private double userDoubleRecieve(double min, double max) {
        //Safely returns a double within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;

        while (!valid) {
            try {
                double choice = sc.nextDouble();
                if (choice < min || choice > max) {
                    ExceptionResponse();
                } else {
                    return choice;
                }
            } catch (Exception e) {
                ExceptionResponse();
                sc.next();
            }

        }
        return 0;
    }

    private int userIntRecieve(int min, int max) {
        //Safely returns an Integer within range min to max
        Scanner sc = new Scanner(System.in);
        boolean valid = false;

        while (!valid) {
            try {
                int choice = sc.nextInt();

                if (choice < min || choice > max) {
                    ExceptionResponse();
                } else {
                    return choice;
                }
            } catch (Exception e) {
                ExceptionResponse();
                sc.next();

            }

        }
        return 0;
    }

    private BigDecimal userBDRecieve(String minStr, String maxStr) {
        Scanner sc = new Scanner(System.in);
        boolean valid = false;

        BigDecimal min = new BigDecimal(minStr);
        BigDecimal max = new BigDecimal(maxStr);

        while (!valid) {

            try {
                BigDecimal input = new BigDecimal(sc.nextLine());
                if (input.compareTo(min) < 0 || input.compareTo(max) > 0) {
                    ExceptionResponse();
                } else {
                    return input;
                }
            } catch (Exception e) {
                ExceptionResponse();
            }
        }
        return BigDecimal.ZERO;

    }

    private String lineReader() {
        String userinput;
        Scanner sc = new Scanner(System.in);
        userinput = sc.nextLine();
        return userinput;
    }

    private String passReader() {
        Scanner sc = new Scanner(System.in);
        Console console = System.console();
        try {
            String userinput = new String(console.readPassword());
            return userinput;

        } catch (Exception e) {
            String userinput = sc.nextLine();
            return userinput;
        }
    }

}
