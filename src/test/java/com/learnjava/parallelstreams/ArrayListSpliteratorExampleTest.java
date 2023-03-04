package com.learnjava.parallelstreams;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.learnjava.util.DataSet.generateArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayListSpliteratorExampleTest {

    private ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue() {

        int listSize = 1_000_000;

        ArrayList<Integer> inputList = generateArrayList(listSize);

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        assertEquals(listSize, resultList.size());

    }

    @RepeatedTest(5)
    void multiplyEachValue_Parallel() {

        int listSize = 1_000_000;

        ArrayList<Integer> inputList = generateArrayList(listSize);

        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        assertEquals(listSize, resultList.size());

    }

}