package com.innowise.document.unit;

import com.innowise.document.entity.CatalogOfOperationMode;
import com.innowise.document.entity.User;
import com.innowise.document.entity.documents.WorkContract;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.repository.documents.WorkContractRepo;
import com.innowise.document.service.documents.WorkContractServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;


public class DocumentServiceTests {

    @Mock
    private WorkContractRepo mockWorkContractRepo;

    @Mock
    private UserRepo mockUserRepo;

    @InjectMocks
    private WorkContractServiceImpl workContractDocumentService;

    private WorkContract workContract1;
    private WorkContract workContract2;
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        CatalogOfOperationMode catalogOfOperationMode = new CatalogOfOperationMode();
        workContract1 = new WorkContract(new Date(), 10, "work", "position", catalogOfOperationMode, 40, 40,1000);
        workContract2 = new WorkContract(new Date(), 11, "work2", "position2", catalogOfOperationMode, 41, 41,1001);
        user = new User( "testuser", "user12345", "123456", "test@user.com");

    }

    @Test
    public void testGetById() {
        Mockito.when(mockWorkContractRepo.findById(any())).thenReturn(java.util.Optional.ofNullable(workContract1));
        Long id = 1L;
         WorkContract result = workContractDocumentService.getById(id);
        assertEquals(null, result.getId());
    }


    @Test
    public void testFindAll() {
        List<WorkContract> workContractList = new ArrayList<>();
        workContractList.add(workContract1);
        workContractList.add(workContract2);
        Mockito.when(mockWorkContractRepo.findAll()).thenReturn(workContractList);
        Integer salary = 1000;
        String placeOfWork = "work";
        String position = "position";
        Integer holiday = 40;
        Integer workingHours = 40;
        Integer salary2 = 1001;
        String placeOfWork2 = "work2";
        String position2 = "position2";
        Integer holiday2 = 41;
        Integer workingHours2 = 41;
        List<WorkContract> result = workContractDocumentService.getAll();
        assertEquals(salary, result.get(0).getSalary());
        assertEquals(placeOfWork, result.get(0).getPlaceOfWork());
        assertEquals(position, result.get(0).getPosition());
        assertEquals(holiday, result.get(0).getHoliday());
        assertEquals(workingHours, result.get(0).getWorkingHours());
        assertEquals(salary2, result.get(1).getSalary());
        assertEquals(placeOfWork2, result.get(1).getPlaceOfWork());
        assertEquals(position2, result.get(1).getPosition());
        assertEquals(holiday2, result.get(1).getHoliday());
        assertEquals(workingHours2, result.get(1).getWorkingHours());
    }


    @Test
    public void testAddByUsername() {
        Mockito.when(mockUserRepo.existsByUsername(any())).thenReturn(true);
        Mockito.when(mockUserRepo.findByUsername(any())).thenReturn(java.util.Optional.ofNullable(user));
        Mockito.when(mockWorkContractRepo.save(any())).thenReturn(workContract1);
        WorkContract result = workContractDocumentService.addByUsername(user.getUsername(), workContract1);
        assertEquals(user.getUsername(), result.getUser().getUsername());
    }

    @Test
    public void testUpdateByUsername() {
        Mockito.when(mockWorkContractRepo.existsById(any())).thenReturn(true);
        Mockito.when(mockUserRepo.existsByUsername(any())).thenReturn(true);
        Mockito.when(mockWorkContractRepo.save(any())).thenReturn(workContract1);
        Mockito.when(mockUserRepo.findByUsername(any())).thenReturn(java.util.Optional.ofNullable(user));
        String username = "testuser";
        WorkContract result = workContractDocumentService.updateByUsername(username, workContract1);
        assertEquals(username, result.getUser().getUsername());
    }
}
