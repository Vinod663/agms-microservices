package lk.ijse.automationcontrol.service;

import lk.ijse.automationcontrol.client.ZoneClient;
import lk.ijse.automationcontrol.dto.SensorData;
import lk.ijse.automationcontrol.dto.ZoneDTO;
import lk.ijse.automationcontrol.model.ActionLog;
import lk.ijse.automationcontrol.repository.ActionLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutomationService {

    private final ZoneClient zoneClient;
    private final ActionLogRepository actionLogRepository;

    public AutomationService(ZoneClient zoneClient, ActionLogRepository actionLogRepository) {
        this.zoneClient = zoneClient;
        this.actionLogRepository = actionLogRepository;
    }

    @Transactional
    public void processSensorData(SensorData sensorData) {
        try {
            //Ask the Zone Service for the threshold rules
            ZoneDTO zone = zoneClient.getZoneById(sensorData.getZoneId());

            //Evaluate the temperature
            String action = "NORMAL";
            if (sensorData.getTemperature() > zone.getMaxTemp()) {
                action = "COOLER_ON";
            } else if (sensorData.getTemperature() < zone.getMinTemp()) {
                action = "HEATER_ON";
            }

            //Log the action to the database
            ActionLog log = new ActionLog(
                    sensorData.getZoneId(),
                    sensorData.getDeviceId(),
                    sensorData.getTemperature(),
                    action
            );
            actionLogRepository.save(log);

            //Print the result
            System.out.println("⚡ Automation triggered for Zone " + sensorData.getZoneId() +
                    " | Temp: " + sensorData.getTemperature() + "°C" +
                    " | Action: " + action);

        } catch (Exception e) {
            System.err.println("❌ Failed to process automation for zone " + sensorData.getZoneId() + ": " + e.getMessage());
        }
    }
}