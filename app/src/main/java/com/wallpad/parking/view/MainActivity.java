package com.wallpad.parking.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Relation;


import com.wallpad.IWallpadData;
import com.wallpad.parking.R;
import com.wallpad.parking.databinding.ActivityMainBinding;
import com.wallpad.parking.repository.Repository;
import com.wallpad.parking.view.common.BaseActivity;
import com.wallpad.parking.view.parking.ParkingFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    @Inject
    Repository repository;
    private ActivityMainBinding binding;
    boolean bound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        addFragment(new ParkingFragment(), R.id.content);
        bindIGSmartService();
    }

    @Override
    protected void onDestroy() {
        unBindIGSmartService();
        super.onDestroy();
    }

    public void onClick(View view) { finish(); }

    protected void addFragment(Fragment fragment, int resID) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(resID, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private void bindIGSmartService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.wallpad.service", "com.wallpad.service.KDService"));
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unBindIGSmartService() {
        if ( !bound ) return;
        this.unbindService(serviceConnection);
        bound = false;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            repository.injectIWallpadService(IWallpadData.Stub.asInterface(service));
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            repository.injectIWallpadService(null);
            bound = false;
        }
    };
}
