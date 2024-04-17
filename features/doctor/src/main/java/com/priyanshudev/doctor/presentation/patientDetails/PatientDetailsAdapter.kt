package com.priyanshudev.doctor.presentation.patientDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.priyanshudev.common.domain.model.Prescription
import com.priyanshudev.doctor.databinding.ItemPatientsHomeBinding

class PatientDetailsAdapter(private var patientHistory: List<Prescription>,) : RecyclerView.Adapter<PatientDetailsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPatientsHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientDetailsAdapter.ViewHolder {
        val binding = ItemPatientsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientDetailsAdapter.ViewHolder, position: Int) {
        val patient = patientHistory[position]
        holder.binding.apply {
            tvName.text = patient.medicines[0].medicineName
            tvDate.text = patient.date
        }
    }

    override fun getItemCount() = patientHistory.size
}