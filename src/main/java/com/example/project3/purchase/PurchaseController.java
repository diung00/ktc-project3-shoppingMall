package com.example.project3.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/create")
    public PurchaseDto create(
            @RequestBody
            PurchaseDto purchaseDto
    ){
        return purchaseService.createPurchase(purchaseDto);
    }

    @GetMapping("/list")
    public List<PurchaseDto> purchases() {
        return purchaseService.purchaseList();
    }

    @PutMapping("/approve/{purchaseId}")
    public PurchaseDto approve(
            @PathVariable
            Long purchaseId
    ){
        return purchaseService.approvePurchase(purchaseId);
    }

    @PutMapping("/cancel/{purchaseId}")
    public ResponseEntity cancel(
            @PathVariable
            Long purchaseId
    ){
        purchaseService.cancelPurchase(purchaseId);
        return ResponseEntity.ok("ok");
    }






}
