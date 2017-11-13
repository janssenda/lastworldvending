package com.dm.vendingapi.controller;


import com.dm.vendingapi.dao.FileIOException;
import com.dm.vendingapi.dao.NoItemInventoryException;
import com.dm.vendingapi.dto.Money;
import com.dm.vendingapi.dto.Product;
import com.dm.vendingapi.logic.DeathException;
import com.dm.vendingapi.logic.MachineJameException;
import com.dm.vendingapi.logic.RealLogic;
import com.dm.vendingapi.servicelayer.InsufficientFundsException;
import com.dm.vendingapi.servicelayer.VendingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/jsp"})
public class VendingJSPController {

    private RealLogic logic;
    private String baseIndex = "index";

    @Inject
    public VendingJSPController(RealLogic logic) {
        this.logic = logic;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String welcomeMap(Model model) {
        model = mergeSharedData(model);
        model.addAttribute("boxmsg", "Please add money..");
        return baseIndex;
    }

    @RequestMapping(value = {"/vendItem"}, method = RequestMethod.POST)
    public String vendItem(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);
        model.addAttribute("boxmsg", "Thank you!!!");
        Money m = new Money(getAsBigDecimal(request.getParameter("totalCash")));
        String item = request.getParameter("selectedItem");


        if (m.getTotalmoney().compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("boxmsg", "Please add money..");
            return baseIndex;
        } else if (item.isEmpty() || item.equals("")) {
            model.addAttribute("boxmsg", "You must select an item...");
            return baseIndex;
        }

        try {
            model.addAttribute("productList", logic.vendProduct(m, item));
            model.addAttribute("plist", null);
            model.addAttribute("change", buildChangeMap(m));
            model.addAttribute("totalCash", "0.00");
            model.addAttribute("messages", "Congratulations! You got: ");
            return baseIndex;

        } catch (NoItemInventoryException e) {
            model.addAttribute("boxmsg", "SOLD OUT!!!");
            return baseIndex;
        } catch (InsufficientFundsException e) {

            String amtShort = new BigDecimal(logic.getProductPrice(item))
                    .subtract(m.getTotalmoney()).setScale(2, RoundingMode.HALF_UP).toString();

            model.addAttribute("boxmsg", "Insufficient Funds!! <br/> Please add $" + amtShort);
            return baseIndex;

        } catch (NullPointerException e) {
            model.addAttribute("error", true);
            model.addAttribute("errMsg", logic.checkForFileIOErrors());
        } catch (MachineJameException e) {
            model.addAttribute("boxmsg", "JAM!!!!");
        }

        model.addAttribute("change", buildChangeMap(m));
        model.addAttribute("totalCash", "0.00");

        return baseIndex;

    }

    @RequestMapping(value = {"/processRealRequest"}, method = RequestMethod.POST)
    public String processRealRequest(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);

        switch (request.getParameter("realAction")) {
            case "shake":
                try {
                    List<Product> productList = logic.shakeTheMachine();
                    if (productList.size() > 0) {
                        model.addAttribute("productList", productList);
                        model.addAttribute("messages", "Yay! You scored free items!");
                    } else {
                        model.addAttribute("messageBox", true);
                        model.addAttribute("messages", "Too bad, nothing this time...");
                        model.addAttribute("buttonText", "Okay");
                    }
                } catch (DeathException e) {
                    model.addAttribute("messageBox", true);
                    model.addAttribute("messages", "Oh no! You DIED!!! " +
                            "<br/> <br/> And your money is ours now!!<br/>" +
                            "(Have you learned a lesson?)<br/>");
                    model.addAttribute("totalCash", "0.00");
                    model.addAttribute("buttonText", "No :(");
                    model.addAttribute("boxmsg", " XXX<br/>XXX<br/>");
                }

                model.addAttribute("plist", null);
                break;

            case "getItems":
                Map<String, Integer> stuckItemMap = new HashMap<>();
                Map<String, List<Product>> fullStuckMap = logic.getStuckItems();
                fullStuckMap.forEach((k, v) -> stuckItemMap.put(k, v.size()));
                model.addAttribute("messages", "The following items are stuck: ");
                model.addAttribute("stuckItemList", stuckItemMap);
                model.addAttribute("plist", null);
                break;
            case "noaction":
                break;
        }

        return baseIndex;
    }


    @RequestMapping(value = {"/getChange"}, method = RequestMethod.POST)
    public String getChange(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);
        Money m = new Money(getAsBigDecimal(request.getParameter("totalCash")));

        if (m.getTotalmoney().compareTo(BigDecimal.ZERO) <= 0) {
            model.addAttribute("boxmsg", "Please add money...");
            return baseIndex;
        }

        model.addAttribute("change", buildChangeMap(m));
        model.addAttribute("boxmsg", "THANK YOU!!! ");
        model.addAttribute("totalCash", "0.00");
        return baseIndex;
    }

    @RequestMapping(value = {"/loadItem"}, method = RequestMethod.POST)
    public String loadItem(HttpServletRequest request, Model model) {
        model = mergeSharedData(request, model);

        BigDecimal d = getAsBigDecimal(request.getParameter("totalCash"));

        if (d.compareTo(BigDecimal.ZERO) > 0) {
            model.addAttribute("boxmsg", "Make a selection...");
        } else {
            model.addAttribute("boxmsg", "Please add money...");
        }

        String[] params = request.getParameter("selectedItemButton").split(",");

        model.addAttribute("selectedItem", params[0]);
        model.addAttribute("displayID", params[1]);

        return baseIndex;
    }


    @RequestMapping(value = "/addMoney", method = RequestMethod.POST)
    public String addMoney(HttpServletRequest request, Model model) {

        model = mergeSharedData(request, model);
        BigDecimal add;
        Money m = new Money(getAsBigDecimal(request.getParameter("totalCash")));

        switch (request.getParameter("mbutton")) {
            case "dollar":
                add = new BigDecimal("1.00");
                break;
            case "quarter":
                add = new BigDecimal("0.25");
                break;
            case "dime":
                add = new BigDecimal("0.10");
                break;
            case "nickel":
                add = new BigDecimal("0.05");
                break;

            default:
                add = new BigDecimal("0.00");
        }

        String newTotal = add.add(m.getTotalmoney()).setScale(2, RoundingMode.HALF_UP).toString();
        model.addAttribute("totalCash", String.format(newTotal));
        model.addAttribute("boxmsg", "Make a selection...");

        return baseIndex;
    }

    @RequestMapping(value = "/toggleRealism", method = RequestMethod.POST)
    public String toggleRealism(HttpServletRequest request, Model model) {

        if (logic.getRealism()) {
            logic.setRealism(false);
        } else {
            logic.setRealism(true);
        }

        model = mergeSharedData(request, model);
        model.addAttribute("boxmsg", request.getParameter("boxmsg"));

        return baseIndex;
    }


    private Model mergeSharedData(HttpServletRequest request, Model model) {
        model.addAttribute("error", false);
        model.addAttribute("errMsg", "");
        model.addAttribute("realismType", logic.getRealismVersion());
        model.addAttribute("realism", logic.getRealism());
        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));

        model.addAttribute("displayID", request.getParameter("displayID"));
        model.addAttribute("totalCash", request.getParameter("totalCash"));
        model.addAttribute("selectedItem", request.getParameter("selectedItem"));
        model.addAttribute("time", formd);

        try {
            model.addAttribute("plist", logic.returnInventoryMap());
        } catch (NullPointerException e) {
            model.addAttribute("error", true);
            model.addAttribute("errMsg", logic.checkForFileIOErrors());
        }
        return model;
    }

    private Model mergeSharedData(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("errMsg", "");
        model.addAttribute("realismType", logic.getRealismVersion());
        model.addAttribute("realism", logic.getRealism());

        LocalDateTime d = LocalDateTime.now();
        String formd = d.format(DateTimeFormatter.ofPattern("MM/dd/yyyy @ hh:mm a"));
        model.addAttribute("time", formd);

        try {
            model.addAttribute("plist", logic.returnInventoryMap());
        } catch (NullPointerException e) {
            model.addAttribute("error", true);
            model.addAttribute("errMsg", logic.checkForFileIOErrors());
        }
        return model;
    }

    private Map<String, String> buildChangeMap(Money m) {

        Map<String, String> changeMap = new HashMap<>();

        changeMap.put("quarters", m.getQuarters() + "x0.25");
        changeMap.put("dimes", m.getDimes() + "x0.10");
        changeMap.put("nickels", m.getNickels() + "x0.10");
        changeMap.put("pennies", m.getPennies() + "x0.01");

        return changeMap;
    }

    private BigDecimal getAsBigDecimal(String s) {
        BigDecimal current;
        try {
            current = new BigDecimal(s);
        } catch (NumberFormatException e) {
            current = BigDecimal.ZERO;
        }

        return current;
    }

}
