package com.nikunj.statussaver.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikunj.statussaver.R;
import com.nikunj.statussaver.adapter.VideoAdapter;
import com.nikunj.statussaver.model.Status;
import com.nikunj.statussaver.utils.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VideoFragment extends Fragment {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final List<Status> videoList = new ArrayList<>();
    private final Handler handler = new Handler();
    private VideoAdapter videoAdapter;
    private RelativeLayout container;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView messageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerViewVideo);
        progressBar = view.findViewById(R.id.prgressBarVideo);
        container = view.findViewById(R.id.videos_container);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        messageTextView = view.findViewById(R.id.messageTextVideo);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark)
                , ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
                ContextCompat.getColor(requireActivity(), R.color.whats_App),
                ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark));

        swipeRefreshLayout.setOnRefreshListener(this::getStatus);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Common.GRID_COUNT));

        getStatus();

        super.onViewCreated(view, savedInstanceState);
    }

    private void getStatus() {

        if (Common.STATUS_DIRECTORY.exists()) {

            new Thread(() -> {
                File[] statusFiles = Common.STATUS_DIRECTORY.listFiles();
                videoList.clear();

                if (statusFiles != null && statusFiles.length > 0) {

                    Arrays.sort(statusFiles);
                    for (File file : statusFiles) {
                        Status status = new Status(file, file.getName(), file.getAbsolutePath());

                        if (status.isVideo()) {
                            videoList.add(status);
                        }

                    }

                    handler.post(() -> {

                        if (videoList.size() <= 0) {
                            messageTextView.setVisibility(View.VISIBLE);
                            messageTextView.setText(R.string.no_files_found);
                        } else {
                            messageTextView.setVisibility(View.GONE);
                            messageTextView.setText("");
                        }

                        videoAdapter = new VideoAdapter(videoList, container);
                        recyclerView.setAdapter(videoAdapter);
                        videoAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    });

                } else {

                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        messageTextView.setVisibility(View.VISIBLE);
                        messageTextView.setText(R.string.no_files_found);
                        Toast.makeText(getActivity(), getString(R.string.no_files_found), Toast.LENGTH_SHORT).show();
                    });

                }
                swipeRefreshLayout.setRefreshing(false);
            }).start();

        } else {
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(R.string.cant_find_whatsapp_dir);
            Toast.makeText(getActivity(), getString(R.string.cant_find_whatsapp_dir), Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

}
