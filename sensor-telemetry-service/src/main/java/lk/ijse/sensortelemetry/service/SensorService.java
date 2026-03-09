package lk.ijse.sensortelemetry.service;

import lk.ijse.sensortelemetry.client.ExternalIoTClient;
import lk.ijse.sensortelemetry.dto.AuthRequest;
import lk.ijse.sensortelemetry.dto.Device;
import lk.ijse.sensortelemetry.dto.TelemetryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private final ExternalIoTClient ioTClient;

    private TelemetryResponse latestReading;

    @Value("${external.iot.username}")
    private String iotUsername;

    @Value("${external.iot.password}")
    private String iotPassword;

    public SensorService(ExternalIoTClient ioTClient) {
        this.ioTClient = ioTClient;
    }

    // Wakes up every 10 seconds!
    @Scheduled(fixedRate = 10000)
    public void fetchAndPushTelemetry() {
        try {
            //Login securely behind the scenes
            String token = ioTClient.login(new AuthRequest(iotUsername, iotPassword)).getAccessToken();
            String bearerToken = "Bearer " + token;

            //Find all devices that have been registered
            List<Device> devices = ioTClient.getAllDevices(bearerToken);

            //Loop through them and get their live temperature
            for (Device device : devices) {
                TelemetryResponse telemetry = ioTClient.getTelemetry(bearerToken, device.getDeviceId());

                this.latestReading = telemetry;

                System.out.println("Fetched live data for Zone: " + device.getZoneId() +
                        " | Temp: " + telemetry.getValue().getTemperature() + "°C");

            }
        } catch (Exception e) {
            System.err.println("Failed to fetch telemetry: " + e.getMessage());
        }
    }

    public TelemetryResponse getLatestReading() {
        return this.latestReading;
    }
}