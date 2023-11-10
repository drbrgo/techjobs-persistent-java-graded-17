package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");
        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	    model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job job, Errors errors, Model model, @RequestParam(defaultValue = "0") int employerId,
                                    @RequestParam(required = false) List<Integer> skills) {
//        @RequestParam(name="employer_id" employerId

        if (errors.hasErrors()) {
	    model.addAttribute("title", "Add Job");
            return "add";
        }
        if(employerId!=0) {
            Optional<Employer> result = employerRepository.findById(employerId);
            if (result.isPresent()) {
                Employer employer = result.get();
                job.setEmployer(employer);
                model.addAttribute("employers", employer);
            }
        }
       if(skills!=null) {
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
            job.setSkills(skillObjs);
            model.addAttribute("skills", skillObjs);
        }
        model.addAttribute("jobs", job);
        //the line above seems to do nothing at all... index template fails to display job
        jobRepository.save(job);


//        }
//        model.addAttribute("employers", employerRepository.findById(employerId));
//        Employer selectedEmployer = employerRepository.findById(employerId).orElse(null);
//        newJob.setEmployer(selectedEmployer);
//        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional<Job> result = jobRepository.findById(jobId);

        if(result.isPresent()){
            Job job = result.get();
            model.addAttribute("job", job);
        }

            return "view";
    }

}
