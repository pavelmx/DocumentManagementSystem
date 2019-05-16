package com.innowise.document.unit;

import com.innowise.document.DocumentManagementSystemApplication;
import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.entity.file.FileStorage;
import com.innowise.document.entity.file.ResponseFile;
import com.innowise.document.repository.documents.CreditContractRepo;
import com.innowise.document.service.FileStorageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DocumentManagementSystemApplication.class)
public class FileServiceTests {

    @Mock
    private CreditContractRepo mockCreditContractRepo;

    @Mock
    private FileStorage mockFileStorage;


    @Autowired
    AuthenticationManager authenticationManager;

    @InjectMocks
    private FileStorageServiceImpl service;

    private CreditContract creditContract;

    @Before
    public void setUp() {
        initMocks(this);
        creditContract = new CreditContract(1450f, 10f, 30);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("admin", "123456"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testAddFile() throws Exception{
        Mockito.when(mockCreditContractRepo.getOne(anyLong())).thenReturn(creditContract);
        Mockito.when(mockFileStorage.getUploadDir()).thenReturn("gdgdfg");
        String kind = "credit";
        Long id = 1L;
        String name = "test-file";
        String filename = "test.txt";
        String contentType = "text/plain";
        String data =  "test data";
        MultipartFile file = new MockMultipartFile(name, filename, contentType, data.getBytes());
        ResponseFile responseFile = service.addFile(file, id, kind);
        assertEquals(filename, responseFile.getFileOriginalName());
        assertEquals(contentType, responseFile.getFileType());
    }

    @Test(expected = Exception.class)
    public void testLoadFileAsResource() throws Exception{
        Mockito.when(mockCreditContractRepo.findByFilename(anyString())).thenReturn(java.util.Optional.ofNullable(creditContract));
        String kind = "credit";
        String filename = "test.txt"; //nonexistent file
        Resource resource = service.loadFileAsResource(filename, kind);
    }

    @Test
    public void testDeleteFile() throws Exception{
        CreditContract creditContract = new CreditContract();
        creditContract.setAnnualInterest(11f);
        creditContract.setCreditAmount(1500f);
        creditContract.setTerm(12);
        Mockito.when(mockCreditContractRepo.findByFilename(any())).thenReturn(java.util.Optional.of(creditContract));
        Mockito.when(mockCreditContractRepo.save(any())).thenReturn(creditContract);
        String kind = "credit";
        String filename = "test.txt";
        service.deleteFile(filename, kind);
    }
}
