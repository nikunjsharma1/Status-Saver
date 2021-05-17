package com.nikunj.statussaver.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikunj.statussaver.R;
import com.nikunj.statussaver.model.Status;
import com.nikunj.statussaver.ui.ImageView;
import com.nikunj.statussaver.utils.Common;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private final List<Status> imagesList;
    private Context context;
    private final RelativeLayout container;

    public ImageAdapter(List<Status> imagesList, RelativeLayout container) {
        this.imagesList = imagesList;
        this.container = container;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {

        final Status status = imagesList.get(position);
        Picasso.get().load(status.getFile()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String path = imagesList.get(position).getPath();

                Intent intent=new Intent(context, ImageView.class);
                intent.putExtra("file",path);
                context.startActivity(intent);
            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFolder();
                final String path = imagesList.get(position).getPath();
                final File file = new File(path);
                String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Common.APP_DIR;
                final File destFile = new File(destPath);
                try {
                    FileUtils.copyFileToDirectory(file, destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MediaScannerConnection.scanFile(context, new String[]{destPath + status.getTitle()},
                        new String[]{"*/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });
                Toast.makeText(context, "Saved to:" + destPath + status.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/jpg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.getFile().getAbsolutePath()));
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });


    }

    private void checkFolder() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Common.APP_DIR;
        File dir = new File(path);
        boolean isDirectoryCreated = dir.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = dir.mkdir();

        } else {
            Log.d("nikunjii", "created");
        }
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
