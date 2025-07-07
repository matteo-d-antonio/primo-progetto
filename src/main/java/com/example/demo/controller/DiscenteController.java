package com.example.demo.controller;


import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.entity.Discente;
import com.example.demo.service.DiscenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/discenti")
@CrossOrigin(origins = "http://localhost:4200")
public class DiscenteController {

    @Autowired
    DiscenteService discenteService;


    @GetMapping("/list")
    public List<DiscenteDTO> list() {
        return discenteService.findAll();
    }

    @PostMapping
    public DiscenteDTO create(@RequestBody DiscenteDTO discenteDTO) {
        return discenteService.save(discenteDTO);
    }

    @PutMapping("/{id}")
    public DiscenteDTO update(@PathVariable Long id, @RequestBody DiscenteDTO discenteDTO) {
        return discenteService.update(id, discenteDTO);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id) {
        discenteService.delete(id);
    }


    @GetMapping("/cerca")
    public List<DiscenteDTO> cerca(@RequestParam String nome) {
        return discenteService.findByName(nome);
    }
}
