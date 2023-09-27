package com.curso.ecommerce.controller;


import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(){
        return "administrador/productos/show";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "administrador/productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("este es el objeto producto {}",producto);
        Usuario u= new Usuario(1,"","","","","","","");
        producto.setUsuario(u);

        productoService.save(producto);
        return "redirect:/productos";
    }
}