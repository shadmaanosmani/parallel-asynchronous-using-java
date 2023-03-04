package com.learnjava.parallelstreams;

import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static com.learnjava.util.DataSet.generateIntegerLinkedList;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    private final LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    void multiplyEachValue() {

        int listSize = 1_000_000;

        LinkedList<Integer> inputList = generateIntegerLinkedList(listSize);

        List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, false);

        assertEquals(listSize, resultList.size());

    }

    @RepeatedTest(5)
    void multiplyEachValue_Parallel() {

        int listSize = 1_000_000;

        LinkedList<Integer> inputList = generateIntegerLinkedList(listSize);

        List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, true);

        assertEquals(listSize, resultList.size());

    }

}