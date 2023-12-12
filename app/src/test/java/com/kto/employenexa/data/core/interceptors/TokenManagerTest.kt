package com.kto.employenexa.data.core.interceptors

import com.kto.employenexa.data.core.datastore.DataInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class TokenManagerTest {

    @MockK
    private lateinit var dataInfo: DataInfo

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `getToken return token when not null`() {
        val token = "adad.qwewqeq.qeqeq"
       coEvery  { dataInfo.getSettings() } returns flowOf(token)
        val data = runBlocking {
            dataInfo.getSettings().first()
        }
        assertNotNull(data)
    }


}
