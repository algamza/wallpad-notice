package com.wallpad.notice.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.IWallpadData;
import com.wallpad.notice.R;
import com.wallpad.notice.databinding.ActivityMainBinding;
import com.wallpad.notice.repository.Repository;
import com.wallpad.notice.view.common.BaseActivity;
import com.wallpad.notice.view.notice.delivery.DeliveryFragment;
import com.wallpad.notice.view.notice.notification.NotificationFragment;
import com.wallpad.notice.view.notice.vote.VoteFragment;
import com.wallpad.notice.view.notice.visitor.VisitorFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private static final String SETTINGS_NEW_MESSAGE_COUNT = "com.wallpad.settings.massage";
    @Inject
    Repository repository;
    private MainViewModel viewModel;
    boolean bound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setNotice(viewModel);
        binding.setLifecycleOwner(this);
        addFragment(new NotificationFragment(), R.id.notice_view);
        viewModel.getMode().observe(this, mode -> {
            switch (mode) {
                case NOTIFICATION: replaceFragment(new NotificationFragment(), R.id.notice_view); break;
                case REFERENDUM: replaceFragment(new VoteFragment(), R.id.notice_view); break;
                case DELIVERY: replaceFragment(new DeliveryFragment(), R.id.notice_view); break;
                case VISTOR: replaceFragment(new VisitorFragment(), R.id.notice_view); break;
            }
        });
        viewModel.getNotificationNewCount().observe(this, count -> updateNewMessageCount());
        viewModel.getDeliveryNewCount().observe(this, count -> updateNewMessageCount());
        viewModel.getReferendumNewCount().observe(this, count -> updateNewMessageCount());
        viewModel.getVisitorNewCount().observe(this, count -> updateNewMessageCount());
        bindIGSmartService();
    }

    @Override
    protected void onDestroy() {
        unBindIGSmartService();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void updateNewMessageCount() {
        Settings.Global.putInt(getContentResolver(), SETTINGS_NEW_MESSAGE_COUNT,
                viewModel.getNotificationCount()+
                        viewModel.getReferendumCount()+
                        viewModel.getDeliveryCount()+
                        viewModel.getVisitorCount());
    }

    private void replaceFragment(Fragment fragment, int res) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(res, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected void addFragment(Fragment fragment, int resID) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(resID, fragment);
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    public void onClick(View view) {
        finish();
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
