package com.example.project3.applyForBusiness;

import com.example.project3.applyForBusiness.dto.RequestBusinessDto;
import com.example.project3.applyForBusiness.entity.RequestBusinessEntity;
import com.example.project3.config.AuthenticationFacade;
import com.example.project3.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BusinessService {
    public final BusinessRepository requestRepository;
    private final AuthenticationFacade authFacade;



    public RequestBusinessDto toBusinessRequest(RequestBusinessDto dto){
        UserEntity user = authFacade.getCurrentUserEntity();

        if (user.getAuthorities().contains("ROLE_DEFAULT")){
            throw new ResponseStatusException((HttpStatus.BAD_REQUEST));
        }

        if (requestRepository.existsByUserAndStatus(user, RequestStatus.PENDING)){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        else if (requestRepository.existsByUserAndStatus(user, RequestStatus.APPROVED)){
            throw new ResponseStatusException(HttpStatus.ACCEPTED);
        } else {

            RequestBusinessEntity request = new RequestBusinessEntity();
            request.setUser(user);
            request.setReason(dto.getReason());
            request.setStatus(RequestStatus.PENDING);
           return RequestBusinessDto.fromEntity(requestRepository.save(request));
        }
    }
















}
