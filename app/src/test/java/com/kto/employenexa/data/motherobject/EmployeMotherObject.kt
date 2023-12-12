package com.kto.employenexa.data.motherobject

import com.kto.employenexa.data.motherobject.RoleMotherObject.anyRoleResponse
import com.kto.employenexa.data.network.response.EmployeResponse

object EmployeMotherObject {

    val anyResponse = EmployeResponse(
        1,
        "Natalia",
        "Paz",
        "natalia@gmail.com",
        "12345",
        "natalia.jpg",
        "develoment",
        anyRoleResponse,
        "3124349059"
    )

}