package com.innowise.document.service.filters;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.QUser;
import com.innowise.document.entity.RoleName;
import com.innowise.document.entity.User;
import com.innowise.document.repository.RoleRepo;
import com.innowise.document.repository.UserRepo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FilterUserServiceImpl implements FilterService<User> {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public Page<User> findAllByFilter(FilterObject filterEntity, int page, int size){
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(filterEntity.getSortOrder()), filterEntity.getSortField()));
        Page<User> list =  userRepo.findAll(
                createPredicate(filterEntity), pageable);
        return list;
    }

    @Override
    public Predicate createPredicate(FilterObject filterEntity){
        QUser quser = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //name
        if (!StringUtils.isEmpty(filterEntity.getName())) {
            booleanBuilder.and(quser.name.toLowerCase().contains(filterEntity.getName().toLowerCase()));
        }
        //email
        if (!StringUtils.isEmpty(filterEntity.getEmail())) {
            booleanBuilder.and(quser.email.toLowerCase().contains(filterEntity.getEmail().toLowerCase()));
        }
        //username
        if (!StringUtils.isEmpty(filterEntity.getUsername())) {
            booleanBuilder.and(quser.username.toLowerCase().contains(filterEntity.getUsername().toLowerCase()));
        }
        //activationCode
        if (filterEntity.getActivationCode().equals("confirm")) {
            booleanBuilder.and(quser.activationCode.isNull());
        }
        if (filterEntity.getActivationCode().equals("notconfirm")) {
            booleanBuilder.and(quser.activationCode.isNotNull());
        }//ROLE_USER

        booleanBuilder.and(quser.roles.contains(roleRepo.findByName(RoleName.ROLE_USER).get()));

        return booleanBuilder.getValue();
    }
}
