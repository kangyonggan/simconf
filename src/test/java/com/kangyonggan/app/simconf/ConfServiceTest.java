package com.kangyonggan.app.simconf;

import com.kangyonggan.app.simconf.model.Conf;
import com.kangyonggan.app.simconf.service.ConfService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@Log4j2
public class ConfServiceTest extends AbstractServiceTest {

    @Autowired
    private ConfService confService;

    @Test
    public void testFindConfsByProjCodeAndEnv() {
        List<Conf> confs = confService.findConfsByProjCodeAndEnv("test", "local");
        log.info(confs);
    }

}
