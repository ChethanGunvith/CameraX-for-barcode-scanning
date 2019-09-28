package com.chethan.camerax.view.ScanTheQRCode

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chethan.camerax.R
import com.chethan.camerax.binding.FragmentDataBindingComponent
import com.chethan.camerax.cameraXutils.CameraConfiguration
import com.chethan.camerax.cameraXutils.QrCodeAnalyzer
import com.chethan.camerax.databinding.ScanQrCodeFragmentBinding
import com.chethan.camerax.di.Injectable
import com.chethan.camerax.testing.OpenForTesting
import com.chethan.camerax.utils.autoCleared

/**
 * Created by Chethan on 9/17/2019.
 */

@OpenForTesting
class ScanQRCodeFragment : Fragment(), Injectable {

    private lateinit var config: CameraConfiguration

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ScanQrCodeFragmentBinding>()

    var isQRCodeRecognized = false;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.scan_qr_code_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }


    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(context as Activity, Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                binding.textureView.post { setupCamera() }
            } else {
                Toast.makeText(context, "Camera permission is required.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isCameraPermissionGranted()) {
            binding.textureView.post { setupCamera() }
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CameraX.unbindAll()
    }

    private fun setupCamera() {
        isQRCodeRecognized = false
        val metrics = DisplayMetrics().also { binding.textureView.display.getRealMetrics(it) }
        config = CameraConfiguration(
            aspectRatio = Rational(metrics.widthPixels, metrics.heightPixels),
            rotation = binding.textureView.display.rotation,
            resolution = Size(metrics.widthPixels, metrics.heightPixels)
        )

        CameraX.unbindAll()
        CameraX.bindToLifecycle(
            this,
            buildPreviewUseCase(),
            buildImageCaptureUseCase(),
            buildImageAnalysisUseCase()
        )
    }

    private fun buildPreviewUseCase(): Preview {
        val previewConfig = PreviewConfig.Builder()
            .setTargetAspectRatio(config.aspectRatio)
            .setTargetRotation(config.rotation)
            .setTargetResolution(config.resolution)
            .setLensFacing(config.lensFacing)
            .build()
        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            val parent = binding.textureView.parent as ViewGroup
            parent.removeView(binding.textureView)
            parent.addView(binding.textureView, 0)
            binding.textureView.surfaceTexture = previewOutput.surfaceTexture

            // Compute the center of the view finder
            val centerX = binding.textureView.width / 2f
            val centerY = binding.textureView.height / 2f

            // Correct preview output to account for display rotation
            val rotationDegrees = when (binding.textureView.display.rotation) {
                Surface.ROTATION_0 -> 0
                Surface.ROTATION_90 -> 90
                Surface.ROTATION_180 -> 180
                Surface.ROTATION_270 -> 270
                else -> return@setOnPreviewOutputUpdateListener
            }

            val matrix = Matrix()
            matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

            // Finally, apply transformations to our TextureView
            binding.textureView.setTransform(matrix)
        }

        return preview
    }

    private fun buildImageCaptureUseCase(): ImageCapture {
        val captureConfig = ImageCaptureConfig.Builder()
            .setTargetAspectRatio(config.aspectRatio)
            .setTargetRotation(config.rotation)
            .setTargetResolution(config.resolution)
            .setFlashMode(config.flashMode)
            .setCaptureMode(config.captureMode)
            .build()
        val capture = ImageCapture(captureConfig)
        return capture
    }

    private fun buildImageAnalysisUseCase(): ImageAnalysis {

        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .build()
        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)
        val qrCodeAnalyzer = QrCodeAnalyzer { qrCodes ->
            qrCodes.forEach {
                Log.d("ScanQRCodeFragment", "QR Code detected: ${it.rawValue}. ")
                if (!isQRCodeRecognized) {
                    isQRCodeRecognized = true
                    navController().navigate(
                        ScanQRCodeFragmentDirections.showOrderList(it.rawValue.toString())
                    )
                }
            }
        }
        imageAnalysis.analyzer = qrCodeAnalyzer
        return imageAnalysis
    }


    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10
    }


    fun navController() = findNavController()

}