package com.learnjava.parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiple, boolean isParallel) {

        startTimer();

        Stream<Integer> inputStream = inputList.stream();

        if(isParallel) {

           inputStream.parallel();

        }

        List<Integer> resultList = inputStream.map(input -> input * multiple).collect(Collectors.toList());

        timeTaken();

        return resultList;

    }

}
