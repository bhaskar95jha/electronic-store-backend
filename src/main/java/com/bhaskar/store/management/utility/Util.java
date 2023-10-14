package com.bhaskar.store.management.utility;

import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.dtos.UserDto;
import com.bhaskar.store.management.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Util {


    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> pages, Class<V> type){
       //u is your entity and v is dto
        List<U> entity = pages.getContent();
        List<V> dtoList = entity.stream().map((object) -> new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(pages.getNumber());
        response.setPageSize(pages.getSize());
        response.setTotalElements(pages.getTotalElements());
        response.setTotalPages(pages.getTotalPages());
        response.setLastPage(pages.isLast());
        return response;
    }
}
