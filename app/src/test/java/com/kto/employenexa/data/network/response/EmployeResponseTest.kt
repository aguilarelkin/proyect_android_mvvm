package com.kto.employenexa.data.network.response

import com.kto.employenexa.data.motherobject.EmployeMotherObject.anyResponse
import com.kto.employenexa.domain.models.RoleModel
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EmployeResponseTest {

    @Test
    fun `toDomain Would return a correct EmployeModel`() {
        //given
        val employeResponse = anyResponse
        //when
        val employeModel = employeResponse.toDomain()
        //then
        employeModel.id shouldBe employeResponse.id
        employeModel.firstname shouldBe employeResponse.firstname
        employeModel.lastname shouldBe employeResponse.lastname
        employeModel.mail shouldBe employeResponse.mail
        employeModel.password shouldNotBe employeResponse.password
        employeModel.avatar shouldBe employeResponse.avatar
        employeModel.base shouldBe employeResponse.base
        employeModel.role shouldNotBe employeResponse.role
        employeModel.telephone shouldBe employeResponse.telephone

        assertEquals(employeModel.password, "")
        assertEquals(
            employeModel.role,
            listOf(RoleModel(1, "ROLE_ADMIN"), RoleModel(2, "ROLE_USER"))
        )
        assertTrue(employeModel.mail, employeModel.mail.contains("@"))
    }

    @Test
    fun `the password would have min 5 character`() {
        val employeResponse = anyResponse

        val employeModel = employeResponse.toDomain().copy(password = "12345")

        assertTrue(employeModel.password.length > 4)
    }

}