package lk.ijse.sensortelemetry.client;

import lk.ijse.sensortelemetry.dto.AuthRequest;
import lk.ijse.sensortelemetry.dto.AuthResponse;
import lk.ijse.sensortelemetry.dto.TelemetryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "external-iot-service", url = "http://104.211.95.241:8080/api")
public interface ExternalIoTClient {

    @PostMapping("/auth/login")
    AuthResponse login(@RequestBody AuthRequest request);

    @GetMapping("/devices/telemetry/{deviceId}")
    TelemetryResponse getTelemetry(
            @RequestHeader("Authorization") String token,
            @PathVariable("deviceId") String deviceId
    );
}