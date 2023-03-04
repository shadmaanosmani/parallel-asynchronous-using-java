package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

@AllArgsConstructor
public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList;
        List<String> names = DataSet.namesList();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        resultList = forkJoinPool.invoke(forkJoinUsingRecursion);

        log("names : "+ names);

        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }

    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }

    @Override
    protected List<String> compute() {

        if(inputList.size() <= 1) {

            List<String> resultList = new ArrayList<>();

            inputList.forEach(input -> resultList.add(addNameLengthTransform(input)));

            return resultList;

        }

        int midPoint = inputList.size() / 2;

        ForkJoinTask<List<String>> leftResultFork = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();

        inputList = inputList.subList(midPoint, inputList.size());

        List<String> rightResultList = compute();
        List<String> lefftResultList = leftResultFork.join();

        lefftResultList.addAll(rightResultList);

        return lefftResultList;

    }

}
