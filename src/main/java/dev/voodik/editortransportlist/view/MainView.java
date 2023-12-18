package dev.voodik.editortransportlist.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.voodik.editortransportlist.component.TransportPassportEditor;
import dev.voodik.editortransportlist.dto.TransportPassportDTO;
import dev.voodik.editortransportlist.dto.TransportTypeDTO;
import dev.voodik.editortransportlist.model.Firm;
import dev.voodik.editortransportlist.model.TransportVid;
import dev.voodik.editortransportlist.repository.FirmRepository;
import dev.voodik.editortransportlist.repository.TransportPassportRepository;
import dev.voodik.editortransportlist.repository.TransportTypeRepository;
import dev.voodik.editortransportlist.repository.TransportVidRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Тестовое")
@Route
public class MainView extends VerticalLayout {

    private final FirmRepository firmRepository;
    private final TransportVidRepository transportVidRepository;
    private final TransportTypeRepository transportTypeRepository;
    private final TransportPassportRepository transportPassportRepository;
    private final TransportPassportEditor transportPassportEditor;

    private Grid<Firm> firmGrid;
    private Grid<TransportVid> transportVidGrid;
    private Grid<TransportTypeDTO> transportTypeGrid;
    private Grid<TransportPassportDTO> transportPassportGrid;

    private H1 firmTitle;
    private H1 vidTitle;
    private H1 typeTitle;
    private H1 transportPassportTitle;
    private Button addNewTransportPassport = new Button("Добавить");

    private Firm selectedFirm;
    private TransportVid selectedVid;
    private TransportTypeDTO selectedType;

    @Autowired
    public MainView(FirmRepository firmRepository, TransportVidRepository transportVidRepository, TransportTypeRepository transportTypeRepository, TransportPassportRepository transportPassportRepository, TransportPassportEditor transportPassportEditor) {
        this.firmRepository = firmRepository;
        this.transportVidRepository = transportVidRepository;
        this.transportTypeRepository = transportTypeRepository;
        this.transportPassportRepository = transportPassportRepository;
        this.transportPassportEditor = transportPassportEditor;

        showFirm();
    }

    private void showFirm() {
        firmGrid = new Grid<>(Firm.class);
        List<Firm> data = firmRepository.findAll();
        data.sort(Comparator.comparing(Firm::getId));
        firmGrid.setItems(data);

        firmGrid.removeAllColumns();

        firmGrid.addColumn(Firm::getId).setHeader("ID");
        firmGrid.addColumn(Firm::getName).setHeader("Имя");
        firmGrid.addColumn(Firm::getLocation).setHeader("Локация");

        firmGrid.asSingleSelect().addValueChangeListener(event -> {
            if (transportVidGrid != null) {
                remove(transportVidGrid, transportTypeGrid, transportPassportGrid, vidTitle, typeTitle, transportPassportTitle, addNewTransportPassport);
            }
            selectedFirm = event.getValue();
            showTransportVid();

        });

        firmTitle = new H1("Предприятия");
        add(firmTitle);
        add(firmGrid);
    }

    private void showTransportVid() {
        transportVidGrid = new Grid<>(TransportVid.class);
        List<TransportVid> data = transportVidRepository.findAll();
        data.sort(Comparator.comparing(TransportVid::getId));
        transportVidGrid.setItems(data);

        transportVidGrid.removeAllColumns();

        transportVidGrid.addColumn(TransportVid::getId).setHeader("ID");
        transportVidGrid.addColumn(TransportVid::getShortName).setHeader("ShortName");
        transportVidGrid.addColumn(TransportVid::getFullName).setHeader("FullName");

        transportVidGrid.asSingleSelect().addValueChangeListener(event -> {
            if (transportTypeGrid != null) {
                remove(transportTypeGrid, transportPassportGrid, typeTitle, transportPassportTitle, addNewTransportPassport);
            }
            selectedVid = event.getValue();
            showTransportType(selectedVid.getId());
        });

        vidTitle = new H1("Виды ТС");
        add(vidTitle);
        add(transportVidGrid);
    }

    private void showTransportType(int id) {
        transportTypeGrid = new Grid<>(TransportTypeDTO.class);
        List<TransportTypeDTO> data = transportTypeRepository.findDTOWithVidId(id);
        data.sort(Comparator.comparing(TransportTypeDTO::getId));
        transportTypeGrid.setItems(data);

        transportTypeGrid.removeAllColumns();

        transportTypeGrid.addColumn(TransportTypeDTO::getId).setHeader("ID");
        transportTypeGrid.addColumn(TransportTypeDTO::getName).setHeader("Имя");
        transportTypeGrid.addColumn(TransportTypeDTO::getVidId).setHeader("ID вида");
        transportTypeGrid.addColumn(TransportTypeDTO::getAttribute).setHeader("Признак");

        transportTypeGrid.asSingleSelect().addValueChangeListener(event -> {
            if (transportPassportGrid != null) {
                remove(transportPassportGrid, transportPassportTitle, addNewTransportPassport);
            }
            selectedType = event.getValue();
            showTransportPassport();
        });

        typeTitle = new H1("Типы ТС");
        add(typeTitle);
        add(transportTypeGrid);
    }

    private void showTransportPassport() {
        transportPassportGrid = new Grid<>(TransportPassportDTO.class);
        AtomicReference<List<TransportPassportDTO>> data = new AtomicReference<>(transportPassportRepository.findTransportPassportByFirmIdAndVidAndType(selectedFirm.getId(), selectedType.getId()));
        data.get().sort(Comparator.comparing(TransportPassportDTO::getUniqueNumber));
        transportPassportGrid.setItems(data.get());

        transportPassportGrid.removeAllColumns();

        transportPassportGrid.addColumn(TransportPassportDTO::getUniqueNumber).setHeader("ID");
        transportPassportGrid.addColumn(TransportPassportDTO::getReferenceNumber).setHeader("Учётный номер");
        transportPassportGrid.addColumn(TransportPassportDTO::getTypeId).setHeader("ID Типа");
        transportPassportGrid.addColumn(TransportPassportDTO::getFirmId).setHeader("ID фирмы");
        transportPassportGrid.addColumn(TransportPassportDTO::getLoadCapacity).setHeader("Грузоподъёмность");
        transportPassportGrid.addColumn(TransportPassportDTO::getNormFuelConsumption).setHeader("Норматив расхода топлива");
        transportPassportGrid.addColumn(TransportPassportDTO::getDateOfDebit).setHeader("Дата списания");

        transportPassportTitle = new H1("Паспорта ТС");
        add(transportPassportTitle);
        add(addNewTransportPassport);
        add(transportPassportGrid);

        transportPassportGrid.asSingleSelect().addValueChangeListener(event -> {
            transportPassportEditor.editTransportPassportDTO(event.getValue());
            add(transportPassportEditor);
        });

        addNewTransportPassport.addClickListener(buttonClickEvent -> {
            transportPassportEditor.editTransportPassportDTO(new TransportPassportDTO());
            add(transportPassportEditor);
        });

        transportPassportEditor.setChangeHandler(() -> {
            transportPassportEditor.setVisible(false);
            data.set(transportPassportRepository.findTransportPassportByFirmIdAndVidAndType(selectedFirm.getId(), selectedType.getId()));
            data.get().sort(Comparator.comparing(TransportPassportDTO::getUniqueNumber));
            transportPassportGrid.setItems(data.get());
        });
    }
}
