package com.cusonar.ib.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cusonar.ib.core.aop.ExceptionController;
import com.cusonar.ib.core.security.JwtTokenUtil;
import com.cusonar.ib.domain.SignupRequest;
import com.cusonar.ib.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserControllerTest {
    
    static final String USER_URI = "/api/users";
    ObjectMapper jsonMapper = new ObjectMapper();
    
    @Mock UserService userService;
    @Mock PasswordEncoder passwordEncoder;
    @Mock AuthenticationManager am;
    @Mock JwtTokenUtil jwtTokenUtil;
    @Mock Principal principal;
    
    @InjectMocks
    UserController userController;
    
    MockMvc mockMvc;
    
    String encodedPassword, token;
    SignupRequest request;
    UsernamePasswordAuthenticationToken authToken;
    UserDetails userDetails;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ExceptionController())
                .alwaysDo(print())
                .build();
        encodedPassword = "encoded";
        token = "generatedToken";
        request = new SignupRequest("user1", "user1pass");
        authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        userDetails = new User("user1", "user1pass", Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
    
    @Test
    public void signupTest() throws Exception {
        when(userService.loadUserByUsername("user1")).thenReturn(userDetails);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);
        when(am.authenticate(authToken)).thenReturn(null);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);
        mockMvc.perform(MockMvcRequestBuilders.post(USER_URI + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToJsonString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(token));
        ;
        verify(userService, times(1)).createUser(userDetails);
    }
    
    @Test
    public void signinTest() throws Exception {
        when(userService.loadUserByUsername("user1")).thenReturn(userDetails);
        when(am.authenticate(authToken)).thenReturn(null);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);
        mockMvc.perform(MockMvcRequestBuilders.post(USER_URI + "/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectToJsonString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(token));
        ;
    }
    
    @Test
    public void refreshTest() throws Exception {
        when(principal.getName()).thenReturn("user1");
        when(userService.loadUserByUsername("user1")).thenReturn(userDetails);
        when(am.authenticate(authToken)).thenReturn(null);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(token);
        mockMvc.perform(MockMvcRequestBuilders.get(USER_URI + "/refresh")
                .accept(MediaType.APPLICATION_JSON)
                .principal(principal))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(token));
    }
    
    private String objectToJsonString(Object object) throws Exception {
        return jsonMapper.writeValueAsString(object);
    }
}
