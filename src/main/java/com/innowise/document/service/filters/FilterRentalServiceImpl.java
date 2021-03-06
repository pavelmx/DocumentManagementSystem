package com.innowise.document.service.filters;

import com.innowise.document.entity.FilterObject;
import com.innowise.document.entity.documents.QRentalContract;
import com.innowise.document.entity.documents.RentalContract;
import com.innowise.document.repository.documents.RentalContractRepo;
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
public class FilterRentalServiceImpl implements FilterService<RentalContract>{

    @Autowired
    RentalContractRepo contractRepo;

    @Override
    public Page<RentalContract> findAllByFilter(FilterObject obj, int page, int size) throws ParseException{
        Pageable pageable =  PageRequest.of(page, size, new Sort(Sort.Direction.fromString(obj.getSortOrder()), obj.getSortField()));
        Page<RentalContract> list =  contractRepo.findAll(createPredicate(obj), pageable);
        return list;
    }

    @Override
    public Predicate createPredicate(FilterObject obj) throws ParseException{
        QRentalContract qdocument = QRentalContract.rentalContract;
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
        if (obj.getFromRentalPrice() != null && obj.getToRentalPrice() != null) {
            booleanBuilder.and(qdocument.rentalPrice.between(obj.getFromRentalPrice(), obj.getToRentalPrice()));
        }else if (obj.getFromRentalPrice() != null) {
            booleanBuilder.and(qdocument.rentalPrice.goe(obj.getFromRentalPrice()));
        }else if(obj.getToRentalPrice() != null){
            booleanBuilder.and(qdocument.rentalPrice.loe(obj.getToRentalPrice()));
        }
//        if (!StringUtils.isEmpty(obj.getFromRental()) && !StringUtils.isEmpty(obj.getToRental())) {
//            booleanBuilder.and(qdocument.dateOfCreation.between(
//                    new SimpleDateFormat("yyyy-MM-dd").parse(obj.getFromRental()),
//                    new SimpleDateFormat("yyyy-MM-dd").parse(obj.getToRental())));
//        }else
            if (!StringUtils.isEmpty(obj.getFromRental())) {
            booleanBuilder.and(qdocument.startRental.after(Date.valueOf(LocalDate.parse(obj.getFromRental()).minusDays(1).toString())));
        }
//            else
            if(!StringUtils.isEmpty(obj.getToRental())){
            booleanBuilder.and(qdocument.endRental.before(Date.valueOf(LocalDate.parse(obj.getToRental()).plusDays(1).toString())));
        }
        if (!StringUtils.isEmpty(obj.getRentalObject())) {
            booleanBuilder.and(qdocument.rentalObject.containsIgnoreCase(obj.getRentalObject()));
        }
        return booleanBuilder.getValue();
    }
}
