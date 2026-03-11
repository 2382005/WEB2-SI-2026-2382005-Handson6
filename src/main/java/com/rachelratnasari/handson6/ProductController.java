package com.rachelratnasari.handson6;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    // In-memory storage
    private static final List<Product> products = new ArrayList<>();
    private static int lastId = 0;

    @GetMapping
    public String showListProduct(Model model) {
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/add")
    public String showAddFormProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", Category.values());
        return "product/form";
    }

    @PostMapping("/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes) {
        if (product.getId() == null) {
            // New product
            product.setId(++lastId);
            products.add(product);
            System.out.println("New product saved: " + product.getName());
        } else {
            // Update existing product
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId().equals(product.getId())) {
                    products.set(i, product);
                    System.out.println("Product updated: " + product.getName());
                    break;
                }
            }
        }

        System.out.println("- Name     : " + product.getName());
        System.out.println("- Price    : " + product.getPrice());
        System.out.println("- Category : " + product.getCategory());
        System.out.println("- Stock    : " + product.getStock());
        System.out.println("- Desc     : " + product.getDescription());
        System.out.println("- Active   : " + product.isActive());

        redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
        return "redirect:/products";
    }
}
