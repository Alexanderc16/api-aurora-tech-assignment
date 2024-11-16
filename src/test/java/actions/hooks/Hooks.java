package actions.hooks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import propertiesreader.ConfigReader;
import api.specification.Specification;

@Slf4j
public class Hooks {

    @Before
    public void before() {
        Specification.installRequestSpec(Specification.requestSpec(ConfigReader.getProperty("base_url")));
    }

}
