package com.dm.vendingapi.controller;

import com.dm.vendingapi.dao.FileIOException;
import com.dm.vendingapi.dto.Product;
import com.dm.vendingapi.logic.RealLogic;
import com.dm.vendingapi.servicelayer.VendingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.*;

@CrossOrigin
@Controller
public class VendingRestController {

    private VendingService service;

    @Inject
    public VendingRestController(VendingService service) {
        this.service = service;

    }




    @RequestMapping(value = "/item/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Product getProduct(@PathVariable("name") String productName){

        return service.getProduct(productName);

    }

}
