package com.example.ratrofitdatawithimage;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ratrofitdatawithimage.databinding.ActivityHomeBinding;

import java.io.File;

import io.reactivex.annotations.NonNull;

public class HomeActivity extends AppCompatActivity implements ImageUploadCallbacks {
    private String TAG = "HomeActivity";
    private ActivityHomeBinding binding;
    File filePhoto;
    Uri uriphoto;
    ViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewModel.class);

        binding.btSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestStoragePermission()){
                    mGetContent.launch("image/*");
                }

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
                    filePhoto = new File(uri.getPath());
                    ;
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
                            Log.d(TAG, "onChanged: " + modelUploadResponse);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean requestStoragePermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "requestStoragePermission: sob permission ase");
            return true;
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: permissions size=" + permissions.length + "grand results size:" + grantResults.length);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: permission granted.");
            } else {
                Toast.makeText(HomeActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}