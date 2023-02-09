package com.ccoins.coins.utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapperUtils {

    public static <D> D map(Object obj, Class<D> cla){
        ModelMapper mapper =  new ModelMapper();
        return mapper.map(obj,cla);
    }

    public static List<Object> mapList(List list, Class<?> cla){

        List<Object> responseList = new ArrayList<>();
        list.forEach(o -> responseList.add(MapperUtils.map(o,cla)));
        return responseList;
    }
}
