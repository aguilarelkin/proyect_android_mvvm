package com.kto.employenexa.ui.servicem.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kto.employenexa.databinding.ItemEmployeBinding
import com.kto.employenexa.domain.models.ServicemModel
import com.squareup.picasso.Picasso

class ServicemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemEmployeBinding.bind(view)

    fun render(
        servicemModel: ServicemModel,
        updateServicemId: (String) -> Unit,
        deleteServicemId: (ServicemModel) -> Unit
    ) {
        Picasso.get().load("https://ui-avatars.com/api/?name=" + servicemModel.service)
            .into(binding.ivAvatar)
        binding.nameEmploye.text = servicemModel.service
        binding.ibUpdate.setOnClickListener { updateServicemId(servicemModel.id.toString()) }
        binding.ibDelete.setOnClickListener { deleteServicemId(servicemModel) }

    }
}