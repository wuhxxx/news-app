package com.laioffer.tinnews.common;

import android.os.Bundle;

public interface TinFragmentManager {
    void doFragmentTransaction(TinBasicFragment basicFragment);

    void startActivityWithBundle(Class<?> clazz, boolean isFinished, Bundle bundle);

    void showSnackBar(String message);
}
