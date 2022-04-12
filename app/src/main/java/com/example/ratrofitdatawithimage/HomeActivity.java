package com.example.ratrofitdatawithimage;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ratrofitdatawithimage.databinding.ActivityHomeBinding;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements ImageUploadCallbacks {
    private String TAG = "HomeActivity";
    private ActivityHomeBinding binding;
    File filePhoto;
    Uri uriphoto;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModel.class);

        binding.btSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
        binding.btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto(filePhoto);
            }
        });

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
//                    Glide.with(getApplicationContext())
//                            .load(uri)
//                            .centerCrop()
//                            .into(binding.ivImage);
                    filePhoto=new File(uri.getPath());;
                    uriphoto = uri;
                    binding.ivImage.setImageURI(uri);
                }
            });

    private void uploadPhoto(File file) {
        if (file != null) {
            ProgressRequestBody progressRequestBody = new ProgressRequestBody(file, "image", HomeActivity.this);
            viewModel.uploadItemImage(file, "Test", progressRequestBody)
                    .observe(this, new Observer<Response>() {
                        @Override
                        public void onChanged(Response modelUploadResponse) {
                            if (modelUploadResponse == null) {
                                Toast.makeText(HomeActivity.this, "Photo Upload Failed!!", Toast.LENGTH_SHORT).show();
                            } else if (modelUploadResponse.getSuccess().equals("Uploaded successfully")) {
                                Toast.makeText(HomeActivity.this, "Photo Upload Successfully!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Select Photo First", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        Log.d(TAG, "onProgressUpdate: " + percentage);
    }

    @Override
    public void onError() {
        Log.d(TAG, "onError: called");
    }

    @Override
    public void onFinish() {
        Log.d(TAG, "onFinish: called");
    }
}
