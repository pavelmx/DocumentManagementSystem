package com.innowise.document.unit;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.Role;
import com.innowise.document.entity.User;
import com.innowise.document.repository.RoleRepo;
import com.innowise.document.repository.UserRepo;
import com.innowise.document.service.filters.FilterUserServiceImpl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;

public class FilterServiceTests {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RoleRepo roleRepo;

    @InjectMocks
    private FilterUserServiceImpl filterUserService;

    private Page<User> list;

    private BooleanBuilder booleanBuilder;

    @Before
    public void setUp() {
        initMocks(this);
        User user1 = new User("username", "name", "123456", "email@email");
        User user2 = new User("username", "name", "123456", "email@email");
        List<User> listusers = new ArrayList<>();
        listusers.add(user1); listusers.add(user2);
        list = new PageImpl<>(listusers);
        booleanBuilder = new BooleanBuilder();
    }

    @Test
    public void testFindAllByFilter() throws Exception{
        Mockito.when(userRepo.findAll((Predicate) any(), (Pageable) any())).thenReturn(list);
        Pageable pageable =  PageRequest.of(1, 1);
        Predicate predicate = booleanBuilder.getValue();
        Page<User> list = userRepo.findAll(predicate, pageable);
        assertTrue(list.getTotalPages() == 1);
        assertTrue(list.getTotalElements() == 2);
    }

    @Test
    public void testCreatePredicate() throws Exception{
        Role role = new Role();
        Mockito.when(roleRepo.findByName(any())).thenReturn(java.util.Optional.of(role));
        FilterObject filterObject = new FilterObject();
        Predicate predicate = filterUserService.createPredicate(filterObject);
        System.out.println(booleanBuilder.getValue());
        assertEquals(null, booleanBuilder.getValue());
        assertNotEquals(role, predicate);
    }
}
