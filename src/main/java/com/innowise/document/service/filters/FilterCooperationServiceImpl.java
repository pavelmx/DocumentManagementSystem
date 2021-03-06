package com.innowise.document.service.filters;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.CooperationContract;
import com.innowise.document.entity.documents.QCooperationContract;
import com.innowise.document.repository.documents.CooperationContractRepo;
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
public class FilterCooperationServiceImpl implements FilterService<CooperationContract>{

    @Autowired
    CooperationContractRepo contractRepo;

    @Override
    public Page<CooperationContract> findAllByFilter(FilterObject obj, int page, int size) throws ParseException{
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(obj.getSortOrder()), obj.getSortField()));
        Page<CooperationContract> list =  contractRepo.findAll(createPredicate(obj), pageable);
        return list;
    }

    @Override
    public Predicate createPredicate(FilterObject obj) throws ParseException{
        QCooperationContract qdocument = QCooperationContract.cooperationContract;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!StringUtils.isEmpty(obj.getTitle())) {
            booleanBuilder.and(qdocument.title.containsIgnoreCase(obj.getTitle()));
        }
        if (!StringUtils.isEmpty(obj.getClientFullname())) {
            booleanBuilder.and(qdocument.clientFullName.containsIgnoreCase(obj.getClientFullname()));
        }
        if (!StringUtils.isEmpty(obj.getUsername()) && obj.getUsername() != null) {
            booleanBuilder.and(qdocument._super.user.username.contains(obj.getUsername()));
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
        if (!StringUtils.isEmpty(obj.getKindOfActivity())) {
            booleanBuilder.and(qdocument.kindOfActivity.containsIgnoreCase(obj.getKindOfActivity()));
        }
        if (obj.getFromCooperationTerm() != null && obj.getToCooperationTerm() != null) {
            booleanBuilder.and(qdocument.term.between(obj.getFromCooperationTerm(), obj.getToCooperationTerm()));
        }else if (obj.getFromCooperationTerm() != null) {
            booleanBuilder.and(qdocument.term.goe(obj.getFromCooperationTerm()));
        }else if(obj.getToCooperationTerm() != null){
            booleanBuilder.and(qdocument.term.loe(obj.getToCooperationTerm()));
        }
        return booleanBuilder.getValue();
    }
}
