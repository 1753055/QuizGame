package com.example.group09ktpm;

import java.util.List;

public interface MyCallback {
    void MyCallback(List<Question> questions);
    void MyCallback2(List<String> categories, List<Integer> hardLevel);
    void MyCallback3(boolean Admin, String username);
}
