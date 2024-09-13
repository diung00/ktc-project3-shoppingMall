package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.dto.RequestDto;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.shop.ShopRepository;
import com.example.project3.shop.ShopService;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    public final BusinessRepository requestRepository;
    public final UserRepository userRepository;
    public final ShopRepository shopRepository;
    public final ShopService shopService;

    public BusinessService(
            BusinessRepository requestRepository,
            UserRepository userRepository,
            ShopRepository shopRepository,
            ShopService shopService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
        this.shopService = shopService;
    }

    //method tạo yêu cầu mới
    public RequestBusinessEntity createRequest(Long userId, RequestDto dto){
        //1. ktra xem user c tồn tại không
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        //2. ktra xem user đã yêu cầu chưa
        if (requestRepository.existsByUserAndStatus(user.get(), RequestStatus.PENDING)){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        //3. kiểm tra xem user đã là business chưa
        else if (requestRepository.existsByUserAndStatus(user.get(), RequestStatus.APPROVED)){
            throw new ResponseStatusException(HttpStatus.ACCEPTED);
        } else {
            //4. tạo yêu cầu mới
            RequestBusinessEntity request = new RequestBusinessEntity();
            request.setUser(user.get());
            request.setReason(dto.getReason());
            request.setStatus(RequestStatus.PENDING);
           return requestRepository.save(request);
        }
    }

     //method xem tất cả yêu cầu của user

    public List<RequestBusinessEntity> viewRequests(){
        return requestRepository.findAll();
    }

    //method chấp nhận yêu cầu

    public RequestBusinessEntity approveRequest(Long requestId) {
            RequestBusinessEntity request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            User user = request.getUser();
            // Thêm quyền ROLE_BUSINESS
            String authorities = user.getAuthorities();
            if (!authorities.contains("ROLE_BUSINESS")) {
                user.setAuthorities(authorities + ",ROLE_BUSINESS");
                userRepository.save(user);
                shopService.getAShop(user);
            }
            // Cập nhật trạng thái yêu cầu
           request.setStatus(RequestStatus.APPROVED);
           return requestRepository.save(request);

    }

        // Từ chối yêu cầu
        public RequestBusinessEntity rejectRequest(Long requestId) {
            RequestBusinessEntity request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            request.setStatus(RequestStatus.REJECTED);
           return requestRepository.save(request);

        }












}
