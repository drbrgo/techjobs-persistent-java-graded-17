package org.launchcode.techjobs.persistent.controllers;


import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    //so i failed tests because they are looking for the @GetMapping annotation below to have a value of
    //"/" so long as it does, I can leave the return statement of the form submission handler as "redirect:"
    //otherwise, the @GetMapping annotation can have no value, and the return statement can read "redirect:/skills"
    @GetMapping("/")
    public String index (Model model) {
      model.addAttribute("skills", skillRepository.findAll());
      return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm (Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm (@ModelAttribute @Valid Skill newSkill,
                                       Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "skills/add";
        }
//        model.addAttribute("skills", newSkill); does nothing
        skillRepository.save(newSkill);
        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill (Model model, @PathVariable int skillId) {
        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../";
        }
    }

}
