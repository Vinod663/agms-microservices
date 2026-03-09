package lk.ijse.automationcontrol.controller;

import lk.ijse.automationcontrol.dto.SensorData;
import lk.ijse.automationcontrol.service.AutomationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automation")
public class AutomationController {

    private final AutomationService automationService;

    public AutomationController(AutomationService automationService) {
        this.automationService = automationService;
    }

    @PostMapping
    public ResponseEntity<Void> receiveSensorData(@RequestBody SensorData sensorData) {
        automationService.processSensorData(sensorData);
        return ResponseEntity.ok().build();
    }
}