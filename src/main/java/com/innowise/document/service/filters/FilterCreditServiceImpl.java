package com.innowise.document.service.filters;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.CreditContract;
import com.innowise.document.entity.documents.QCreditContract;
import com.innowise.document.repository.documents.CreditContractRepo;
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
public class FilterCreditServiceImpl implements FilterService<CreditContract>{

    @Autowired
    CreditContractRepo contractRepo;

    @Override
    public Page<CreditContract> findAllByFilter(FilterObject obj, int page, int size) throws ParseException{
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(obj.getSortOrder()), obj.getSortField()));
        Page<CreditContract> list =  contractRepo.findAll(createPredicate(obj), pageable);
        return list;
    }

    @Override
    public Predicate createPredicate(FilterObject obj) throws ParseException{
        QCreditContract qdocument = QCreditContract.creditContract;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!StringUtils.isEmpty(obj.getTitle())) {
            booleanBuilder.and(qdocument.title.containsIgnoreCase(obj.getTitle()));
        }

        if (!StringUtils.isEmpty(obj.getClientFullname())) {
            booleanBuilder.and(qdocument.clientFullName.containsIgnoreCase(obj.getClientFullname()));
        }

        if (!StringUtils.isEmpty(obj.getUsername()) && obj.getUsername() != null) {
            booleanBuilder.and(qdocument.user.username.containsIgnoreCase(obj.getUsername()));
        }
        if (!StringUtils.isEmpty(obj.getFromDate()) && !StringUtils.isEmpty(obj.getToDate())) {
            booleanBuilder.and(qdocument.dateOfCreation.between(
                    new SimpleDateFormat("yyyy-MM-dd").parse(obj.getFromDate()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(obj.getToDate())));
        }else if (!StringUtils.isEmpty(obj.getFromDate())) {
            booleanBuilder.and(qdocument.dateOfCreation.after(Date.valueOf(LocalDate.parse(obj.getFromDate()).minusDays(1).toString())));
        }else if(!StringUtils.isEmpty(obj.getToDate())){
            booleanBuilder.and(qdocument.dateOfCreation.before(Date.valueOf(LocalDate.parse(obj.getToDate()).plusDays(1).toString())));
        }
        if (!StringUtils.isEmpty(obj.getIsActive())) {
            booleanBuilder.and(qdocument._super.isActive.eq(Boolean.valueOf(obj.getIsActive())));
        }
        if (obj.getFromTerm() != null && obj.getToTerm() != null) {
            booleanBuilder.and(qdocument.term.between(obj.getFromTerm(), obj.getToTerm()));
        }else if (obj.getFromTerm() != null) {
            booleanBuilder.and(qdocument.term.goe(obj.getFromTerm()));
        }else if(obj.getToTerm() != null){
            booleanBuilder.and(qdocument.term.loe(obj.getToTerm()));
        }
        if (obj.getFromAnnualInterest() != null && obj.getToAnnualInterest() != null) {
            booleanBuilder.and(qdocument.annualInterest.between(obj.getFromAnnualInterest(), obj.getToAnnualInterest()));
        }else if (obj.getFromAnnualInterest() != null) {
            booleanBuilder.and(qdocument.annualInterest.goe(obj.getFromAnnualInterest()));
        }else if(obj.getToAnnualInterest() != null){
            booleanBuilder.and(qdocument.annualInterest.loe(obj.getToAnnualInterest()));
        }
        if (obj.getFromCreditAmount() != null && obj.getToCreditAmount() != null) {
            booleanBuilder.and(qdocument.creditAmount.between(obj.getFromCreditAmount(), obj.getToCreditAmount()));
        }else if (obj.getFromCreditAmount() != null) {
            booleanBuilder.and(qdocument.creditAmount.goe(obj.getFromCreditAmount()));
        }else if(obj.getToCreditAmount() != null){
            booleanBuilder.and(qdocument.creditAmount.loe(obj.getToCreditAmount()));
        }
        return booleanBuilder.getValue();
    }
}
