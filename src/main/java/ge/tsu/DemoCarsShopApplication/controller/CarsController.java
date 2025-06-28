package ge.tsu.DemoCarsShopApplication.controller;

import ge.tsu.DemoCarsShopApplication.controller.comment.CommentForm;
import ge.tsu.DemoCarsShopApplication.entiti.Car;
import ge.tsu.DemoCarsShopApplication.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CarsController {

    private final CarRepository repository;

    @GetMapping("/")
    public String index(Model model) {
        log.info("Called index()");
        List<Car> carsList = repository.findAll(Sort.by("createdDate").descending());
        log.debug("Loaded {} cars", carsList.size());
        model.addAttribute("carsList", carsList);
        return "index";
    }

    @GetMapping("/car/{id}")
    public String car(@PathVariable Long id, Model model) {
        log.info("Called car() with id={}", id);
        Optional<Car> car = repository.findById(id);
        if (car.isPresent()) {
            log.debug("Found car with id={} -> {}", id, car.get().getBrand());
            model.addAttribute("car", car.get());
            model.addAttribute("commentForm", new CommentForm(car.get().getId()));
            return "car/single";
        } else {
            log.warn("Car with id={} not found", id);
            return "error/404";
        }
    }


}
