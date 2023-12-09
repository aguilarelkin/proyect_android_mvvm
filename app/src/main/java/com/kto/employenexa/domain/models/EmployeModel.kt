package com.kto.employenexa.domain.models

data class EmployeModel(
    val id: Int,
    val firstname: String,
    val lastname: String,
    //
    val password: String,
    val mail: String,
    val avatar: String,
    val base: String,
    val role: List<RoleModel>,
    val telephone: String
)
