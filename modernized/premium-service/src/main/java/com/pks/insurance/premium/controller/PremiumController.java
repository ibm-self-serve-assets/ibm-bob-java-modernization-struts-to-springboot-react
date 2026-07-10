package com.pks.insurance.premium.controller;

import com.pks.insurance.premium.dto.QuoteResponse;
import com.pks.insurance.premium.service.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumService premiumService;

    @GetMapping("/{ssn}")
    @PreAuthorize("hasAnyRole('USER','ADMIN') and (authentication.name == #ssn or hasRole('ADMIN'))")
    public ResponseEntity<QuoteResponse> getQuote(@PathVariable String ssn) {
        return ResponseEntity.ok(premiumService.calculateQuote(ssn));
    }
}
