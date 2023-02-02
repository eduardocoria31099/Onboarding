package com.example.onboarding.ui.fgm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.onboarding.databinding.FragmentHomeBinding
import com.example.onboarding.model.PersonEntity
import com.example.onboarding.viewmodel.ContainerViewModel
import com.example.utils.Constants.REQUEST_IMAGE_CAPTURE
import com.example.utils.ExtendedFunctions.bitmapToString
import com.example.utils.ExtendedFunctions.collect

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContainerViewModel by activityViewModels()

    private var encoded = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        setFlows()
        setListeners()
        return binding.root
    }

    private fun init() {}

    private fun setFlows() {
        collect(viewModel.state) {
            binding.btnAddPeople.isEnabled = it.visibility
        }
    }

    private fun setListeners() {
        binding.apply {
            btnTakePicture.setOnClickListener {
                validateCameraPermission()
            }
            btnAddPeople.setOnClickListener {
                viewModel.savePerson(
                    PersonEntity(
                        null,
                        encoded,
                        txtName.text.toString(),
                        txtBirthdayDay.text.toString(),
                        txtAddress.text.toString(),
                        txtPhoneNumber.text.toString(),
                        txtHobbies.text.toString(),
                    )
                )
            }
            txtName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    validate()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validate()
                }

                override fun afterTextChanged(s: Editable?) {
                    validate()
                }

            })
            txtBirthdayDay.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    validate()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validate()
                }

                override fun afterTextChanged(s: Editable?) {
                    validate()
                }

            })
            txtAddress.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    validate()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validate()
                }

                override fun afterTextChanged(s: Editable?) {
                    validate()
                }

            })
            txtPhoneNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    validate()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validate()
                }

                override fun afterTextChanged(s: Editable?) {
                    validate()
                }

            })
            txtHobbies.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    validate()
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validate()
                }

                override fun afterTextChanged(s: Editable?) {
                    validate()
                }

            })
        }
    }

    private fun validateCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            dispatchTakePictureIntent()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgPerson.setImageBitmap(imageBitmap)
            encoded = imageBitmap.bitmapToString() ?: ""
        }
        validate()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun validate() {
        binding.apply {
            viewModel.validateFields(
                encoded,
                txtName.text.toString(),
                txtBirthdayDay.text.toString(),
                txtAddress.text.toString(),
                txtPhoneNumber.text.toString(),
                txtHobbies.text.toString(),
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetMessage()
        _binding = null
    }
}