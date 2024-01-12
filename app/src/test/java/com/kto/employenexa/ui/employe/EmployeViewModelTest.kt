package com.kto.employenexa.ui.employe

import com.kto.employenexa.data.motherobject.EmployeMotherObject
import com.kto.employenexa.domain.usecase.employe.DeleteEmploye
import com.kto.employenexa.domain.usecase.employe.GetEmploye
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmployeViewModelTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: EmployeViewModel

    @MockK
    private lateinit var getEmploye: GetEmploye

    @MockK
    private lateinit var deleteEmploye: DeleteEmploye

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }


    @Test
    fun `when response is not null getEmployes should val result return state Success of list employeModel`() {
        viewModel = EmployeViewModel(getEmploye, deleteEmploye)
        coEvery { getEmploye() } returns listOf(EmployeMotherObject.anyResponse.toDomain())
        viewModel.getEmployes()
        runBlocking {
            coVerify { getEmploye() }
            assertEquals(
                viewModel.state.value,
                EmployeState.Success(listOf(EmployeMotherObject.anyResponse.toDomain()))
            )
        }
    }

    @Test
    fun `when response is null getEmployes should val result return state Error of String`() {
        viewModel = EmployeViewModel(getEmploye, deleteEmploye)
        runBlocking {
            coEvery { getEmploye() } returns null
            viewModel.getEmployes()
            runBlocking {
                coVerify { getEmploye() }
                assertEquals(viewModel.state.value, EmployeState.Error("Error :(("))
            }
        }
       
    }

    @Test
    fun `when response is corret deleteEmployeId should return state Success of employeModel`() {
        viewModel = EmployeViewModel(getEmploye, deleteEmploye)
        val id = "1"
        runBlocking {
            coEvery { deleteEmploye(id) } returns "Delete Correct"
            coEvery { getEmploye() } returns listOf(EmployeMotherObject.anyResponse.toDomain())
            viewModel.deleteEmployeId(id)
        }
        runBlocking {
            coVerify { deleteEmploye(id) }
            coVerify { getEmploye() }
            assertEquals(
                viewModel.state.value,
                EmployeState.Success(listOf(EmployeMotherObject.anyResponse.toDomain()))
            )
        }
    }
}
