package com.example.denis.runtracker;

import android.support.v4.app.Fragment;

/**
 * Created by Denis on 22.12.2015.
 */
public class RunMapActivity extends SingleFragActForMap {
    /** Ключ для передачи идентификатора серии в формате long */
    public static final String EXTRA_RUN_ID =
            "com.bignerdranch.android.runtracker.run_id";
    @Override
    protected Fragment createFragment() {
        long runId = getIntent().getLongExtra(EXTRA_RUN_ID, -1);
        if (runId != -1) {
            return RunMapFragment.newInstance(runId);
        } else {
            return new RunMapFragment();
        }
    }
    }