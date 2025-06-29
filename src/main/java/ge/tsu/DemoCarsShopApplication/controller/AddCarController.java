package ge.tsu.DemoCarsShopApplication.controller;

import ge.tsu.DemoCarsShopApplication.entiti.Car;
import ge.tsu.DemoCarsShopApplication.model.AddCar;
import ge.tsu.DemoCarsShopApplication.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
public class AddCarController {

    @Autowired
    private CarService carService;

    @GetMapping("/add")
    public String addCar(Model model) {
        log.info("GET /add called - preparing form");
        model.addAttribute("addCar", new AddCar());
        return "/add";  // templates/add.html
    }
    @PostMapping("/add")
    public String saveCar(@Valid @ModelAttribute("addCar") AddCar addCar,
                          BindingResult bindingResult,
                          @RequestParam("image") MultipartFile image) throws IOException {

        log.info("POST /add called with car data: {}", addCar);

        if (image.isEmpty()) {
            log.warn("Image file is empty");
            bindingResult.addError(new FieldError("addCar", "image", "No image file selected"));
        }

        if (bindingResult.hasErrors()) {
            log.debug("Validation errors: {}", bindingResult.getAllErrors());
            return "/add";
        }

        Car newCar = carService.save(addCar, image);
        log.info("New car saved with ID: {}", newCar.getId());

        return "redirect:/car/" + newCar.getId();
    }


}
