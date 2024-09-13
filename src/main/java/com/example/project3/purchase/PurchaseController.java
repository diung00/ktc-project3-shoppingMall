package com.example.project3.purchase;

import com.example.project3.applyForBusiness.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/create/{userId}")
    public PurchaseDto create(
            @PathVariable
            Long userId,
            @RequestBody
            PurchaseDto purchaseDto
    ){
        return purchaseService.createPurchase(userId, purchaseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PurchaseDto>> purchaseList() {
        List<PurchaseDto> purchaseList = purchaseService.purchaseList();
        return ResponseEntity.ok(purchaseList);
    }

    @PutMapping("/approve/{purchaseId}")
    public ResponseEntity approve(
            @PathVariable
            Long purchaseId
    ){
        PurchaseDto approvedPurchase = purchaseService.approvePurchase(purchaseId);
        return ResponseEntity.ok(approvedPurchase);
    }

    @PutMapping("/cancel/{purchaseId}")
    public ResponseEntity cancel(
            @RequestParam("userId")
            Long userId,
            @PathVariable
            Long purchaseId
    ){
        purchaseService.cancelPurchase(userId, purchaseId);
        return ResponseEntity.ok("ok");  
    }






}
