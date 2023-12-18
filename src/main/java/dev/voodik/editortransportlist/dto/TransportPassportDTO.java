package dev.voodik.editortransportlist.dto;

public class TransportPassportDTO {

    private Integer uniqueNumber;
    private Integer referenceNumber;
    private Integer typeId;
    private Integer firmId;
    private Integer loadCapacity;
    private Integer normFuelConsumption;
    private String dateOfDebit;

    public TransportPassportDTO(Integer uniqueNumber, Integer referenceNumber, Integer typeId, Integer firmId, Integer loadCapacity, Integer normFuelConsumption, String dateOfDebit) {
        this.uniqueNumber = uniqueNumber;
        this.referenceNumber = referenceNumber;
        this.typeId = typeId;
        this.firmId = firmId;
        this.loadCapacity = loadCapacity;
        this.normFuelConsumption = normFuelConsumption;
        this.dateOfDebit = dateOfDebit;
    }

    public TransportPassportDTO() {
    }

    public Integer getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Integer uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public Integer getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(Integer loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Integer getNormFuelConsumption() {
        return normFuelConsumption;
    }

    public void setNormFuelConsumption(Integer normFuelConsumption) {
        this.normFuelConsumption = normFuelConsumption;
    }

    public String getDateOfDebit() {
        return dateOfDebit;
    }

    public void setDateOfDebit(String dateOfDebit) {
        this.dateOfDebit = dateOfDebit;
    }
}
