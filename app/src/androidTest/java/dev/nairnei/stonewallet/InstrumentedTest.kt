package dev.nairnei.stonewallet

import androidx.lifecycle.ViewModelProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.nairnei.stonewallet.service.room.RoomService
import dev.nairnei.stonewallet.viewModel.DaoViewModel
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private lateinit var db: RoomService

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("dev.nairnei.stonewallet", appContext.packageName)
    }

    @Before
    fun initDatabase()  {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = DaoViewModel().init(appContext)
        assertNotNull(db)
    }

    @Test
    fun verifyDatabase()  {
        assertNotNull(db)
    }

    @Test
    fun verifyDatabaseIsClosed()  {
        db.close()
        assertEquals(db.isOpen, false)
    }



}