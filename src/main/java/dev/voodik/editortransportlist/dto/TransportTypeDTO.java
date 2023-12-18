package dev.voodik.editortransportlist.dto;

public class TransportTypeDTO {
    private Integer id;
    private String name;
    private Integer vidId;
    private String attribute;

    public TransportTypeDTO(Integer id, String name, Integer vidId, String attribute) {
        this.id = id;
        this.name = name;
        this.vidId = vidId;
        this.attribute = attribute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVidId() {
        return vidId;
    }

    public void setVidId(Integer vidId) {
        this.vidId = vidId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
