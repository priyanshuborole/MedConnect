package com.priyanshudev.doctor.presentation.scanner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.priyanshudev.doctor.R
import com.priyanshudev.doctor.databinding.FragmentScanQrBinding
import com.priyanshudev.doctor.presentation.home.components.HomeScreen
import kotlin.random.Random

class ScanQrFragment : Fragment() {

    private lateinit var binding: FragmentScanQrBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanQrBinding.inflate(layoutInflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    ScanQr()
                }
            }
        }
        return binding.root
    }
}

@Composable
fun ScanQr() {
    var scanFlag by remember { mutableStateOf(false) }
    var showResult by remember { mutableStateOf(false) }
    var lastReadBarcode by remember { mutableStateOf<String?>(null) }
    var torchState by remember { mutableStateOf(false) }
    var recomposeFlag by remember { mutableIntStateOf(Random.nextInt()) }
    key (recomposeFlag){
        AndroidView(
            factory = { context ->
                val preview = CompoundBarcodeView(context)
                preview.setStatusText("")
                preview.cameraSettings.isAutoTorchEnabled = torchState
                preview.apply {
                    val capture = CaptureManager(context as Activity, this)
                    capture.initializeFromIntent(context.intent, null)
                    capture.decode()
                    this.decodeContinuous { result ->
                        if (scanFlag) {
                            return@decodeContinuous
                        }
                        scanFlag = true
                        result.text?.let { barCodeOrQr ->
                            lastReadBarcode = result.text
                            scanFlag = true
                            showResult = true
                            Log.d("PRIYANSHU", "ScanQr: ${result.text}")
                        }
                    }
                    this.resume()
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
    if (showResult) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Scanned QR Code: $lastReadBarcode", fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    // Back button shows the scanner again
                    showResult = false
                    scanFlag = false
                    // Reset recompose flag to recompose AndroidView
                    recomposeFlag = Random.nextInt()
                }) {
                    Text(text = "Back")
                }
                Button(onClick = {
                    // Add button adds the scanned text
                    // You can handle the addition logic here
                    Toast.makeText(context, "Added: $lastReadBarcode", Toast.LENGTH_SHORT).show()
                    // Optionally, reset the state
                    showResult = false
                    scanFlag = false
                    recomposeFlag = Random.nextInt()
                }) {
                    Text(text = "Add")
                }
            }
        }
    }
}

@Composable
fun ScanQrScreen() {
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        if (hasCamPermission) {
            AndroidView(
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                            Log.d("PRIYANSHU", "ScanQrScreen: result - $result")
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Text(
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }
    }
}
