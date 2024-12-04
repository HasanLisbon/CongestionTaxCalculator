package congestion.calculator.controller;

import congestion.calculator.dto.RateDto;
import congestion.calculator.entity.Rate;
import congestion.calculator.entity.TaxRequest;
import congestion.calculator.entity.TaxResponse;
import congestion.calculator.exception.TaxException;
import congestion.calculator.service.CongestionTaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/congestion-tax")
@RequiredArgsConstructor
@Slf4j
public class TaxCalculatorController {

    @Autowired
    private final CongestionTaxService congestionTaxService;

    @PostMapping
    public ResponseEntity<TaxResponse> getTax(@RequestBody TaxRequest taxReq) {
        try {
            return ResponseEntity.ok().body(congestionTaxService.calculateTax(taxReq));
        } catch (IllegalArgumentException ex) {
            log.error(ex.toString(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (TaxException ex) {
            log.error(ex.toString(), ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
