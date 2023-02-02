package com.example.onboarding.ui.fgm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onboarding.databinding.FragmentListBinding
import com.example.onboarding.model.PersonEntity
import com.example.onboarding.ui.adapter.PersonAdapter
import com.example.onboarding.ui.adapter.SwipeGesture
import com.example.onboarding.ui.dlg.DialogPerson
import com.example.onboarding.viewmodel.ContainerViewModel
import com.example.utils.ExtendedFunctions.collect
import com.example.utils.ExtendedFunctions.toast
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var personAdapter: PersonAdapter
    private val viewModel: ContainerViewModel by activityViewModels()

    private lateinit var listPerson: List<PersonEntity>
    private var person = PersonEntity(null, "", "", "", "", "", "")

    private lateinit var dialogPerson: DialogPerson


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        init()
        setFlows()
        setListeners()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners() {
        binding.apply {
            val count = personAdapter.itemCount
            tvNumberPeople.text = "Total $count person"
        }
    }

    private fun setFlows() = lifecycleScope.launch {
        collect(viewModel.inventory) {
            listPerson = it
            personAdapter.addAll(it)
            validateCont()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun validateCont() = with(binding) {
        val count = personAdapter.itemCount
        tvNumberPeople.text = "Total $count person"
        if (count == 0) {
            emptyState.root.visibility = View.VISIBLE
        } else {
            emptyState.root.visibility = View.GONE
        }
    }

    private fun init() {
        setAdapter()
        setRecycler()
    }

    private fun setAdapter() {

        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        requireContext().toast("LEFT")
                        personAdapter.addAll(listPerson)
                    }
                    ItemTouchHelper.RIGHT -> {
                        requireContext().toast("RIGHT")
                        personAdapter.addAll(listPerson)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.recyclerView)
        personAdapter = PersonAdapter(emptyList()) {
            person = it
            dialogPerson = DialogPerson(requireContext(), person)
            showDialogPerson()
        }
    }

    private fun showDialogPerson() {
        dialogPerson.setOnOptionSelectedListener(object : DialogPerson.OnOptionSelectedListener {
            override fun onCancelOptionSelected() {
                dialogPerson.hide()
            }
        })
        dialogPerson.show()
    }

    private fun setRecycler() = with(binding) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = personAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}