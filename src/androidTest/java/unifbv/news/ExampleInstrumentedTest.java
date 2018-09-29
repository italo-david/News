package unifbv.news;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Teste instrumentado, que será executado em um dispositivo Android.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Contexto do aplicativo em teste.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("unifbv.news", appContext.getPackageName());
    }
}
