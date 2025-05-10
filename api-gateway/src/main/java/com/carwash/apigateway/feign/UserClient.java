package com.carwash.apigateway.feign;

import com.carwash.apigateway.dto.ResponseDTO;
import com.carwash.apigateway.dto.UserClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/users/{email}")
    ResponseDTO<UserClientDTO> getUserByEmail(@PathVariable String email);

}
