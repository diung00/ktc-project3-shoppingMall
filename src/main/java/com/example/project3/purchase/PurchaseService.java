package com.example.project3.purchase;

import com.example.project3.config.AuthenticationFacade;
import com.example.project3.item.ItemEntity;
import com.example.project3.item.ItemRepository;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepo purchaseRepo;
    private final ItemRepository itemRepository;
    private final AuthenticationFacade authFacade;


    public PurchaseDto createPurchase(PurchaseDto dto) {
        UserEntity user = authFacade.getCurrentUserEntity();
        ItemEntity item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (item.getStock() < dto.getQuantity()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }

        item.setStock(item.getStock() - dto.getQuantity());
        itemRepository.save(item);

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setUser(user);
        purchase.getItems().add(item);
        purchase.setQuantity(dto.getQuantity());
        purchase.setTotalPrice(item.getPrice()* dto.getQuantity());
        purchase.setStatus(PurchaseStatus.PENDING);
        purchaseRepo.save(purchase);
        return PurchaseDto.fromEntity(purchase);
    }

    public List<PurchaseDto> purchaseList(){
        UserEntity user = authFacade.getCurrentUserEntity();
        List<PurchaseEntity> purchases = purchaseRepo.findAllByUserId(user.getId());
        return purchases.stream()
                .map(PurchaseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Xác nhận yêu cầu mua
    public PurchaseDto approvePurchase(Long purchaseId) {
        UserEntity owner = authFacade.getCurrentUserEntity();
        PurchaseEntity purchase = purchaseRepo.findById(purchaseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!purchase.getShop().getOwner().equals(owner)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (purchase.getStatus().equals(PurchaseStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        purchase.setStatus(PurchaseStatus.APPROVED);
        return PurchaseDto.fromEntity(purchaseRepo.save(purchase));
    }

    // Hủy yêu cầu mua
    public void cancelPurchase(Long purchaseId) {
        UserEntity user = authFacade.getCurrentUserEntity();
        PurchaseEntity purchase = purchaseRepo.findById(purchaseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (purchase.getStatus().equals(PurchaseStatus.PENDING)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        for (ItemEntity item : purchase.getItems()) {
            item.setStock(item.getStock() + purchase.getQuantity());
            itemRepository.save(item);
        }
        purchase.setStatus(PurchaseStatus.CANCELLED);
        purchaseRepo.save(purchase);
    }
}

