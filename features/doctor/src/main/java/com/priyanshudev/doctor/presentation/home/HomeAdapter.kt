package com.priyanshudev.doctor.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priyanshudev.common.domain.model.patient.Patient
import com.priyanshudev.doctor.databinding.ItemPatientsHomeBinding

class HomeAdapter(private var patientList: List<Patient>,
                  private val listener: ItemClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: ItemPatientsHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        Log.d("VIBHUTI", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        val binding = ItemPatientsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() = patientList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        Log.d("VIBHUTI", "onBindViewHolder() called with: holder = $holder, position = $position")
        val patient = patientList[position]
        holder.binding.apply {
            tvName.text = patient.name
            tvId.text = "Id: ${patient.id}"
            tvDate.text = patient.date.toString()
            holder.itemView.setOnClickListener {
                listener.onItemClick(patient)
            }
        }
    }
}

interface ItemClickListener {
    fun onItemClick(patient: Patient)
}