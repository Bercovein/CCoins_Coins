package com.ccoins.coins.utils;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PaginateUtils<T> {

    public PageImpl<T> paginate(List<T> list, Pageable pageable){

        int pageSize;
        int currentPage = 0;
        List<T> newList;

        if(pageable != null){

            pageSize = pageable.getPageSize();
            currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;

            if (list.size() < startItem) {
                newList = Collections.emptyList();
            } else {

                int toIndex = Math.min(startItem + pageSize, list.size());
                newList = list.subList(startItem, toIndex);
            }

        }else{
            pageSize = list.size();
            newList = list;
        }

        return new PageImpl<>(newList, PageRequest.of(currentPage, pageSize), list.size());

    }

}
