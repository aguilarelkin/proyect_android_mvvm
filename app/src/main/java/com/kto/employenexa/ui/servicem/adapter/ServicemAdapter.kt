package com.kto.employenexa.ui.servicem.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kto.employenexa.R
import com.kto.employenexa.domain.models.ServicemModel

class ServicemAdapter(private var servicemList: List<ServicemModel> = emptyList(),
                      private val updateServicemId: (String) -> Unit,
                      private val deleteServicemId: (ServicemModel) -> Unit
) :
    RecyclerView.Adapter<ServicemViewHolder>() {

    fun udateServicemList(list: List<ServicemModel>) {
        servicemList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicemViewHolder {
        return ServicemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_employe, parent, false)
        )
    }

    override fun getItemCount() = servicemList.size

    override fun onBindViewHolder(holder: ServicemViewHolder, position: Int) {
        holder.render(servicemList[position], updateServicemId, deleteServicemId)
    }
}