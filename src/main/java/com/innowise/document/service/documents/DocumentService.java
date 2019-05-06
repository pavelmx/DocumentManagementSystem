package com.innowise.document.service.documents;

import com.innowise.document.entity.documents.DocumentPattern;
import com.innowise.document.service.RestService;
import org.springframework.data.domain.Page;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public interface DocumentService<T extends DocumentPattern> extends RestService<T> {

    Page<T> getAllByUsername(String username, int page, int size);

    Page<T> getAllPage(int page, int size);

    List<T> getAllByUsername(String username);

    T addByUsername(String username, T t);

    T updateByUsername(String username, T t);

    void deleteAll();

    default long checkDiffDate(Date old, Date today){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate oldLocal = LocalDate.parse(sdf.format(old));
        LocalDate todayLocal = LocalDate.parse(sdf.format(today));
        long di = ChronoUnit.DAYS.between(oldLocal, todayLocal);
        return di;
    }
}
