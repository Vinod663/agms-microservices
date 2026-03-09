package lk.ijse.automationcontrol.dto;

public class ZoneDTO {
    private String id;
    private double minTemp;
    private double maxTemp;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getMinTemp() { return minTemp; }
    public void setMinTemp(double minTemp) { this.minTemp = minTemp; }
    public double getMaxTemp() { return maxTemp; }
    public void setMaxTemp(double maxTemp) { this.maxTemp = maxTemp; }
}