package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform() {

        startTimer();

        List<String> inputList = DataSet.namesList();

        List<String> resultList = parallelStreamsExample.stringTransform(inputList);

        assertEquals(inputList.size(), resultList.size());
        resultList.forEach(result -> assertTrue(result.contains("-")));

        timeTaken();

    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void stringTransform_1(boolean isParallel) {

        startTimer();

        List<String> inputList = DataSet.namesList();

        List<String> resultList = parallelStreamsExample.stringTransform_1(inputList, isParallel);

        assertEquals(inputList.size(), resultList.size());
        resultList.forEach(result -> assertTrue(result.contains("-")));

        timeTaken();

    }

}