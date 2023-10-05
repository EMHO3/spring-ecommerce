package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;
    //para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();
    //datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "administrador/usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String productohome(@PathVariable Integer id, Model model){
        log.info("id producto enviado como parametro{}",id);
        Producto producto =new Producto();
        Optional<Producto> productoOptional= productoService.get(id);
        producto=productoOptional.get();
        model.addAttribute("producto", producto);
          return "administrador/usuario/productohome";
    }

   //metodo añadir al carrito
    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id,@RequestParam Integer cantidad,Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto =new Producto();
        double sumaTotal=0;
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("producto añadido: {} ", optionalProducto.get());
        log.info("cantidad:{}",cantidad);
        producto=optionalProducto.get();
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //validar que el producto no se añada 2 veces
        Integer idproducto= producto.getId();
        boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idproducto);
        if (!ingresado){
            detalles.add(detalleOrden);
        }

        sumaTotal=detalles.stream().mapToDouble(dt ->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "administrador/usuario/carrito";
    }

    //quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id,Model model){

        //Lista nueva de productos
        List<DetalleOrden>ordenesNueva=new ArrayList<DetalleOrden>();
        for(DetalleOrden detalleOrden:detalles){
              if(detalleOrden.getProducto().getId()!=id){
                    ordenesNueva.add(detalleOrden);
              }
        }

        //poner la nueva lista con los productos restantes
        detalles=ordenesNueva;
        double sumaTotal=0;
        sumaTotal=detalles.stream().mapToDouble(dt ->dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);

        return "administrador/usuario/carrito";
    }


    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart",detalles);
        model.addAttribute("orden",orden);
        return "administrador/usuario/carrito";
    }

}
