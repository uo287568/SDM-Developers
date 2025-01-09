package com.sdmdevelopers.asturspot

import android.content.Context
import android.content.res.Resources
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.mock

class FavoritosFragmentTest {

    @Mock
    lateinit var mockAuth: FirebaseAuth

    @Mock
    lateinit var mockTask: Task<AuthResult>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testSignInWithEmail_successfulLogin() = runBlocking {
        val email = "12345678@gmail.com"
        val password = "12345678"

        val mockAuthResult = Mockito.mock(AuthResult::class.java)
        Mockito.`when`(mockTask.isSuccessful).thenReturn(true)
        Mockito.`when`(mockTask.result).thenReturn(mockAuthResult)
        Mockito.`when`(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask)

        val result = mockAuth.signInWithEmailAndPassword(email, password)
        assertTrue(result.isSuccessful)
    }

    @Test
    fun testSignInWithEmail_invalidCredentials() = runBlocking {
        val email = "invalid@gmail.com"
        val password = "wrongpassword"

        Mockito.`when`(mockTask.isSuccessful).thenReturn(false) // Simula que la tarea falló
        Mockito.`when`(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask)

        val result = mockAuth.signInWithEmailAndPassword(email, password)
        assertFalse(result.isSuccessful)
    }

    @Test
    fun testSignInWithEmail_errorHandling() = runBlocking {
        val email = "error@gmail.com"
        val password = "12345678"

        // Simula que ocurre una excepción al intentar iniciar sesión
        Mockito.`when`(mockAuth.signInWithEmailAndPassword(email, password))
            .thenThrow(RuntimeException("Error de autenticación"))

        try {
            mockAuth.signInWithEmailAndPassword(email, password)
            assertFalse(true)
        } catch (e: Exception) {
            assertEquals("Error de autenticación", e.message)
        }
    }
}