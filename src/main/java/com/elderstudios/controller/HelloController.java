package com.elderstudios.controller;

import com.elderstudios.domain.assignment;
import com.elderstudios.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HelloController {

	@Autowired
	protected GuestBookService guestBookService;

	private static final String GUESTBOOK_FORM = "guestbook";
	private static final String ENTRIES_KEY = "entries";
	private static final String FORM_KEY = "form";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayGuestbook( Model model ) {

		model.addAttribute(ENTRIES_KEY, guestBookService.findAll());
		model.addAttribute(FORM_KEY, new assignment());

		return GUESTBOOK_FORM;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String changeGuestbook(
			Model model,
			@Valid @ModelAttribute(FORM_KEY) assignment form,
			BindingResult bindingResult ) {

		if ( bindingResult.hasErrors() ) {
			model.addAttribute(ENTRIES_KEY, guestBookService.findAll());
			return GUESTBOOK_FORM;
		}

		guestBookService.save(form);

		return "redirect:/";
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String deleteEntry (Model model, @PathVariable Long id) {

		guestBookService.delete (id);

		return "redirect:/";
	}

	@RequestMapping (value="/edit/{id}", method = RequestMethod.GET)
	public String editEntry (Model model, @PathVariable Long id) {
		model.addAttribute (FORM_KEY, guestBookService.findOne (id));
		return GUESTBOOK_FORM;
	}

	@RequestMapping (value="/edit/{id}", method = RequestMethod.POST)
	public String editSaveGuestBook (Model model,
									 @PathVariable Long id,
									 @Valid @ModelAttribute(FORM_KEY) assignment form,
									 BindingResult bindingResult ) {

		if ( bindingResult.hasErrors() ) {
			model.addAttribute(ENTRIES_KEY, guestBookService.findAll());
			return GUESTBOOK_FORM;
		}

		assignment existing = guestBookService.findOne (id);
		existing.setMake (form.getMake());
		existing.setModel (form.getModel());
		existing.setYear (form.getYear());
		existing.setComment(form.getComment());
		guestBookService.save (existing);

		return "redirect:/";
	}


}