package com.kto.employenexa.ui.employe.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kto.employenexa.databinding.ItemEmployeBinding
import com.kto.employenexa.domain.models.EmployeModel
import com.squareup.picasso.Picasso

class EmployeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    //bindig para el item employe
    private val binding = ItemEmployeBinding.bind(view)

    fun render(
        employeModel: EmployeModel,
        updateEmployeId: (String) -> Unit,
        deleteEmployeId: (EmployeModel) -> Unit
    ) {
        Picasso.get().load("https://ui-avatars.com/api/?name=" + employeModel.avatar)
            .into(binding.ivAvatar)

        binding.nameEmploye.text = employeModel.firstname
        binding.ibUpdate.setOnClickListener { updateEmployeId(employeModel.id.toString()) }
        binding.ibDelete.setOnClickListener {
            deleteEmployeId(
                employeModel
            )
        }
    }
}