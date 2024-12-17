package br.com.insuranceacme.insuranceacme_api.controller;


import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.InsuranceQuoteResponse;
import br.com.insuranceacme.insuranceacme_api.service.InsuranceQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/Insurancequotes")
public class InsuranceQuoteController {


    private final InsuranceQuoteService insuranceQuoteService;

    @PostMapping
    public ResponseEntity<URI> create(@RequestBody final InsuranceQuoteRequest request) {

        final Long id = insuranceQuoteService.create(request);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<InsuranceQuoteResponse> findById(@PathVariable("id") final Long id) {

        return ResponseEntity.ok(insuranceQuoteService.findById(id));
    }


    @GetMapping
    public ResponseEntity<Page<InsuranceQuoteResponse>> findAll(final Pageable pageable) {

        return ResponseEntity.ok(insuranceQuoteService.findAll(pageable));
    }
}
