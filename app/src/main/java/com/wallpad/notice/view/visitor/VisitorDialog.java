package com.wallpad.notice.view.visitor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.wallpad.notice.R;
import com.wallpad.notice.databinding.DialogVisitorBinding;
import com.wallpad.notice.view.common.BaseDialog;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VisitorDialog extends BaseDialog {
    private VisitorDialogViewModel viewModel;
    private final String id;
    private final int place;
    private final String date;
    private final String path;
    @Inject MediaPlayer mediaPlayer;
    public VisitorDialog(String id, int place, String date, String path) {
        this.id = id;
        this.place = place;
        this.date = date;
        if ( path != null && !path.contains("file:///") ) this.path = "file://"+path;
        else this.path = path;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.LauncherDialog);
        viewModel = new ViewModelProvider(this).get(VisitorDialogViewModel.class);
        viewModel.setDate(this.date);
        viewModel.setPlace(this.place);
        viewModel.setScreen(this.path);
        viewModel.setIsMedia(path.contains("mp4"));
        DialogVisitorBinding binding = DialogVisitorBinding.inflate(LayoutInflater.from(getContext()));
        binding.setLifecycleOwner(this);
        binding.setView(this);
        binding.setVisitor(viewModel);
        if ( path.contains("mp4") ) binding.surface.getHolder().addCallback(callback);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private final SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnErrorListener(errorListener);
                mediaPlayer.setOnPreparedListener(preparedListener);
                mediaPlayer.setDataSource(path);
                mediaPlayer.setDisplay(holder);
                mediaPlayer.prepareAsync();
            } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mediaPlayer.setDisplay(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    };

    private final MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            viewModel.setIsPrepare(true);
            mp.start();
        }
    };

    private final MediaPlayer.OnErrorListener errorListener = (mp, what, extra) -> false;

    public void onClickClose() { dismiss(); }

    public void onClickDelete() {
        viewModel.deleteVisitor(id);
        dismiss();
    }

    public void onClickPlayPause() {
        if ( mediaPlayer.isPlaying() ) mediaPlayer.pause();
        else mediaPlayer.start();
    }

    public void onClickStop() {
        mediaPlayer.stop();
    }
}
