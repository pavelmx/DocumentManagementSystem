package com.innowise.document.service;

import com.innowise.document.entity.*;
import com.innowise.document.entity.QDocument;
import com.innowise.document.entity.QUser;
import com.innowise.document.repository.DocumentRepo;
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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Service
public class FilterServiceImpl implements FilterService{


    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Page<Document> findAllDocsByFilter(FilterEntity filterEntity, int page, int size) throws ParseException{
        Pageable pageable =  PageRequest.of(page, size, new Sort
                (Sort.Direction.fromString(filterEntity.getSortOrder()), filterEntity.getSortField()));
        Page<Document> list =  documentRepo.findAll(
                createPredicateForDocument(filterEntity), pageable);

        return list;
    }

    @Override
    public Predicate createPredicateForDocument(FilterEntity filterEntity) throws ParseException{
        QDocument qdocument = QDocument.document;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //title
        if (!StringUtils.isEmpty(filterEntity.getTitle())) {
            booleanBuilder.and(qdocument.title.toLowerCase().contains(filterEntity.getTitle().toLowerCase()));
        }//customer
        if (!StringUtils.isEmpty(filterEntity.getCustomer())) {
            booleanBuilder.and(qdocument.customer.toLowerCase().contains(filterEntity.getCustomer().toLowerCase()));
        }//expired
        if (!StringUtils.isEmpty(filterEntity.getExpired())) {
            booleanBuilder.and(qdocument.expired.eq(Boolean.valueOf(filterEntity.getExpired())));
            System.out.println("exp " + filterEntity.getExpired());
        }//date of creation
        if (!StringUtils.isEmpty(filterEntity.getFromDate()) && !StringUtils.isEmpty(filterEntity.getToDate())) {
            booleanBuilder.and(qdocument.dateOfCreation.between(
                    new SimpleDateFormat("yyyy-MM-dd").parse(filterEntity.getFromDate()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(filterEntity.getToDate())));
        }else if (!StringUtils.isEmpty(filterEntity.getFromDate())) {
            booleanBuilder.and(qdocument.dateOfCreation.after(Date.valueOf(LocalDate.parse(filterEntity.getFromDate()).minusDays(1).toString())));
        }else if(!StringUtils.isEmpty(filterEntity.getToDate())){
            booleanBuilder.and(qdocument.dateOfCreation.before(Date.valueOf(LocalDate.parse(filterEntity.getToDate()).plusDays(1).toString())));
        }
        //user
        if (!StringUtils.isEmpty(filterEntity.getUsername()) && filterEntity.getUsername()!=null) {
            booleanBuilder.and(qdocument.user.username.toLowerCase().contains(filterEntity.getUsername()));
        }
        return booleanBuilder.getValue();
    }

    @Override
    public Page<User> findAllUsersByFilter(FilterEntity filterEntity, int page, int size){
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(filterEntity.getSortOrder()), filterEntity.getSortField()));
        Page<User> list =  userRepo.findAll(
                createPredicateForUser(filterEntity), pageable);
        return list;
    }



    @Override
    public Predicate createPredicateForUser(FilterEntity filterEntity){
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
        }
        return booleanBuilder.getValue();
    }
}
