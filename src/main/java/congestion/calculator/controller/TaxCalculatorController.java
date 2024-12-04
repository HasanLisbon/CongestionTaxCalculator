package congestion.calculator.controller;

import congestion.calculator.service.CongestionTaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/congestion-tax")
public class TaxCalculatorController {

    @Autowired
    private final CongestionTaxCalculator congestionTaxCalculator;

    public TaxCalculatorController(CongestionTaxCalculator congestionTaxCalculator) {
        this.congestionTaxCalculator = congestionTaxCalculator;
    }
}
