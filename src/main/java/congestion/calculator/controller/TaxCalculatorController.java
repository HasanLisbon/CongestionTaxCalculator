package congestion.calculator.controller;

import congestion.calculator.service.CongestionTaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/congestion-tax")
@RequiredArgsConstructor
public class TaxCalculatorController {

    private final CongestionTaxService congestionTaxService;

}
