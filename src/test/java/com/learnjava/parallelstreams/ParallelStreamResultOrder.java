package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {

    public static List<Integer> listOrder(List<Integer> inputList) {

        return inputList.parallelStream().map(input -> input * 2).collect(Collectors.toList());

    }

    public static Set<Integer> setOrder(Set<Integer> inputList) {

        return inputList.parallelStream().map(input -> input * 2).collect(Collectors.toSet());

    }

    public static void main(String[] args) {

        List<Integer> inputList = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        log("inputList: " + inputList);

        List<Integer> resultList = listOrder(inputList);

        log("inputList: " + resultList);

        Set<Integer> inputSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8);

        log("inputSet: " + inputSet);

        Set<Integer> resultSet = setOrder(inputSet);

        log("resultSet: " + resultSet);

    }

}
