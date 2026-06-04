package org.fleetflow.fleetflow.service;


import org.fleetflow.fleetflow.dto.AuthRequest;
import org.fleetflow.fleetflow.dto.AuthResponse;

public interface UserService{
     AuthResponse registerUser(AuthRequest request);
     AuthResponse loginUser(AuthRequest request);
}