package com.example.myquizzesapplication.Helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myquizzesapplication.Question.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

public class ListRandomizer {

    public static <T> List<T> drawRandomList(List<T> list, int numberOfElements){//returns random list
        if(numberOfElements > list.size())
            return null;
        else if(numberOfElements == list.size()) return list;
        else{
            List<T> randomList = new ArrayList<>();

            Random random = new Random();
            int randomValue;

            for(int i =numberOfElements-1;i>=0;i--){
                randomValue = random.nextInt(list.size());
                randomList.add(list.get(randomValue));
                list.remove(randomValue);
            }

            return randomList;
        }
    }
}
