package com.innowise.document.service.filters;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.ContractOfSale;
import com.innowise.document.entity.documents.QContractOfSale;
import com.innowise.document.repository.documents.ContractOfSaleRepo;
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
public class FilterSaleServiceImpl implements FilterService<ContractOfSale>{

    @Autowired
    ContractOfSaleRepo contractRepo;

    @Override
    public Page<ContractOfSale> findAllByFilter(FilterObject obj, int page, int size) throws ParseException{
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(obj.getSortOrder()), obj.getSortField()));
        Page<ContractOfSale> list =  contractRepo.findAll(createPredicate(obj) ,pageable);
        return list;
    }

    @Override
    public Predicate createPredicate(FilterObject obj) throws ParseException{
        QContractOfSale qdocument = QContractOfSale.contractOfSale;
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
            System.out.println("exp " + obj.getIsActive());
        }
        if (!StringUtils.isEmpty(obj.getSaleObject())) {
            booleanBuilder.and(qdocument.saleObject.containsIgnoreCase(obj.getSaleObject()));
        }
        if (obj.getFromSalingPrice() != null && obj.getToSalingPrice() != null) {
            booleanBuilder.and(qdocument.salingPrice.between(obj.getFromSalingPrice(), obj.getToSalingPrice()));
        }else if (obj.getFromSalingPrice() != null) {
            booleanBuilder.and(qdocument.salingPrice.goe(obj.getFromSalingPrice()));
        }else if(obj.getToSalingPrice() != null){
            booleanBuilder.and(qdocument.salingPrice.loe(obj.getToSalingPrice()));
        }
        if (obj.getFromWarrantyPeriod() != null && obj.getToWarrantyPeriod() != null) {
            booleanBuilder.and(qdocument.warrantyPeriod.between(obj.getFromWarrantyPeriod(), obj.getToWarrantyPeriod()));
        }else if (obj.getFromWarrantyPeriod() != null) {
            booleanBuilder.and(qdocument.warrantyPeriod.goe(obj.getFromWarrantyPeriod()));
        }else if(obj.getToWarrantyPeriod() != null){
            booleanBuilder.and(qdocument.warrantyPeriod.loe(obj.getToWarrantyPeriod()));
        }
        return booleanBuilder.getValue();
    }
}
