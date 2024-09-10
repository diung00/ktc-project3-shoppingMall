package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.dto.RequestDto;
import com.example.project3.applyForBusiness.entity.RequestEntity;
import com.example.project3.user.UserRepository;
import com.example.project3.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BusinessService {
    public final BusinessRepository requestRepository;
    public final UserRepository userRepository;

    public BusinessService(BusinessRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    //method tạo yêu cầu mới
    public RequestEntity createRequest(RequestDto dto){
        //1. ktra xem user c tồn tại không
        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //2. ktra xem user đã yêu cầu chưa
        if (requestRepository.existsByUserAndStatus(user, RequestStatus.PENDING)){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        //3. kiểm tra xem user đã là business chưa
        else if (requestRepository.existsByUserAndStatus(user, RequestStatus.APPROVED)){
            throw new ResponseStatusException(HttpStatus.ACCEPTED);
        } else {
            //4. tạo yêu cầu mới
            RequestEntity request = new RequestEntity();
            request.setUser(user);
            request.setReason(dto.getReason());
            request.setStatus(RequestStatus.PENDING);
           return requestRepository.save(request);
        }
    }

     //method xem tất cả yêu cầu của user

    public List<RequestEntity> viewRequests(){
        return requestRepository.findAll();
    }

    //method chấp nhận yêu cầu

    public RequestEntity approveRequest(Long requestId) {
            RequestEntity request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            User user = request.getUser();
            // Thêm quyền ROLE_BUSINESS
            String authorities = user.getAuthorities();
            if (!authorities.contains("ROLE_BUSINESS")) {
                user.setAuthorities(authorities + ",ROLE_BUSINESS");
                userRepository.save(user);
            }

            // Cập nhật trạng thái yêu cầu
            request.setStatus(RequestStatus.APPROVED);
           return requestRepository.save(request);

    }

        // Từ chối yêu cầu
        public RequestEntity rejectRequest(Long requestId) {
            RequestEntity request = requestRepository.findById(requestId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            request.setStatus(RequestStatus.REJECTED);
           return requestRepository.save(request);

        }












}
