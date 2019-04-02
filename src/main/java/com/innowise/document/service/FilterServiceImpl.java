package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.QDocument;
import com.innowise.document.entity.QUser;
import com.innowise.document.entity.User;
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
    public Page<Document> findAllDocsByFilter(String title, String customer, String fromDate, String toDate,
                                          String username, String expired, int page, int size, String sortField,
                                          String sortOrder) throws ParseException{
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(sortOrder), sortField));
        Page<Document> list =  documentRepo.findAll(
                createPredicateForDocument(title, customer, fromDate, toDate, username, expired), pageable);

        return list;
    }

    @Override
    public Page<User> findAllUsersByFilter(String name, String activationCode, String email, String username, int page, int size,
                                               String sortField, String sortOrder){
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(sortOrder), sortField));
        Page<User> list =  userRepo.findAll(
                createPredicateForUser(name, email, username, activationCode), pageable);
        return list;
    }

    @Override
    public Predicate createPredicateForDocument(String title, String customer, String fromDate, String toDate,
                                                String username, String expired) throws ParseException{
        QDocument qdocument = QDocument.document;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
         //title
        if (!StringUtils.isEmpty(title)) {
            booleanBuilder.and(qdocument.title.toLowerCase().contains(title.toLowerCase()));
        }//customer
        if (!StringUtils.isEmpty(customer)) {
            booleanBuilder.and(qdocument.customer.toLowerCase().contains(customer.toLowerCase()));
        }//expired
        if (!StringUtils.isEmpty(expired)) {
            booleanBuilder.and(qdocument.expired.eq(Boolean.valueOf(expired)));
        }//date of creation
        if (!StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)) {
            booleanBuilder.and(qdocument.dateOfCreation.between(
                    new SimpleDateFormat("yyyy-MM-dd").parse(fromDate),
                    new SimpleDateFormat("yyyy-MM-dd").parse(toDate)));
        }else if (!StringUtils.isEmpty(fromDate)) {
            //booleanBuilder.and(qdocument.dateOfCreation.after(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate)));
            booleanBuilder.and(qdocument.dateOfCreation.after(Date.valueOf(LocalDate.parse(fromDate).minusDays(1).toString())));
        }else if(!StringUtils.isEmpty(toDate)){
            //booleanBuilder.and(qdocument.dateOfCreation.before(new SimpleDateFormat("yyyy-MM-dd").parse(toDate)));
            booleanBuilder.and(qdocument.dateOfCreation.before(Date.valueOf(LocalDate.parse(toDate).plusDays(1).toString())));
        }
        //user
        if (!StringUtils.isEmpty(username) && username!=null) {
            booleanBuilder.and(qdocument.user.username.toLowerCase().contains(username));
        }
        return booleanBuilder.getValue();
    }

    @Override
    public Predicate createPredicateForUser(String name, String email, String username, String activationCode){
        QUser quser = QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //name
        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(quser.name.toLowerCase().contains(name.toLowerCase()));
        }
        //email
        if (!StringUtils.isEmpty(email)) {
            booleanBuilder.and(quser.email.toLowerCase().contains(email.toLowerCase()));
        }
        //username
        if (!StringUtils.isEmpty(username)) {
            booleanBuilder.and(quser.username.toLowerCase().contains(username.toLowerCase()));
        }
        //activationCode
        if (activationCode.equals("confirm")) {
            booleanBuilder.and(quser.activationCode.isNull());
        }
        if (activationCode.equals("notconfirm")) {
            booleanBuilder.and(quser.activationCode.isNotNull());
        }
        return booleanBuilder.getValue();
    }
}
