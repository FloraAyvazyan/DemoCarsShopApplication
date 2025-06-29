package ge.tsu.DemoCarsShopApplication.controller;

import ge.tsu.DemoCarsShopApplication.model.ContactUs;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class ContactUsControler {

    @GetMapping("/contact-us")
    public String contactUs(Model model) {
        log.info("GET /contact-us called");
        model.addAttribute("contactUs", new ContactUs());
        return "contact-us";
    }

    @PostMapping("/contact-us")
    public String contactUs(@Valid @ModelAttribute("contactUs") ContactUs contactUs,
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("POST /contact-us called with name={} and email={}", contactUs.getName(), contactUs.getEmail());

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors occurred in contact form: {}", bindingResult.getAllErrors());
            return "contact-us";
        }

        log.info("Contact form submitted successfully: {}", contactUs);
        redirectAttributes.addAttribute("successMessage", "Message successfully sent to the contact us");
        return "redirect:/contact-us";
    }
}
