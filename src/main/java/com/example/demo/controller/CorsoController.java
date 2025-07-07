package com.example.demo.controller;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.service.CorsoService;
import com.example.demo.service.DiscenteService;
import com.example.demo.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//ciao
@RestController
@RequestMapping("/corsi")
@CrossOrigin(origins = "http://localhost:4200")
public class CorsoController {
    @Autowired
    CorsoService corsoService;
    @Autowired
    DocenteService docenteService;
    @Autowired
    DiscenteService discenteService;

    //lista
    @GetMapping("/{id}")
    public CorsoDTO getById(@PathVariable Long id) {
        return corsoService.get(id);
    }

    @GetMapping("/list")
    public List<CorsoDTO> list() {
        return corsoService.findAll();

    }

    @PostMapping
    public CorsoDTO create(@RequestBody CorsoDTO corsoDTO) {
        return corsoService.save(corsoDTO);
    }

    @PutMapping("/{id}")
    public CorsoDTO update(@PathVariable Long id, @RequestBody CorsoDTO corsoDTO) {
        return corsoService.update(id, corsoDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        corsoService.delete(id);
    }


}
