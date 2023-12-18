package dev.voodik.editortransportlist.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dev.voodik.editortransportlist.dto.TransportPassportDTO;
import dev.voodik.editortransportlist.model.TransportPassport;
import dev.voodik.editortransportlist.model.TransportType;
import dev.voodik.editortransportlist.repository.FirmRepository;
import dev.voodik.editortransportlist.repository.TransportPassportRepository;
import dev.voodik.editortransportlist.repository.TransportTypeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class TransportPassportEditor extends VerticalLayout implements KeyNotifier {

    private final TransportPassportRepository transportPassportRepository;
    private final TransportTypeRepository transportTypeRepository;
    private final FirmRepository firmRepository;

    private TransportPassportDTO transportPassportDTO;

    //private TextField uniqueNumber = new TextField("uniqueNumber");
    private TextField referenceNumber = new TextField("Учётный номер");
    private TextField typeId = new TextField("ID типа ТС");
    private TextField firmId = new TextField("ID фирмы ТС");
    private TextField loadCapacity = new TextField("Грузоподъёмность");
    private TextField normFuelConsumption = new TextField("Норматив расхода топлива");
    private TextField dateOfDebit = new TextField("Дата списания ТС");

    private Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Назад");
    private Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<TransportPassportDTO> binder = new Binder<>(TransportPassportDTO.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public TransportPassportEditor(TransportPassportRepository transportPassportRepository, TransportTypeRepository transportTypeRepository, FirmRepository firmRepository) {
        this.transportPassportRepository = transportPassportRepository;
        this.transportTypeRepository = transportTypeRepository;
        this.firmRepository = firmRepository;

        add(referenceNumber, typeId, firmId, loadCapacity, normFuelConsumption, dateOfDebit, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(event -> save());

        delete.addClickListener(event -> delete());
        cancel.addClickListener(event -> editTransportPassportDTO(transportPassportDTO));
    }

    public void editTransportPassportDTO(TransportPassportDTO newTPDTO) {
        try {
            if (newTPDTO == null) {
                setVisible(false);
                return;
            }

            if (newTPDTO.getUniqueNumber() != null) {
                initTransportPassportDTO(newTPDTO);
            }
            else
                transportPassportDTO = newTPDTO;

            binder.setBean(transportPassportDTO);

            setVisible(true);

            referenceNumber.focus();
        }
        catch (Exception e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE);
            return;
        }
    }

    private void delete() {
        try {
            Integer uniqueNumber = transportPassportDTO.getUniqueNumber();

            if (uniqueNumber == null)
                throw new Exception("Мне кажется, эту ошибку нереально получить");

            TransportPassport transportPassport = transportPassportRepository.findByUniqueNumber(uniqueNumber).get();

            transportPassportRepository.delete(transportPassport);
            changeHandler.onChange();
        }
        catch (Exception e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE);
            return;
        }
    }

    private void save(){
        try {
            Integer uniqueNumber = transportPassportDTO.getUniqueNumber();
            Integer typeId = transportPassportDTO.getTypeId();
            Integer firmId = transportPassportDTO.getFirmId();
            Integer referenceNumber = transportPassportDTO.getReferenceNumber();
            Integer loadCapacity = transportPassportDTO.getLoadCapacity();
            Integer normFuelConsumption = transportPassportDTO.getNormFuelConsumption();

            if (transportTypeRepository.findById(typeId).isEmpty())
                throw new Exception("Ошибка, такого типа ТС не существует");

            if (firmRepository.findById(firmId).isEmpty())
                throw new Exception("Ошибка, такого предприятия не существует");

            if (referenceNumber == null || loadCapacity == null || normFuelConsumption == null)
                throw new Exception("Все поля, кроме даты списания, обязательно должны быть заполнены");

            TransportPassport transportPassport;

            if (transportPassportRepository.findByUniqueNumber(uniqueNumber).isPresent())
                transportPassport = transportPassportRepository.findByUniqueNumber(uniqueNumber).get();
            else
                transportPassport = new TransportPassport();

            transportPassport.setReferenceNumber(transportPassportDTO.getReferenceNumber());
            transportPassport.setTypeId(transportPassportDTO.getTypeId());
            transportPassport.setFirmId(transportPassportDTO.getFirmId());
            transportPassport.setLoadCapacity(transportPassportDTO.getLoadCapacity());
            transportPassport.setNormFuelConsumption(transportPassportDTO.getNormFuelConsumption());
            transportPassport.setDateOfDebit(transportPassportDTO.getDateOfDebit());

            transportPassportRepository.save(transportPassport);

            changeHandler.onChange();
        }
        catch (Exception e) {
            Notification.show(e.getMessage(), 5000, Notification.Position.MIDDLE);
            return;
        }
    }

    private void initTransportPassportDTO(TransportPassportDTO newTPDTO) {
        transportPassportDTO = new TransportPassportDTO();
        TransportPassport transportPassport = transportPassportRepository.findByUniqueNumber(newTPDTO.getUniqueNumber()).get();
        transportPassportDTO.setUniqueNumber(transportPassport.getUniqueNumber());
        transportPassportDTO.setReferenceNumber(transportPassport.getReferenceNumber());
        transportPassportDTO.setTypeId(transportPassport.getTypeId());
        transportPassportDTO.setFirmId(transportPassport.getFirmId());
        transportPassportDTO.setLoadCapacity(transportPassport.getLoadCapacity());
        transportPassportDTO.setNormFuelConsumption(transportPassport.getNormFuelConsumption());
        transportPassportDTO.setDateOfDebit(transportPassport.getDateOfDebit());
    }
}
