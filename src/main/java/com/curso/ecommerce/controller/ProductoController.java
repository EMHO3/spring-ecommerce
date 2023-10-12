package com.curso.ecommerce.controller;


import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;

import jakarta.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private UploadFileService upload;

    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos",productoService.findAll());
        return "administrador/productos/show";
    }

    @GetMapping("/create")
    public String create(){

        return "administrador/productos/create";
    }


    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("este es el objeto producto {}",producto);

        Usuario u= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
        producto.setUsuario(u);

          //imagen
        if (producto.getId()==null){//cuando se crea un producto
          String nombreImagen= upload.saveImage(file);
          producto.setImagen(nombreImagen);
        }else {

        }
        productoService.save(producto);
        return "redirect:/productos";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto=productoService.get(id);
        producto =optionalProducto.get();
        LOGGER.info("Producto buscado : {}",producto);
        model.addAttribute("producto",producto);
        return "administrador/productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto ,@RequestParam("img") MultipartFile file) throws IOException{
        if (file.isEmpty()){ //cuando editamos el producto mas no la imagen
            Producto p =new Producto();
            p =productoService.get(producto.getId()).get();
            producto.setImagen(p.getImagen());
        } else{//cuando se edita tambien la imagen
            Producto p = new Producto();
            p =productoService.get(producto.getId()).get();}
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Integer id){
         Producto p = new Producto();
         p =productoService.get(id).get();

         //eliminar cuando no sea la imagen por defecto
         if(!p.getImagen().equals("default.jpg")){
           upload.deleteImage(p.getImagen());
         }

        productoService.delete(id);
        return "redirect:/productos";
    }

}
