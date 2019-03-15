package grab.com.newsfeed;

import android.os.Environment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import grab.com.newsfeed.constants.IntegerConstants;
import grab.com.newsfeed.utils.Utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Environment.class})
public class UtilityTest {

    @Rule
    public TemporaryFolder storageDirectory = new TemporaryFolder();
    private static final String DATE = "14/03/2019";
    private File existentDirectory;

    @Before
    public void setup() {

        existentDirectory = storageDirectory.getRoot();
        PowerMockito.mockStatic(Environment.class);
    }


    @Test
    public void dateValidator() {
        String date = Utility.dateFormatter("2019-03-14T01:36:00Z");
        assertEquals(date, DATE);
    }

    @Test
    public void FilePathValidator() {
        Mockito.when(Environment.getExternalStorageDirectory()).thenReturn(existentDirectory);
        String path = Utility.getEmptyFilePath(IntegerConstants.IMAGE);
        assertNotNull(path);
    }
}
