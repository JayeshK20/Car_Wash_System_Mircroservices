package com.carwash.apigateway.service;

import com.carwash.apigateway.dto.ResponseDTO;
import com.carwash.apigateway.dto.UserClientDTO;
import com.carwash.apigateway.dto.UserPrincipal;
import com.carwash.apigateway.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class FeignUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ResponseDTO<UserClientDTO> user = userClient.getUserByEmail(username);
        if(user == null || user.getData() == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new UserPrincipal(user.getData());
    }
}
