package com.kto.employenexa.ui.employe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kto.employenexa.R
import com.kto.employenexa.domain.models.EmployeModel

class EmployeAdapter(
    private var employList: List<EmployeModel> = emptyList(),
    private val updateEmployeId: (String) -> Unit,
    private val deleteEmployeId: (EmployeModel) -> Unit
) :
    RecyclerView.Adapter<EmployeViewHolder>() {

    fun updateList(list: List<EmployeModel>) {
        employList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeViewHolder {
        return EmployeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_employe, parent, false)
        )
    }//este crea la instacia de employeViewHolder

    override fun getItemCount() = employList.size

    override fun onBindViewHolder(holder: EmployeViewHolder, position: Int) {
        holder.render(employList[position], updateEmployeId, deleteEmployeId)
    }//le dice al view folder que pintar
}