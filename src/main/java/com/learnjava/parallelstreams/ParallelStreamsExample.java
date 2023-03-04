package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.DataSet.namesList;
import static com.learnjava.util.LoggerUtil.log;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreamsExample {

    public static void main(String[] args) {

        startTimer();

        List<String> nameList = namesList();

        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

        List<String> resultList = parallelStreamsExample.stringTransform(nameList);

        log("resultList: " + resultList);

        timeTaken();

    }

    public List<String> stringTransform(List<String> nameList) {

        return nameList.parallelStream().map(this::addNameLengthTransform).collect(Collectors.toList());

    }

    public List<String> stringTransform_1(List<String> nameList, boolean isParallel) {

        Stream<String> nameStream = nameList.stream();

        if(isParallel) {

            nameStream = nameStream.parallel();

        }

        return nameStream.map(this::addNameLengthTransform).collect(Collectors.toList());

    }

    private String addNameLengthTransform(String name) {

        delay(500);
        return name.length()+" - "+name ;

    }

}
