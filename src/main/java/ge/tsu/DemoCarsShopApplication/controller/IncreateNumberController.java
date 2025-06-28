package ge.tsu.DemoCarsShopApplication.controller;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class IncreateNumberController {

    @Autowired
    MeterRegistry meterRegistry;

    @PostConstruct
    void init() {
        Gauge.builder("numberOf.total.cars", 15, value -> value)
                .register(meterRegistry);
    }

    @GetMapping("/increase")
    public void increateNumber() {
        meterRegistry.counter("numberOf.total.cars").increment();
    }
}
