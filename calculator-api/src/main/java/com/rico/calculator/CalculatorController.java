package com.rico.calculator;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/calculate")
@CrossOrigin(origins = "*")
public class CalculatorController {

    private final CalculatorService service;

    public CalculatorController(CalculatorService service) {
        this.service = service;
    }

    @GetMapping("/{operation}")
    public Map<String, Object> calculate(
            @PathVariable String operation,
            @RequestParam double a,
            @RequestParam double b) {

        double result = switch (operation.toLowerCase()) {
            case "add"      -> service.add(a, b);
            case "subtract" -> service.subtract(a, b);
            case "multiply" -> service.multiply(a, b);
            case "divide"   -> service.divide(a, b);
            default -> throw new IllegalArgumentException("Unknown operation: " + operation);
        };

        return Map.of(
            "operation", operation,
            "a", a,
            "b", b,
            "result", result
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleError(IllegalArgumentException e) {
        return Map.of("error", e.getMessage());
    }
}
