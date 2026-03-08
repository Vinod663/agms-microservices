package lk.ijse.zonemanagement.service;

import lk.ijse.zonemanagement.client.ExternalIoTClient;
import lk.ijse.zonemanagement.dto.DeviceRegistrationRequest;
import lk.ijse.zonemanagement.dto.DeviceRegistrationResponse;
import lk.ijse.zonemanagement.model.Zone;
import lk.ijse.zonemanagement.repository.ZoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ExternalIoTClient ioTClient;

    // inject the repository and the Feign client
    public ZoneService(ZoneRepository zoneRepository, ExternalIoTClient ioTClient) {
        this.zoneRepository = zoneRepository;
        this.ioTClient = ioTClient;
    }

    @Transactional
    public Zone createZone(Zone zone, String rawToken) {

        Zone savedZone = zoneRepository.save(zone);

        // Prepare the payload for the External IoT API
        DeviceRegistrationRequest request = new DeviceRegistrationRequest(
                savedZone.getZoneName() + "-Sensor",
                savedZone.getId()
        );

        // Call the External API using OpenFeign
        DeviceRegistrationResponse response = ioTClient.registerDevice("Bearer " + rawToken, request);

        // Update a local database with the brand-new external device ID
        savedZone.setExternalDeviceId(response.getDeviceId());
        return zoneRepository.save(savedZone);
    }
}