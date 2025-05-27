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
@Controller
@RequestMapping("/corsi")
public class CorsoController {
    @Autowired
    CorsoService corsoService;
    @Autowired
    DocenteService docenteService;
    @Autowired
    DiscenteService discenteService;

    //lista
    @GetMapping("/list")
    public String list(Model model) {
        List<CorsoDTO> corsi= corsoService.findAll();
        corsi.forEach(c -> System.out.println("DTO: " + c.getNome()));
        model.addAttribute("corsi", corsi);
        return "corso-list";
    }

    @GetMapping("/new")
    public String showAdd(Model model) {
        model.addAttribute("corso", new CorsoDTO());
        model.addAttribute("docenti", docenteService.findAll());
        model.addAttribute("discenti", discenteService.findAll());
        return "form-corso";
    }

    @PostMapping
    public String create(@ModelAttribute("corso") CorsoDTO corso, BindingResult br, Model model){
        if(br.hasErrors()) {
            model.addAttribute("docenti", docenteService.findAll());
            model.addAttribute("discenti", discenteService.findAll());
            return "form-corso";
        }
        corsoService.save(corso);
        return "redirect:/corsi/list";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model){
        model.addAttribute("corso", corsoService.get(id));
        model.addAttribute("docenti", docenteService.findAll());
        model.addAttribute("discenti", discenteService.findAll());
        return "form-corso";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("corso") CorsoDTO corso, BindingResult br, Model model) {
        if(br.hasErrors()) {
            model.addAttribute("docenti", docenteService.findAll());
            model.addAttribute("discenti", discenteService.findAll());
            return "form-corso";
        }


        corsoService.save(corso);
        return "redirect:/corsi/list";
    }



    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        corsoService.delete(id);
        return "redirect:/corsi/list";
    }

}
