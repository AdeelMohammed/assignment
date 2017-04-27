package com.elderstudios.controller;

import com.elderstudios.domain.assignment;
import com.elderstudios.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Controller
public class CarController {

	@Autowired
	protected AssignmentService assignmentService;

	private static final String assignment_FORM = "assignment";
	private static final String ENTRIES_KEY = "entries";
	private static final String FORM_KEY = "form";

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	public CarController()
    {

    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayassignment( Model model ) {

		model.addAttribute(ENTRIES_KEY, assignmentService.findAll());
		model.addAttribute(FORM_KEY, new assignment());

		return assignment_FORM;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String changeassignment(
			Model model,
			@Valid @ModelAttribute(FORM_KEY) assignment form,
			BindingResult bindingResult ) {

		if ( bindingResult.hasErrors() ) {
			model.addAttribute(ENTRIES_KEY, assignmentService.findAll());
			return assignment_FORM;
		}

		assignmentService.save(form);

		return "redirect:/";
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String deleteEntry (Model model, @PathVariable Long id) {

		assignmentService.delete (id);

		return "redirect:/";
	}

	@RequestMapping (value="/edit/{id}", method = RequestMethod.GET)
	public String editEntry (Model model, @PathVariable Long id) {
		model.addAttribute (FORM_KEY, assignmentService.findOne (id));
		return assignment_FORM;
	}

	@RequestMapping (value="/edit/{id}", method = RequestMethod.POST)
	public String editSaveassignment (Model model,
									 @PathVariable Long id,
									 @Valid @ModelAttribute(FORM_KEY) assignment form,
									 BindingResult bindingResult ) {

		if ( bindingResult.hasErrors() ) {
			model.addAttribute(ENTRIES_KEY, assignmentService.findAll());
			return assignment_FORM;
		}

		assignment existing = assignmentService.findOne (id);
		existing.setMake (form.getMake());
		existing.setModel (form.getModel());
		existing.setYear (form.getYear());
		existing.setComment(form.getComment());
		assignmentService.save (existing);

		return "redirect:/";
	}


}